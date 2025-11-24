package com.integrador.tpe.msvcgroq.service;

import com.integrador.tpe.msvcgroq.client.FechasFiltro;
import com.integrador.tpe.msvcgroq.client.ParadasFeignClient;
import com.integrador.tpe.msvcgroq.client.UsuariosFeignClient;
import com.integrador.tpe.msvcgroq.dto.ParametrosExtraidosDTO;
import com.integrador.tpe.msvcgroq.dto.interservice.MonopatinResponseDTO;
import com.integrador.tpe.msvcgroq.dto.interservice.ReporteConsumoPersonal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExternalDataService {
    private final UsuariosFeignClient usuariosClient;
    private final ParadasFeignClient paradasClient;

    public Map<String, Object> obtenerDatosSegunTipo(
            Long idUsuario,
            ParametrosExtraidosDTO parametros) {

        Map<String, Object> datos = new HashMap<>();

        String tipoConsulta = parametros.getTipoConsulta();

        if ("consumo-personal".equals(tipoConsulta)) {
            return obtenerConsumoPersonal(idUsuario, parametros);
        } else if ("monopatines-parada".equals(tipoConsulta)) {
            return obtenerMonopatinesEnParada(parametros.getIdParada());
        } else {
            Map<String, Object> consumo = obtenerConsumoPersonal(idUsuario, parametros);
            datos.putAll(consumo);

            if (parametros.getIdParada() != null && !parametros.getIdParada().isBlank()) {
                Map<String, Object> parada = obtenerMonopatinesEnParada(parametros.getIdParada());
                datos.putAll(parada);
            }
        }

        return datos;
    }

    private Map<String, Object> obtenerConsumoPersonal(
            Long idUsuario,
            ParametrosExtraidosDTO parametros) {

        Map<String, Object> datos = new HashMap<>();

        try {
            LocalDateTime fechaInicio = validarFechaInicio(parametros.getFechaInicio());
            LocalDateTime fechaFin = validarFechaFin(parametros.getFechaFin());

            ReporteConsumoPersonal reporte = usuariosClient
                    .obtenerReporteConsumoPersonal(idUsuario, fechaInicio, fechaFin, false);

            System.out.println(reporte);

            datos.put("tipo", "consumo-personal");
            datos.put("reporte", reporte);
            datos.put("idUsuario", reporte.idUsuario());
            datos.put("horas", reporte.cantidadHorasServicio());
            datos.put("kmRecorridos", reporte.kmRecorridos());
            datos.put("periodoInicio", reporte.periodoInicio());
            datos.put("periodoFin", reporte.periodoFin());

        } catch (Exception e) {
            datos.put("error", "Error obteniendo consumo personal: " + e.getMessage());
        }

        return datos;
    }

    private Map<String, Object> obtenerMonopatinesEnParada(String idParada) {

        Map<String, Object> datos = new HashMap<>();

        try {
            if (idParada == null || idParada.isBlank()) {
                datos.put("error", "ID de parada no especificado");
                return datos;
            }

            List<MonopatinResponseDTO> monopatines = paradasClient
                    .getMonopatinesEnParada(idParada);

            datos.put("tipo", "monopatines-parada");
            datos.put("idParada", idParada);
            datos.put("monopatines", monopatines);
            datos.put("cantidadTotal", monopatines.size());
            datos.put("cantidadLibres", monopatines.stream()
                    .filter(m -> m.estado().name().equals("LIBRE"))
                    .count());
            datos.put("cantidadEnUso", monopatines.stream()
                    .filter(m -> m.estado().name().equals("EN_USO"))
                    .count());
            datos.put("cantidadMantenimiento", monopatines.stream()
                    .filter(m -> m.estado().name().equals("MANTENIMIENTO"))
                    .count());

        } catch (Exception e) {
            datos.put("error", "Error obteniendo monopatines: " + e.getMessage());
        }

        return datos;
    }

    private LocalDateTime validarFechaInicio(LocalDateTime fechaInicio) {
        if (fechaInicio == null) {
            return LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        }
        return fechaInicio;
    }

    private LocalDateTime validarFechaFin(LocalDateTime fechaFin) {
        if (fechaFin == null) {
            return LocalDateTime.of(2050, 1, 1, 0, 0, 0);
        }
        return fechaFin;
    }
}