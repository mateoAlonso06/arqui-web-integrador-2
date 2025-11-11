package com.integrador.tpe.msvcusuarios.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReporteConsumoServicioCompleto {
    private ReporteConsumoPersonalServicio usuarioPrincipal;
    private List<ReporteConsumoPersonalServicio> usuariosRelacionados = new ArrayList<>();

    public void addUsuarioRelacionado(ReporteConsumoPersonalServicio usuarioRelacionado) {
        this.usuariosRelacionados.add(usuarioRelacionado);
    }
    public void removeUsuarioRelacionado(ReporteConsumoPersonalServicio usuarioRelacionado) {
        this.usuariosRelacionados.remove(usuarioRelacionado);
    }
}
