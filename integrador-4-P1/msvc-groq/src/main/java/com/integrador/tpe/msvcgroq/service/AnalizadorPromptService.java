package com.integrador.tpe.msvcgroq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.integrador.tpe.msvcgroq.client.GroqClient;
import com.integrador.tpe.msvcgroq.dto.ParametrosExtraidosDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class AnalizadorPromptService {

    private final GroqClient groqClient;
    private final ObjectMapper objectMapper;

    public AnalizadorPromptService(GroqClient groqClient) {
        this.groqClient = groqClient;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public ParametrosExtraidosDTO extraerParametros(String preguntaUsuario) {
        String promptExtraccion = construirPromptExtraccion(preguntaUsuario);

        try {
            String respuestaIA = groqClient.preguntar(promptExtraccion);
            ParametrosExtraidosDTO parametros = parsearRespuestaIA(respuestaIA);
            return aplicarValoresPorDefecto(parametros);
        } catch (Exception e) {
            return obtenerParametrosPorDefecto();
        }
    }

    private String construirPromptExtraccion(String pregunta) {
        LocalDate hoy = LocalDate.now();

        return """
                Analiza la siguiente pregunta del usuario y extrae los parametros necesarios en formato JSON.
                
                Fecha actual: %s
                
                Pregunta: "%s"
                
                Debes extraer:
                1. idParada: ID de la parada si se menciona (o null si no se menciona)
                2. fechaInicio: Fecha de inicio en formato ISO 8601 (YYYY-MM-DDTHH:mm:ss) o null si no se menciona
                3. fechaFin: Fecha de fin en formato ISO 8601 (YYYY-MM-DDTHH:mm:ss) o null si no se menciona
                4. tipoConsulta: Puede ser "consumo-personal", "monopatines-parada" o "general"
                
                Interpreta frases temporales:
                - "este mes" -> fechaInicio: primer día del mes actual a las 00:00:00, fechaFin: hoy a las 23:59:59
                - "última semana" -> fechaInicio: hace 7 días a las 00:00:00, fechaFin: hoy a las 23:59:59
                - "hoy" -> fechaInicio: hoy a las 00:00:00, fechaFin: hoy a las 23:59:59
                - "ayer" -> fechaInicio: ayer a las 00:00:00, fechaFin: ayer a las 23:59:59
                - Si solo menciona una fecha específica, usa esa fecha tanto para inicio como fin
                
                Responde SOLO con un JSON válido sin texto adicional:
                {
                  "idParada": "string o null",
                  "fechaInicio": "YYYY-MM-DDTHH:mm:ss o null",
                  "fechaFin": "YYYY-MM-DDTHH:mm:ss o null",
                  "tipoConsulta": "string"
                }
                """.formatted(hoy, pregunta);
    }

    private ParametrosExtraidosDTO parsearRespuestaIA(String respuestaIA) {
        try {
            respuestaIA = respuestaIA.trim();

            if (respuestaIA.startsWith("```json")) {
                respuestaIA = respuestaIA.substring(7);
            }
            if (respuestaIA.endsWith("```")) {
                respuestaIA = respuestaIA.substring(0, respuestaIA.length() - 3);
            }
            respuestaIA = respuestaIA.trim();

            var jsonNode = objectMapper.readTree(respuestaIA);

            String idParada = jsonNode.has("idParada") && !jsonNode.get("idParada").isNull()
                    ? jsonNode.get("idParada").asText()
                    : null;

            LocalDateTime fechaInicio = null;
            if (jsonNode.has("fechaInicio") && !jsonNode.get("fechaInicio").isNull()) {
                String fechaInicioStr = jsonNode.get("fechaInicio").asText();
                if (!fechaInicioStr.equals("null")) {
                    try {
                        fechaInicio = LocalDateTime.parse(fechaInicioStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    } catch (Exception e) {
                        fechaInicio = null;
                    }
                }
            }

            LocalDateTime fechaFin = null;
            if (jsonNode.has("fechaFin") && !jsonNode.get("fechaFin").isNull()) {
                String fechaFinStr = jsonNode.get("fechaFin").asText();
                if (!fechaFinStr.equals("null")) {
                    try {
                        fechaFin = LocalDateTime.parse(fechaFinStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    } catch (Exception e) {
                        fechaFin = null;
                    }
                }
            }

            String tipoConsulta = jsonNode.has("tipoConsulta") && !jsonNode.get("tipoConsulta").isNull()
                    ? jsonNode.get("tipoConsulta").asText()
                    : "general";

            return new ParametrosExtraidosDTO(idParada, fechaInicio, fechaFin, tipoConsulta);

        } catch (Exception e) {
            return obtenerParametrosPorDefecto();
        }
    }

    private ParametrosExtraidosDTO aplicarValoresPorDefecto(ParametrosExtraidosDTO parametros) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicioMesActual = LocalDate.now().withDayOfMonth(1).atStartOfDay();

        if (parametros.getFechaInicio() == null) {
            parametros.setFechaInicio(inicioMesActual);
        }

        if (parametros.getFechaFin() == null) {
            parametros.setFechaFin(ahora.with(LocalTime.of(23, 59, 59)));
        }

        if (parametros.getTipoConsulta() == null || parametros.getTipoConsulta().isBlank()) {
            parametros.setTipoConsulta("general");
        }

        return parametros;
    }

    private ParametrosExtraidosDTO obtenerParametrosPorDefecto() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicioMesActual = LocalDate.now().withDayOfMonth(1).atStartOfDay();

        return new ParametrosExtraidosDTO(
                null,
                inicioMesActual,
                ahora.with(LocalTime.of(23, 59, 59)),
                "general"
        );
    }
}

