# Guía de Uso del Chatbot Groq

## Endpoint Principal

**POST** `/api/chatbot/usuario/{idUsuario}/consultar`

El chatbot extrae automáticamente información del prompt (fechas, IDs de paradas, etc.) usando IA y luego consulta los microservicios necesarios.

---

## Ejemplos de Uso en Postman

### 1. Consulta de Consumo Personal

**URL**: `POST http://localhost:8099/api/chatbot/usuario/1/consultar`

**Body (JSON)**:
```json
{
  "pregunta": "¿Cuántos kilómetros recorrí este mes?"
}
```

**Lo que hace el sistema**:
1. La IA extrae del prompt: "este mes" → calcula fechas del mes actual
2. Llama a `msvc-usuarios` para obtener el reporte de consumo del usuario 1
3. Genera una respuesta conversacional con los datos obtenidos

**Respuesta esperada**:
```json
{
  "exito": true,
  "mensaje": "Analisis generado exitosamente",
  "datos": {
    "titulo": "Analisis de tu consulta",
    "contenido": "Según los datos de este mes, has recorrido un total de 45.5 kilómetros utilizando el servicio de monopatines. Has utilizado el servicio durante 12 horas, lo que representa un uso moderado...",
    "datosOriginales": {
      "tipo": "consumo-personal",
      "idUsuario": 1,
      "horas": 12,
      "kmRecorridos": 45.5,
      "periodoInicio": "2025-11-01",
      "periodoFin": "2025-11-23"
    },
    "tiempoGeneracionMs": 1523
  },
  "timestamp": 1700745600000
}
```

---

### 2. Consulta de Consumo con Rango de Fechas Específico

**URL**: `POST http://localhost:8099/api/chatbot/usuario/3/consultar`

**Body (JSON)**:
```json
{
  "pregunta": "Muéstrame mis viajes entre el 1 de octubre y el 31 de octubre de 2025"
}
```

**Lo que hace el sistema**:
1. La IA extrae: `fechaInicio: 2025-10-01T00:00:00`, `fechaFin: 2025-10-31T23:59:59`
2. Consulta el reporte de consumo del usuario 3 en ese rango
3. Genera análisis personalizado

---

### 3. Consulta de Estado de Parada

**URL**: `POST http://localhost:8099/api/chatbot/usuario/2/consultar`

**Body (JSON)**:
```json
{
  "pregunta": "¿Cuántos monopatines disponibles hay en la parada 69109df90a4d7d294328affc?"
}
```

**Lo que hace el sistema**:
1. La IA extrae: `idParada: "69109df90a4d7d294328affc"`, `tipoConsulta: "monopatines-parada"`
2. Llama a `msvc-flota` para obtener monopatines en esa parada
3. Genera respuesta con disponibilidad

**Respuesta esperada**:
```json
{
  "exito": true,
  "mensaje": "Analisis generado exitosamente",
  "datos": {
    "titulo": "Analisis de tu consulta",
    "contenido": "En la parada 69109df90a4d7d294328affc actualmente hay 8 monopatines en total. De estos, 5 están disponibles para usar (LIBRE), 2 están en uso y 1 se encuentra en mantenimiento. La disponibilidad es del 62.5%, lo cual es bueno para esta hora del día.",
    "datosOriginales": {
      "tipo": "monopatines-parada",
      "idParada": "69109df90a4d7d294328affc",
      "cantidadTotal": 8,
      "cantidadLibres": 5,
      "cantidadEnUso": 2,
      "cantidadMantenimiento": 1
    },
    "tiempoGeneracionMs": 987
  },
  "timestamp": 1700745700000
}
```

---

### 4. Consulta Combinada

**URL**: `POST http://localhost:8099/api/chatbot/usuario/5/consultar`

**Body (JSON)**:
```json
{
  "pregunta": "¿Cuánto he usado el servicio la última semana y hay monopatines disponibles en la parada ABC123?"
}
```

**Lo que hace el sistema**:
1. La IA extrae: `fechaInicio/Fin` (últimos 7 días) y `idParada: "ABC123"`
2. Consulta AMBOS microservicios (consumo personal + estado de parada)
3. Genera respuesta combinada

---

### 5. Pregunta No Relacionada

**URL**: `POST http://localhost:8099/api/chatbot/usuario/1/consultar`

**Body (JSON)**:
```json
{
  "pregunta": "¿Cuál es la capital de Francia?"
}
```

**Respuesta esperada**:
```json
{
  "exito": true,
  "mensaje": "Analisis generado exitosamente",
  "datos": {
    "titulo": "Analisis de tu consulta",
    "contenido": "Lo siento, solo puedo ayudarte con información sobre tu uso del servicio de monopatines y el estado de las paradas. No tengo datos disponibles para responder preguntas generales.",
    "datosOriginales": {
      "tipoConsulta": "general"
    },
    "tiempoGeneracionMs": 234
  },
  "timestamp": 1700745800000
}
```

---

## Variantes de Preguntas que Entiende

### Para Consumo Personal:
- "¿Cuántos kilómetros recorrí este mes?"
- "Muéstrame mis viajes de la última semana"
- "¿Cuántas horas usé el servicio en octubre?"
- "Dame un resumen de mi actividad entre el 1 y el 15 de noviembre"

### Para Paradas:
- "¿Hay monopatines en la parada X?"
- "¿Cuántos monopatines libres hay en la parada ABC?"
- "Estado de la parada 12345"

### Expresiones de Tiempo que Entiende:
- "este mes"
- "la última semana" / "última semana"
- "hoy"
- "entre el [fecha] y el [fecha]"
- "en octubre" / "en noviembre de 2025"

---

## Health Check

**GET** `http://localhost:8099/api/chatbot/health`

**Respuesta**:
```json
{
  "exito": true,
  "mensaje": "Agente Groq Chatbot activo",
  "datos": "OK",
  "timestamp": 1700745900000
}
```

---

## Requisitos Previos

1. **Variable de entorno configurada**:
   ```bash
   export GROQ_API_KEY="tu_api_key_aqui"
   ```

2. **Servicios levantados en orden**:
   - Config Server (8888)
   - Eureka (8761)
   - msvc-usuarios (8081)
   - msvc-flota (8082)
   - msvc-groq (8099)

3. **Usuario debe existir**: El `{idUsuario}` en la URL debe ser un usuario válido en la base de datos

---

## Flujo Técnico

```
Usuario → POST /api/chatbot/usuario/{id}/consultar
              ↓
    [1] AnalizadorPromptService extrae parámetros del prompt usando Groq
              ↓
    [2] ExternalDataService consulta microservicios según parámetros
              ↓
    [3] PromptConstructorService arma prompt final con datos
              ↓
    [4] GroqClient genera respuesta inteligente
              ↓
    Respuesta al usuario con análisis + datos originales
```

---

## Estructura del Response

```json
{
  "exito": boolean,
  "mensaje": "string",
  "datos": {
    "titulo": "string",
    "contenido": "string - Respuesta generada por IA",
    "datosOriginales": {
      // Datos crudos obtenidos de los microservicios
    },
    "tiempoGeneracionMs": number
  },
  "timestamp": number
}
```

