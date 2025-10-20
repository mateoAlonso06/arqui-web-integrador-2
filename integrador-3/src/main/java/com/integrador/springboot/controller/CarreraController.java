package com.integrador.springboot.controller;

import com.integrador.springboot.dto.requestDTO.CarreraRequestDTO;
import com.integrador.springboot.dto.responseDTO.CarreraResponseDTO;
import com.integrador.springboot.dto.responseDTO.EstudianteResponseDTO;
import com.integrador.springboot.service.interfaces.ICarreraService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/carreras")
@RequiredArgsConstructor
@Validated
public class CarreraController {
    private final ICarreraService  carreraService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarreraResponseDTO addCarrera(@RequestBody CarreraRequestDTO carreraRequestDTO) {
        return carreraService.addCarrera(carreraRequestDTO);
    }

    @GetMapping("/inscriptos")
    Page<CarreraResponseDTO> getCarrerasConInscriptos(Pageable pageable) {
        return carreraService.getCarrerasConInscriptos(pageable);
    }

    @GetMapping
    public Page<CarreraResponseDTO> getAllCarreras(Pageable pageable) {
        return carreraService.getAllCarreras(pageable);
    }

    @GetMapping("/{idCarrera}/estudiantes/ciudad/{ciudad}")
    List<EstudianteResponseDTO> getEstudiantesByCarrera(@PathVariable @Positive Integer idCarrera, @PathVariable @NotBlank String ciudad) {
        return carreraService.getEstudiantesByCarreraId(idCarrera, ciudad);
    }

    @DeleteMapping("/{idCarrera}")
    public void deleteCarrera(@PathVariable @Positive Integer idCarrera) {
        carreraService.deleteCarrera(idCarrera);
    }
}
