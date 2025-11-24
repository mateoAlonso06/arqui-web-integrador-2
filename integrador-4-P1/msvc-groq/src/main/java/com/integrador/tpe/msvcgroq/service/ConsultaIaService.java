package com.integrador.tpe.msvcgroq.service;

import com.integrador.tpe.msvcgroq.client.GroqClient;
import com.integrador.tpe.msvcgroq.dto.ParametrosExtraidosDTO;
import com.integrador.tpe.msvcgroq.dto.RespuestaConsultaDTO;
import com.integrador.tpe.msvcgroq.dto.SolicitudConsultaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConsultaIaService {

    private final GroqClient groqClient;
    private final ExternalDataService externalDataService;
    private final PromptConstructorService promptConstructorService;
    private final AnalizadorPromptService analizadorPromptService;

    public RespuestaConsultaDTO procesarConsulta(Long idUsuario, SolicitudConsultaDTO solicitud) {
        long inicio = System.currentTimeMillis();

        try {
            ParametrosExtraidosDTO parametros = analizadorPromptService.extraerParametros(solicitud.pregunta());

            Map<String, Object> datos = externalDataService.obtenerDatosSegunTipo(idUsuario, parametros);

            if (datos.containsKey("error")) {
                long duracion = System.currentTimeMillis() - inicio;
                return RespuestaConsultaDTO.builder()
                        .titulo("Error")
                        .contenido("No se pudo obtener los datos: " + datos.get("error"))
                        .datosOriginales(datos)
                        .tiempoGeneracionMs(duracion)
                        .build();
            }

            String prompt = promptConstructorService.construirPromptRespuesta(
                    solicitud.pregunta(),
                    datos
            );

            String contenidoAnalisis = groqClient.preguntar(prompt);

            long duracion = System.currentTimeMillis() - inicio;

            return RespuestaConsultaDTO.builder()
                    .titulo("Analisis de tu consulta")
                    .contenido(contenidoAnalisis)
                    .datosOriginales(datos)
                    .tiempoGeneracionMs(duracion)
                    .build();

        } catch (Exception e) {
            long duracion = System.currentTimeMillis() - inicio;
            return RespuestaConsultaDTO.builder()
                    .titulo("Error")
                    .contenido("No se pudo procesar la consulta: " + e.getMessage())
                    .tiempoGeneracionMs(duracion)
                    .build();
        }
    }
}

