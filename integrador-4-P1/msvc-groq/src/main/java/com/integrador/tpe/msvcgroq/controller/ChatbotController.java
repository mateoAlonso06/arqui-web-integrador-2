package com.integrador.tpe.msvcgroq.controller;

import com.integrador.tpe.msvcgroq.dto.RespuestaApiDTO;
import com.integrador.tpe.msvcgroq.dto.RespuestaConsultaDTO;
import com.integrador.tpe.msvcgroq.dto.SolicitudConsultaDTO;
import com.integrador.tpe.msvcgroq.service.ConsultaIaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ChatbotController {

    private final ConsultaIaService consultaIaService;

    @PostMapping("/usuario/{idUsuario}/consultar")
    public ResponseEntity<RespuestaApiDTO<RespuestaConsultaDTO>> realizarConsulta(
            @PathVariable Long idUsuario,
            @Valid @RequestBody SolicitudConsultaDTO solicitud) {

        try {
            RespuestaConsultaDTO resultado = consultaIaService.procesarConsulta(idUsuario, solicitud);

            return ResponseEntity.ok(
                    RespuestaApiDTO.<RespuestaConsultaDTO>builder()
                            .exito(true)
                            .mensaje("Analisis generado exitosamente")
                            .datos(resultado)
                            .timestamp(System.currentTimeMillis())
                            .build()
            );

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(RespuestaApiDTO.<RespuestaConsultaDTO>builder()
                            .exito(false)
                            .mensaje(e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(RespuestaApiDTO.<RespuestaConsultaDTO>builder()
                            .exito(false)
                            .mensaje("Error interno: " + e.getMessage())
                            .timestamp(System.currentTimeMillis())
                            .build());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<RespuestaApiDTO<String>> health() {
        return ResponseEntity.ok(
                RespuestaApiDTO.<String>builder()
                        .exito(true)
                        .mensaje("Agente Groq Chatbot activo")
                        .datos("OK")
                        .timestamp(System.currentTimeMillis())
                        .build()
        );
    }
}