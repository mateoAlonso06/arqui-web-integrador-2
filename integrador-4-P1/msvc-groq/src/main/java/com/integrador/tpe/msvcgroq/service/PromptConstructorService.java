package com.integrador.tpe.msvcgroq.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PromptConstructorService {

    public String construirPromptRespuesta(String preguntaUsuario, Map<String, Object> datos) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Eres un asistente inteligente para un sistema de monopatines compartidos.\n\n");

        prompt.append("El usuario ha preguntado: \"").append(preguntaUsuario).append("\"\n\n");

        prompt.append("Basándote ÚNICAMENTE en los siguientes datos del sistema, proporciona una respuesta clara, concisa y útil:\n\n");

        prompt.append("=== DATOS DISPONIBLES ===\n");
        formatearDatos(datos, prompt);

        prompt.append("\n=== INSTRUCCIONES ===\n");
        prompt.append("1. Responde de forma conversacional y amigable\n");
        prompt.append("2. Usa SOLO los datos proporcionados, no inventes información\n");
        prompt.append("3. Si los datos son insuficientes para responder, indícalo claramente\n");
        prompt.append("4. Incluye números y estadísticas relevantes cuando estén disponibles\n");
        prompt.append("5. Sé conciso pero completo\n\n");

        prompt.append("Tu respuesta:");

        return prompt.toString();
    }

    private void formatearDatos(Map<String, Object> datos, StringBuilder prompt) {
        if (datos.isEmpty()) {
            prompt.append("No hay datos disponibles.\n");
            return;
        }

        String tipo = (String) datos.get("tipo");

        if ("consumo-personal".equals(tipo)) {
            prompt.append("TIPO: Reporte de Consumo Personal\n");
            prompt.append("Usuario ID: ").append(datos.get("idUsuario")).append("\n");
            prompt.append("Horas de servicio: ").append(datos.get("horas")).append(" horas\n");
            prompt.append("Kilómetros recorridos: ").append(datos.get("kmRecorridos")).append(" km\n");
            prompt.append("Período: ").append(datos.get("periodoInicio"))
                    .append(" a ").append(datos.get("periodoFin")).append("\n");
        } else if ("monopatines-parada".equals(tipo)) {
            prompt.append("TIPO: Estado de Parada\n");
            prompt.append("Parada ID: ").append(datos.get("idParada")).append("\n");
            prompt.append("Total de monopatines: ").append(datos.get("cantidadTotal")).append("\n");
            prompt.append("Disponibles (LIBRE): ").append(datos.get("cantidadLibres")).append("\n");
            prompt.append("En uso: ").append(datos.get("cantidadEnUso")).append("\n");
            prompt.append("En mantenimiento: ").append(datos.get("cantidadMantenimiento")).append("\n");
        } else {
            datos.forEach((clave, valor) -> {
                if (!clave.equals("error") && !clave.equals("tipo")) {
                    prompt.append(clave).append(": ").append(valor).append("\n");
                }
            });
        }
    }
}