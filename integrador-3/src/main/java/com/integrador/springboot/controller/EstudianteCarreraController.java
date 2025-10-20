package com.integrador.springboot.controller;

import com.integrador.springboot.dto.requestDTO.EstudianteCarreraRequestDTO;
import com.integrador.springboot.dto.responseDTO.Reporte;
import com.integrador.springboot.service.interfaces.IEstudianteCarreraService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/estudiantes-carreras")
@Validated
public class EstudianteCarreraController {
    private final IEstudianteCarreraService estudianteCarreraService;

    @PostMapping("/carrera/{idCarrera}/estudiantes/{idEstudiante}")
    @ResponseStatus(HttpStatus.OK)
    public void inscribirEstudiante(@PathVariable @Positive Integer idCarrera, @PathVariable @Positive String idEstudiante, @RequestBody @Valid EstudianteCarreraRequestDTO estudianteCarreraRequestDTO) {
        estudianteCarreraService.matricularEstudiante(idCarrera, idEstudiante, estudianteCarreraRequestDTO);
    }

    @DeleteMapping("/carrera/{idCarrera}/estudiantes/{idEstudiante}")
    @ResponseStatus(HttpStatus.OK)
    public void darDeBajaEstudiante(@PathVariable @Positive Integer idCarrera, @PathVariable @Positive String idEstudiante) {
        estudianteCarreraService.darDeBajaEstudiante(idCarrera, idEstudiante);
    }

    @GetMapping("/reporte-carreras")
    public List<Reporte> getReportesPorCarrera() {
        return estudianteCarreraService.getReportes();
    }
}
