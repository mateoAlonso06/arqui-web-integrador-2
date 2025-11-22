package com.integrador.tpe.msvcgateway.dto.response;

import com.integrador.tpe.msvcgateway.dto.Role;

public record LoginResponse(
        String token,
        String email,
        Role role,
        boolean success,
        String error
) {
}
