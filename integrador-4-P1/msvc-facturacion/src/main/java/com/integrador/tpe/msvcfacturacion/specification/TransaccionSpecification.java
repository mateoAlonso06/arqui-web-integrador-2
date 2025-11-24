package com.integrador.tpe.msvcfacturacion.specification;

import com.integrador.tpe.msvcfacturacion.dto.utils.TransaccionFiltroDTO;
import com.integrador.tpe.msvcfacturacion.entity.Transaccion;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class TransaccionSpecification {
    public static Specification<Transaccion> build(TransaccionFiltroDTO filtro) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filtro.fechaInicio() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("fecha"), filtro.fechaInicio()));
            }
            if (filtro.fechaFin() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("fecha"), filtro.fechaFin()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
