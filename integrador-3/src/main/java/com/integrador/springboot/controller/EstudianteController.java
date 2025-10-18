package com.integrador.springboot.controller;

import com.integrador.springboot.dto.requestDTO.EstudianteRequestDTO;
import com.integrador.springboot.dto.responseDTO.EstudianteResponseDTO;
import com.integrador.springboot.service.implementations.EstudianteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/estudiantes")
@Validated
public class EstudianteController {
    private final EstudianteService estudianteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstudianteResponseDTO addEstudiante(@RequestBody @Valid EstudianteRequestDTO estudiante) {
        return estudianteService.addEstudiante(estudiante);
    }

    @GetMapping("/libreta-universitaria/{libretaUniversitaria}")
    @ResponseStatus(HttpStatus.OK)
    public EstudianteResponseDTO getEstudianteByLibretUniversitaria(@PathVariable @NotBlank String libretaUniversitaria) {
        return estudianteService.getEstudianteByLibretaUniversitaria(libretaUniversitaria);
    }

    @GetMapping
    public Page<EstudianteResponseDTO> getEstudiantes(Pageable pageable) {
        return estudianteService.getEstudiantes(pageable);
    }

    @GetMapping("/genero/{genero}")
    public List<EstudianteResponseDTO> getEstudiantesByGenero(String genero) {
        return estudianteService.getEstudiantesByGenero(genero);
    }
}
