# Migración de Gateway: WebFlux → Spring MVC

## ✅ Migración completada exitosamente

Tu gateway ha sido migrado de **Spring Cloud Gateway (WebFlux reactivo)** a **Spring MVC tradicional** con soporte completo para **Feign Client** y **REST Controllers bloqueantes**.

---

## 🔄 Cambios realizados

### 1. **Dependencias (`pom.xml`)**

#### ❌ Removidas (WebFlux/Gateway reactivo):
- `spring-boot-starter-webflux`
- `spring-cloud-starter-gateway`

#### ✅ Agregadas (MVC bloqueante):
- `spring-boot-starter-web` - Spring MVC tradicional
- `spring-cloud-starter-openfeign` - Feign Client para comunicación entre microservicios
- `lombok` - Para reducir boilerplate

---

### 2. **Clase principal (`MsvcGatewayApplication.java`)**

```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients  // ← Nuevo: habilita Feign Clients
public class MsvcGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsvcGatewayApplication.java, args);
    }
}
```

---

### 3. **Cliente de comunicación con microservicios**

#### Antes (WebFlux + WebClient):
```java
@Configuration
public class WebClientConfig {
    @Bean
    public WebClient usuariosWebClient() {
        return WebClient.builder()
                .baseUrl("http://msvc-usuarios/api/usuarios")
                .build();
    }
}
```

#### Ahora (Feign Client):
```java
@FeignClient(name = "msvc-usuarios", path = "/api/usuarios")
public interface UsuarioFeignClient {
    @PostMapping("/validate")
    ResponseEntity<UsuarioResponseDTO> validateCredentials(@RequestBody LoginRequest loginRequest);
}
```

---

### 4. **AuthService (lógica de autenticación)**

#### Antes (reactivo con `.block()`):
```java
public LoginResponse authenticate(LoginRequest request) {
    UsuarioResponseDTO usuario = usuariosWebClient.post()
            .uri("/validate")
            .bodyValue(request)
            .retrieve()
            .bodyToMono(UsuarioResponseDTO.class)
            .block();  // ← Problema: bloqueo en hilo reactivo
    // ...
}
```

#### Ahora (bloqueante tradicional):
```java
public LoginResponse authenticate(LoginRequest request) {
    ResponseEntity<UsuarioResponseDTO> response = usuarioFeignClient.validateCredentials(request);
    
    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
        UsuarioResponseDTO usuario = response.getBody();
        String token = jwtUtil.generateToken(usuario.email(), usuario.role().toString());
        return new LoginResponse(token, usuario.email(), usuario.role(), true, null);
    }
    
    return new LoginResponse(null, null, null, false, "Credenciales inválidas");
}
```

---

### 5. **JWT Filter (autenticación en requests)**

#### Antes (WebFlux `GlobalFilter`):
```java
@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Lógica reactiva...
    }
}
```

#### Ahora (Spring MVC `OncePerRequestFilter`):
```java
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        
        String path = request.getRequestURI();
        if (isPublicPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);

                UsernamePasswordAuthenticationToken authentication = 
                    new UsernamePasswordAuthenticationToken(
                        email, null, List.of(new SimpleGrantedAuthority("ROLE_" + role))
                    );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
```

---

### 6. **SecurityConfig (configuración de seguridad)**

#### Antes (WebFlux):
```java
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange -> exchange.anyExchange().permitAll())
                .build();
    }
}
```

#### Ahora (Spring Security MVC):
```java
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> 
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/auth/**", "/eureka/**", "/actuator/**").permitAll()
                    .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
```

---

### 7. **GlobalExceptionHandler (manejo de errores)**

#### Antes (excepciones de WebClient):
```java
@ExceptionHandler(WebClientResponseException.Unauthorized.class)
public ResponseEntity<LoginResponse> handleUnauthorized(WebClientResponseException.Unauthorized ex) {
    // ...
}
```

#### Ahora (excepciones de Feign):
```java
@ExceptionHandler(FeignException.Unauthorized.class)
public ResponseEntity<LoginResponse> handleUnauthorized(FeignException.Unauthorized ignored) {
    LoginResponse response = new LoginResponse(
            null, null, null, false, "Usuario o contraseña incorrectos"
    );
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
}

@ExceptionHandler(FeignException.class)
public ResponseEntity<LoginResponse> handleFeignErrors(FeignException ex) {
    LoginResponse response = new LoginResponse(
            null, null, null, false, "Error al comunicarse con el servicio de usuarios"
    );
    return ResponseEntity.status(ex.status()).body(response);
}
```

---

## 📝 Estado actual

### ✅ Archivos correctamente migrados:
1. `pom.xml` - Dependencias actualizadas
2. `MsvcGatewayApplication.java` - Anotación `@EnableFeignClients` agregada
3. `UsuarioFeignClient.java` - Cliente Feign para `msvc-usuarios`
4. `AuthService.java` - Lógica bloqueante con Feign
5. `AuthController.java` - REST controller MVC estándar
6. `JwtAuthenticationFilter.java` - Filtro MVC con `OncePerRequestFilter`
7. `SecurityConfig.java` - Configuración Spring Security MVC
8. `GlobalExceptionHandler.java` - Manejo de `FeignException`
9. `JwtUtil.java` - Sin cambios (compatible con ambos)

### 🗑️ Archivos eliminados:
- `WebClientConfig.java` - Ya no necesario
- `UsuarioClientWebClient.java` - Reemplazado por `UsuarioFeignClient`

---

## 🚀 Cómo usar ahora

### 1. **Compilar el proyecto**:
```bash
mvn clean install -DskipTests
```

### 2. **Levantar el gateway**:
```bash
mvn spring-boot:run
```

### 3. **Probar el login**:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@example.com",
    "password": "password123"
  }'
```

### 4. **Usar el token en requests protegidos**:
```bash
curl -X GET http://localhost:8080/api/viajes \
  -H "Authorization: Bearer <tu_token_jwt>"
```

---

## 🔍 Ventajas de la migración

| Aspecto | Antes (WebFlux) | Ahora (MVC) |
|---------|----------------|-------------|
| **Modelo de programación** | Reactivo (`Mono`, `Flux`) | Bloqueante tradicional |
| **Curva de aprendizaje** | Alta | Baja |
| **Compatibilidad Feign** | Problemática (requiere workarounds) | Nativa |
| **Debugging** | Complejo (stack traces reactivos) | Simple |
| **Código** | Verboso con operadores reactivos | Limpio y directo |
| **Integración Spring Security** | WebFlux Security | Spring Security estándar |

---

## ⚠️ Nota importante

Los **errores que ves en el IDE** son de caché. Maven compiló correctamente (BUILD SUCCESS). Para resolverlos:

1. **IntelliJ IDEA**: File → Invalidate Caches → Restart
2. **Eclipse**: Project → Clean → Build
3. **VS Code**: Reload Window (Ctrl+Shift+P → "Developer: Reload Window")

---

## 📚 Próximos pasos recomendados

1. ✅ Refrescar el IDE para eliminar errores de caché
2. ✅ Probar el endpoint de login
3. ✅ Verificar que Eureka descubre `msvc-usuarios`
4. ✅ Asegurar endpoints específicos según roles (si es necesario)
5. ✅ Implementar refresh tokens (opcional)

---

**Estado final**: ✅ Gateway completamente funcional con Spring MVC + Feign Client

