package com.integrador.springboot.service.implementations;

import com.integrador.springboot.dto.requestDTO.EstudianteCarreraRequestDTO;
import com.integrador.springboot.dto.responseDTO.Reporte;
import com.integrador.springboot.entity.Carrera;
import com.integrador.springboot.entity.Estudiante;
import com.integrador.springboot.entity.EstudianteCarrera;
import com.integrador.springboot.exception.CarreraNotFoundException;
import com.integrador.springboot.exception.EstudianteNotFoundException;
import com.integrador.springboot.mapper.EstudianteCarreraMapper;
import com.integrador.springboot.repository.ICarreraRepository;
import com.integrador.springboot.repository.IEstudianteCarreraRepository;
import com.integrador.springboot.repository.IEstudianteRepository;
import com.integrador.springboot.service.interfaces.IEstudianteCarreraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteCarreraService implements IEstudianteCarreraService {
    private final IEstudianteCarreraRepository estudianteCarreraRepository;
    private final ICarreraRepository carreraRepository;
    private final IEstudianteRepository estudianteRepository;
    private final EstudianteCarreraMapper estudianteCarreraMapper;

    @Override
    @Transactional
    public void matricularEstudiante(Integer idCarrera, String idEstudiante, EstudianteCarreraRequestDTO estudianteCarreraRequestDTO) {
        Carrera carrera = carreraRepository.findById(idCarrera)
                .orElseThrow(() -> new CarreraNotFoundException("La carrera con ID: " + idCarrera + " no existe."));

        Estudiante estudiante = estudianteRepository.findById(idEstudiante)
                .orElseThrow(() -> new EstudianteNotFoundException("El estudiante con ID: " + idEstudiante + " no existe"));

        boolean isMatriculado = estudianteCarreraRepository.existsEstudianteCarreraByCarreraAndEstudiante(carrera, estudiante);

        if (isMatriculado)
            throw new IllegalArgumentException("El estudiante con ID: " + idEstudiante + " ya esta matriculado a la carrera con ID: " + idCarrera);

        EstudianteCarrera nuevaMatriculacion = estudianteCarreraMapper.toEntity(estudianteCarreraRequestDTO);

        nuevaMatriculacion.setCarrera(carrera);
        nuevaMatriculacion.setEstudiante(estudiante);

        carrera.getEstudianteCarrera().add(nuevaMatriculacion);
        estudiante.getEstudianteCarreras().add(nuevaMatriculacion);

        estudianteCarreraRepository.save(nuevaMatriculacion);
    }

    @Override
    @Transactional
    public void darDeBajaEstudiante(Integer idCarrera, String idEstudiante) {
        Carrera carrera = carreraRepository.findById(idCarrera)
                .orElseThrow(() -> new CarreraNotFoundException("No existe carrera con ID: " + idCarrera));

        Estudiante estudiante = estudianteRepository.findById(idEstudiante)
                .orElseThrow(() -> new EstudianteNotFoundException("No existe estudiante con ID: " + idEstudiante));

        EstudianteCarrera matricula = estudianteCarreraRepository.findByEstudianteAndCarrera(estudiante, carrera)
                .orElseThrow(() -> new IllegalArgumentException("El estudiante no esta matriculado a la carrera con ID: " + idCarrera));

        carrera.getEstudianteCarrera().remove(matricula);
        estudiante.getEstudianteCarreras().remove(matricula);

        estudianteCarreraRepository.delete(matricula);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reporte> getReportes() {
        return estudianteCarreraRepository.obtenerReporteEstudiantesPorCarrera();
    }
}
