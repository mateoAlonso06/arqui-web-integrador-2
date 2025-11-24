package com.integrador.tpe.msvcgateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (sin autenticación)
                        .requestMatchers(
                                "/api/auth/**",
                                "/actuator/**"
                        ).permitAll()

                        // Solo rol ADMIN para todos estos métodos y rutas
                        .requestMatchers(
                                "/api/reportes/**",
                                "/api/tarifas/{id}/admin/{idAdmin}",
                                "/api/monopatines/administracion/{idAdmin}/reporte-uso",
                                "/api/cuentas/{id}/administraccion/{idAdmin}/habilitar",
                                "/api/cuentas/{id}/administraccion/{idAdmin}/deshabilitar"
                        ).hasRole("ADMIN")

                        // Solo el POST de /api/tarifas requiere rol ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/tarifas").hasRole("ADMIN")

                        // El resto de métodos en /api/tarifas (GET, PUT, etc.) públicos o
                        // si preferís solo autenticados:
                        .requestMatchers("/api/tarifas").permitAll()

                        // Todos los demás requieren autenticación
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}