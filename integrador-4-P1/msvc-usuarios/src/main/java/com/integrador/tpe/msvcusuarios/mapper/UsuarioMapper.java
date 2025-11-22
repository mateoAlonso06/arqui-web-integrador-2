package com.integrador.tpe.msvcusuarios.mapper;

import com.integrador.tpe.msvcusuarios.dto.request.UsuarioRequestDTO;
import com.integrador.tpe.msvcusuarios.dto.response.UsuarioResponseDTO;
import com.integrador.tpe.msvcusuarios.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    @Mapping(target = "password", ignore = true)
    Usuario toEntity(UsuarioRequestDTO usuarioRequestDTO);

    UsuarioResponseDTO toResponseDTO(Usuario usuario);
}
