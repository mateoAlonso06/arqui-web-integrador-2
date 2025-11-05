package com.integrador.tpe.msvcusuarios.service.impl;

import com.integrador.tpe.msvcusuarios.dto.request.CuentaRequestDTO;
import com.integrador.tpe.msvcusuarios.dto.response.CuentaResponseDTO;
import com.integrador.tpe.msvcusuarios.entity.Cuenta;
import com.integrador.tpe.msvcusuarios.entity.Usuario;
import com.integrador.tpe.msvcusuarios.exception.CuentaNotFoundException;
import com.integrador.tpe.msvcusuarios.exception.UsuarioNotFoundException;
import com.integrador.tpe.msvcusuarios.mapper.CuentaMapper;
import com.integrador.tpe.msvcusuarios.repository.ICuentaRepository;
import com.integrador.tpe.msvcusuarios.repository.IUsuarioRepository;
import com.integrador.tpe.msvcusuarios.service.ICuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio para la gestión de cuentas de usuario.
 * Proporciona métodos para obtener, crear y eliminar cuentas.
 */
@Service
@RequiredArgsConstructor
public class CuentaService implements ICuentaService {

    private final ICuentaRepository cuentaRepository;
    private final IUsuarioRepository usuarioRepository;
    private final CuentaMapper cuentaMapper;

    /**
     * Obtiene una página de cuentas, opcionalmente filtradas por el estado habilitado.
     *
     * @param habilitado valor booleano para filtrar cuentas habilitadas o deshabilitadas (puede ser null para no filtrar)
     * @param pageable   información de paginación
     * @return página de cuentas en formato CuentaResponseDTO
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CuentaResponseDTO> getAllCuentas(Boolean habilitado, Pageable pageable) {
        Page<Cuenta> cuentas = (habilitado != null)
                ? this.cuentaRepository.findCuentaByEstadoCuenta(habilitado, pageable)
                : this.cuentaRepository.findAll(pageable);

        return cuentas.map(cuentaMapper::toResponse);
    }

    /**
     * Obtiene una cuenta por su ID.
     *
     * @param id identificador de la cuenta
     * @return CuentaResponseDTO correspondiente
     * @throws CuentaNotFoundException si no existe la cuenta con el ID proporcionado
     */
    @Override
    @Transactional(readOnly = true)
    public CuentaResponseDTO getCuentaById(Long id) {
        return cuentaMapper.toResponse(
                cuentaRepository.findById(id)
                        .orElseThrow(() -> new CuentaNotFoundException("No existe cuenta con ID: " + id))
        );
    }

    /**
     * Elimina una cuenta por su ID.
     *
     * @param id identificador de la cuenta a eliminar
     * @throws CuentaNotFoundException si no existe la cuenta con el ID proporcionado
     */
    @Override
    @Transactional
    public void deleteCuentaById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("No existe cuenta con ID: " + id));
        cuentaRepository.delete(cuenta);
    }

    /**
     * Crea una nueva cuenta a partir de los datos proporcionados.
     *
     * @param cuentaCreateDTO datos para la creación de la cuenta
     * @return CuentaResponseDTO de la cuenta creada
     */
    @Override
    @Transactional
    public CuentaResponseDTO createCuenta(CuentaRequestDTO cuentaCreateDTO) {
        if (cuentaRepository.existsCuentaByIdCuentaMercadoPago(cuentaCreateDTO.idCuentaMercadoPago()))
            throw new IllegalArgumentException("Ya existe una cuenta asociada al ID de Mercado Pago: " + cuentaCreateDTO.idCuentaMercadoPago());

        Cuenta cuenta = cuentaMapper.toEntity(cuentaCreateDTO);
        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);
        return cuentaMapper.toResponse(cuentaGuardada);
    }

    /**
     * Habilita una cuenta por su ID.
     *
     * @param id identificador de la cuenta a habilitar
     * @return CuentaResponseDTO de la cuenta habilitada
     * @throws CuentaNotFoundException si no existe la cuenta con el ID proporcionado
     */
    @Override
    @Transactional
    public CuentaResponseDTO habilitarCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("No existe cuenta con ID: " + id));

        if (cuenta.getEstadoCuenta() != null && cuenta.getEstadoCuenta().equals(false)) {
            cuenta.setEstadoCuenta(true);
            Cuenta cuentaActualizada = cuentaRepository.save(cuenta);
            return cuentaMapper.toResponse(cuentaActualizada);
        } else {
            return cuentaMapper.toResponse(cuenta);
        }
    }

    /**
     * Deshabilita una cuenta por su ID.
     *
     * @param id identificador de la cuenta a deshabilitar
     * @return CuentaResponseDTO de la cuenta deshabilitada
     * @throws CuentaNotFoundException si no existe la cuenta con el ID proporcionado
     */
    @Override
    @Transactional
    public CuentaResponseDTO deshabilitarCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNotFoundException("No existe cuenta con ID: " + id));

        if (cuenta.getEstadoCuenta() != null && cuenta.getEstadoCuenta().equals(true)) {
            cuenta.setEstadoCuenta(false);
            Cuenta cuentaActualizada = cuentaRepository.save(cuenta);
            return cuentaMapper.toResponse(cuentaActualizada);
        } else {
            return cuentaMapper.toResponse(cuenta);
        }
    }

    @Override
    @Transactional
    public void asociarUsuarioACuenta(Long idCuenta, Long idUsuario) {
        // - both entities have lazy loading relationships | 2 querys vs 1 query with join
        // - is better query the association existence in the database.
        if (cuentaRepository.existeAsociacionCuentaUsuario(idCuenta, idUsuario))
            throw new IllegalArgumentException("La cuenta con ID: " + idCuenta + " ya está asociada al usuario con ID: " + idUsuario);

        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new CuentaNotFoundException("No existe cuenta con ID: " + idCuenta));
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("No existe usuario con ID: " + idUsuario));

        cuenta.addUsuario(usuario); // internamente sincroniza ambas colecciones en memoria
        cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional
    public void removerUsuarioDeCuenta(Long idCuenta, Long idUsuario) {
        if (!cuentaRepository.existeAsociacionCuentaUsuario(idCuenta, idUsuario))
            throw new IllegalArgumentException("La cuenta con ID: " + idCuenta + " ya está asociada al usuario con ID: " + idUsuario);

        Cuenta cuenta = cuentaRepository.findById(idCuenta)
                .orElseThrow(() -> new CuentaNotFoundException("No existe cuenta con ID: " + idCuenta));
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNotFoundException("No existe usuario con ID: " + idUsuario));

        cuenta.removeUsuario(usuario); // internamente sincroniza ambas colecciones en memoria
        cuentaRepository.save(cuenta);
    }
}
