package com.integrador.tpe.msvcfacturacion.specification;

import com.integrador.tpe.msvcfacturacion.dto.utils.TransaccionFiltroDTO;
import com.integrador.tpe.msvcfacturacion.entity.Transaccion;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class TransaccionSpecification {
    public static Specification<Transaccion> build(TransaccionFiltroDTO filtro) {
        if (filtro == null) {
            return (root, query, cb) -> cb.conjunction(); // Sin filtros
        }
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filtro.getFechaInicio() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fecha"), filtro.getFechaInicio()));
            }
            if (filtro.getFechaFin() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fecha"), filtro.getFechaFin()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
