# HelpDesk API Reference

Base URL: `http://localhost:****`

> **Nota:** Los identificadores marcados como `<id-...>` deben reemplazarse por valores reales obtenidos a través de los endpoints `POST` o `GET` correspondientes. No envíes los campos `id`, `createdAt` ni `updatedAt` en los cuerpos de las peticiones; estos se gestionan automáticamente.

---

## 1. Salud del sistema
- **GET** `/api`
- **GET** `/api/health`

Ejemplo de verificación:
```bash
curl http://localhost:8095/api/health
```

---

## 2. Usuarios
- **GET** `/api/users`
- **GET** `/api/users/{id}`
- **POST** `/api/users`
- **PUT** `/api/users/{id}`
- **DELETE** `/api/users/{id}`

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111",
    "createdAt": "2025-11-13T17:05:12.345678",
    "updatedAt": "2025-11-13T17:05:12.345678",
    "username": "jsmith",
    "fullName": "John Smith",
    "role": "AGENT",
    "active": true,
    "contact": {
      "email": "john.smith@example.com",
      "phoneNumber": "+57-3001234567"
    }
  }
]
```

**Ejemplo respuesta GET (detalle)**
```json
{
  "id": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111",
  "createdAt": "2025-11-13T17:05:12.345678",
  "updatedAt": "2025-11-13T17:10:01.123456",
  "username": "jsmith",
  "fullName": "John Smith",
  "role": "SUPPORT",
  "active": true,
  "contact": {
    "email": "john.smith@example.com",
    "phoneNumber": "+57-3009876543"
  }
}
```

**POST / PUT JSON**
```json
{
  "username": "jsmith",
  "fullName": "John Smith",
  "role": "AGENT",
  "active": true,
  "contact": {
    "email": "john.smith@example.com",
    "phoneNumber": "+57-3001234567"
  }
}
```

**Respuesta DELETE**
- `204 No Content` cuando la eliminación es exitosa.
- `404 Not Found` si el recurso no existe.

---

## 3. Categorías
- **GET** `/api/categories`
- **GET** `/api/categories/{id}`
- **POST** `/api/categories`
- **PUT** `/api/categories/{id}`
- **DELETE** `/api/categories/{id}`

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "1f3d64a0-7df4-4b92-a6f0-4ef34f4ae222",
    "createdAt": "2025-11-13T17:02:00.000000",
    "updatedAt": "2025-11-13T17:02:00.000000",
    "name": "Hardware",
    "description": "Incidencias relacionadas con equipos y periféricos"
  }
]
```

**Ejemplo respuesta GET (detalle)**
```json
{
  "id": "1f3d64a0-7df4-4b92-a6f0-4ef34f4ae222",
  "createdAt": "2025-11-13T17:02:00.000000",
  "updatedAt": "2025-11-13T17:08:45.999000",
  "name": "Hardware",
  "description": "Incidencias relacionadas con equipos y periféricos"
}
```

**POST / PUT JSON**
```json
{
  "name": "Hardware",
  "description": "Incidencias relacionadas con equipos y periféricos"
}
```

**Respuesta DELETE**
- `204 No Content`
- `404 Not Found`

---

## 4. Estados
- **GET** `/api/status`
- **GET** `/api/status/{id}`
- **POST** `/api/status`
- **PUT** `/api/status/{id}`
- **DELETE** `/api/status/{id}`

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "4a6fdb25-5d26-4bb4-8f81-6b176e3b8888",
    "createdAt": "2025-11-13T17:03:00.000000",
    "updatedAt": "2025-11-13T17:03:00.000000",
    "code": "IN_PROGRESS",
    "name": "En progreso",
    "description": "El ticket está siendo trabajado por un agente"
  }
]
```

**Ejemplo respuesta GET (detalle)**
```json
{
  "id": "4a6fdb25-5d26-4bb4-8f81-6b176e3b8888",
  "createdAt": "2025-11-13T17:03:00.000000",
  "updatedAt": "2025-11-13T17:04:30.500000",
  "code": "IN_PROGRESS",
  "name": "En progreso",
  "description": "El ticket está siendo trabajado por un agente"
}
```

**POST / PUT JSON**
```json
{
  "code": "IN_PROGRESS",
  "name": "En progreso",
  "description": "El ticket está siendo trabajado por un agente"
}
```

**Respuesta DELETE**
- `204 No Content`
- `404 Not Found`

---

## 5. Prioridades
- **GET** `/api/priorities`
- **GET** `/api/priorities/{id}`
- **POST** `/api/priorities`
- **PUT** `/api/priorities/{id}`
- **DELETE** `/api/priorities/{id}`

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "2c5f9d10-1234-4091-9fb2-0f5c3dbbaa33",
    "createdAt": "2025-11-13T17:04:00.000000",
    "updatedAt": "2025-11-13T17:04:00.000000",
    "name": "Alta",
    "level": "HIGH",
    "responseMinutes": 60,
    "resolveMinutes": 240
  }
]
```

**Ejemplo respuesta GET (detalle)**
```json
{
  "id": "2c5f9d10-1234-4091-9fb2-0f5c3dbbaa33",
  "createdAt": "2025-11-13T17:04:00.000000",
  "updatedAt": "2025-11-13T17:06:15.321000",
  "name": "Alta",
  "level": "HIGH",
  "responseMinutes": 60,
  "resolveMinutes": 240
}
```

**POST / PUT JSON**
```json
{
  "name": "Alta",
  "level": "HIGH",
  "responseMinutes": 60,
  "resolveMinutes": 240
}
```
Valores permitidos para `level`: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`.

**Respuesta DELETE**
- `204 No Content`
- `404 Not Found`

---

## 6. Tickets
- **GET** `/api/tickets`
- **GET** `/api/tickets/{id}`
- **POST** `/api/tickets`
- **PUT** `/api/tickets/{id}`
- **DELETE** `/api/tickets/{id}`

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
    "createdAt": "2025-11-13T17:05:45.000000",
    "updatedAt": "2025-11-13T17:05:45.000000",
    "title": "Equipo no enciende",
    "description": "El portátil no responde al botón de encendido",
    "statusId": "ec4de150-e3dc-4596-8d2f-2503d5c239a6",
    "categoryId": "1f3d64a0-7df4-4b92-a6f0-4ef34f4ae222",
    "priorityId": "2c5f9d10-1234-4091-9fb2-0f5c3dbbaa33",
    "requesterId": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111",
    "assigneeId": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a222",
    "dueDate": "2025-11-20T18:00:00"
  }
]
```

**Ejemplo respuesta GET (detalle)**
```json
{
  "id": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
  "createdAt": "2025-11-13T17:05:45.000000",
  "updatedAt": "2025-11-13T17:20:12.500000",
  "title": "Equipo no enciende",
  "description": "Se reemplazó la fuente de poder",
  "statusId": "ec4de150-e3dc-4596-8d2f-2503d5c239a6",
  "categoryId": "1f3d64a0-7df4-4b92-a6f0-4ef34f4ae222",
  "priorityId": "2c5f9d10-1234-4091-9fb2-0f5c3dbbaa33",
  "requesterId": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111",
  "assigneeId": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a333",
  "dueDate": "2025-11-20T18:00:00"
}
```

**POST / PUT JSON**
```json
{
  "title": "Equipo no enciende",
  "description": "El portátil no responde al botón de encendido",
  "statusId": "<id-status>",
  "categoryId": "<id-categoria>",
  "priorityId": "<id-prioridad>",
  "requesterId": "<id-usuario-reporta>",
  "assigneeId": "<id-usuario-asignado>",
  "dueDate": "2025-11-20T18:00:00"
}
```
Estados válidos: `OPEN`, `ASSIGNED`, `IN_PROGRESS`, `ON_HOLD`, `RESOLVED`, `CLOSED`.

**Respuesta DELETE**
- `204 No Content`
- `404 Not Found`

---

## 7. Asignaciones
- **GET** `/api/assignments`
- **GET** `/api/assignments/{id}`
- **POST** `/api/assignments`
- **PUT** `/api/assignments/{id}`
- **DELETE** `/api/assignments/{id}`

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "b930b3af-08d0-41d8-95bc-8ecc1f05c444",
    "createdAt": "2025-11-13T17:06:30.000000",
    "updatedAt": "2025-11-13T17:06:30.000000",
    "ticketId": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
    "technicianId": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a333",
    "assignedAt": "2025-11-13T10:30:00",
    "notes": "Asignado por disponibilidad del turno matutino"
  }
]
```

**Ejemplo respuesta GET (detalle)**
```json
{
  "id": "b930b3af-08d0-41d8-95bc-8ecc1f05c444",
  "createdAt": "2025-11-13T17:06:30.000000",
  "updatedAt": "2025-11-13T17:10:45.250000",
  "ticketId": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
  "technicianId": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a333",
  "assignedAt": "2025-11-13T10:30:00",
  "notes": "Asignado por disponibilidad del turno matutino"
}
```

**POST / PUT JSON**
```json
{
  "ticketId": "<id-ticket>",
  "technicianId": "<id-tecnico>",
  "assignedAt": "2025-11-13T10:30:00",
  "notes": "Asignado por disponibilidad del turno matutino"
}
```

**Respuesta DELETE**
- `204 No Content`
- `404 Not Found`

---

## 8. Comentarios
- **GET** `/api/comments`
- **GET** `/api/comments/{id}`
- **POST** `/api/comments`
- **PUT** `/api/comments/{id}`
- **DELETE** `/api/comments/{id}`

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "c4f107f1-0a4e-48d9-9d08-b5016b765555",
    "createdAt": "2025-11-13T17:07:00.000000",
    "updatedAt": "2025-11-13T17:07:00.000000",
    "ticketId": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
    "authorId": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111",
    "content": "Se solicitó al usuario reiniciar el equipo",
    "commentedAt": "2025-11-13T11:15:00"
  }
]
```

**Ejemplo respuesta GET (detalle)**
```json
{
  "id": "c4f107f1-0a4e-48d9-9d08-b5016b765555",
  "createdAt": "2025-11-13T17:07:00.000000",
  "updatedAt": "2025-11-13T17:12:12.654321",
  "ticketId": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
  "authorId": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111",
  "content": "Se validó con el usuario, problema resuelto",
  "commentedAt": "2025-11-13T11:45:00"
}
```

**POST / PUT JSON**
```json
{
  "ticketId": "<id-ticket>",
  "authorId": "<id-autor>",
  "content": "Se solicitó al usuario reiniciar el equipo",
  "commentedAt": "2025-11-13T11:15:00"
}
```

**Respuesta DELETE**
- `204 No Content`
- `404 Not Found`

---

## 9. Adjuntos
- **GET** `/api/attachments`
- **GET** `/api/attachments/{id}`
- **POST** `/api/attachments`
- **PUT** `/api/attachments/{id}`
- **DELETE** `/api/attachments/{id}`

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "d9b6a4fb-3b68-4aa6-a712-5cb898d16666",
    "createdAt": "2025-11-13T17:07:30.000000",
    "updatedAt": "2025-11-13T17:07:30.000000",
    "ticketId": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
    "fileName": "captura-error.png",
    "filePath": "/evidencias/captura-error.png",
    "sizeBytes": 204800
  }
]
```

**Ejemplo respuesta GET (detalle)**
```json
{
  "id": "d9b6a4fb-3b68-4aa6-a712-5cb898d16666",
  "createdAt": "2025-11-13T17:07:30.000000",
  "updatedAt": "2025-11-13T17:09:05.111000",
  "ticketId": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
  "fileName": "captura-error.png",
  "filePath": "/evidencias/captura-error.png",
  "sizeBytes": 204800
}
```

**POST / PUT JSON**
```json
{
  "ticketId": "<id-ticket>",
  "fileName": "captura-error.png",
  "filePath": "/evidencias/captura-error.png",
  "sizeBytes": 204800
}
```

**Respuesta DELETE**
- `204 No Content`
- `404 Not Found`

---

## 10. SLA
- **GET** `/api/slas`
- **GET** `/api/slas/{id}`
- **POST** `/api/slas`
- **PUT** `/api/slas/{id}`
- **DELETE** `/api/slas/{id}`

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "e5a174dc-9a90-4746-8c47-1efc94b77777",
    "createdAt": "2025-11-13T17:08:00.000000",
    "updatedAt": "2025-11-13T17:08:00.000000",
    "ticketId": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
    "responseDeadline": "2025-11-13T12:00:00",
    "resolutionDeadline": "2025-11-14T18:00:00",
    "breached": false
  }
]
```

**Ejemplo respuesta GET (detalle)**
```json
{
  "id": "e5a174dc-9a90-4746-8c47-1efc94b77777",
  "createdAt": "2025-11-13T17:08:00.000000",
  "updatedAt": "2025-11-13T17:11:40.987000",
  "ticketId": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
  "responseDeadline": "2025-11-13T12:00:00",
  "resolutionDeadline": "2025-11-14T18:00:00",
  "breached": false
}
```

**POST / PUT JSON**
```json
{
  "ticketId": "<id-ticket>",
  "responseDeadline": "2025-11-13T12:00:00",
  "resolutionDeadline": "2025-11-14T18:00:00",
  "breached": false
}
```

**Respuesta DELETE**
- `204 No Content`
- `404 Not Found`

---

## 11. Notificaciones
- **GET** `/api/notifications`
- **GET** `/api/notifications/{id}`
- **POST** `/api/notifications`
- **PUT** `/api/notifications/{id}`
- **DELETE** `/api/notifications/{id}`

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "f7c9bd6e-e2e3-4127-9f1d-1c6fa6c68888",
    "createdAt": "2025-11-13T17:08:30.000000",
    "updatedAt": "2025-11-13T17:08:30.000000",
    "ticketId": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
    "recipientId": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111",
    "message": "El ticket ha sido actualizado",
    "sentAt": "2025-11-13T12:05:00",
    "read": false
  }
]
```

**Ejemplo respuesta GET (detalle)**
```json
{
  "id": "f7c9bd6e-e2e3-4127-9f1d-1c6fa6c68888",
  "createdAt": "2025-11-13T17:08:30.000000",
  "updatedAt": "2025-11-13T17:13:20.222000",
  "ticketId": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
  "recipientId": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111",
  "message": "El ticket ha sido actualizado",
  "sentAt": "2025-11-13T12:05:00",
  "read": true
}
```

**POST / PUT JSON**
```json
{
  "ticketId": "<id-ticket>",
  "recipientId": "<id-usuario>",
  "message": "El ticket ha sido actualizado",
  "sentAt": "2025-11-13T12:05:00",
  "read": false
}
```

**Respuesta DELETE**
- `204 No Content`
- `404 Not Found`

---

## Comandos útiles

### Ejemplo `curl` para crear un recurso
```bash
curl -X POST "http://localhost:8095/api/users" ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"jsmith\",\"fullName\":\"John Smith\",\"role\":\"AGENT\",\"active\":true}"
```

### Ejemplo `curl` para actualizar un recurso
```bash
curl -X PUT "http://localhost:8095/api/tickets/<id-ticket>" ^
  -H "Content-Type: application/json" ^
  -d "{\"title\":\"Equipo reparado\",\"status\":\"RESOLVED\"}"
```

### Ejemplo `curl` para eliminar un recurso
```bash
curl -X DELETE "http://localhost:8095/api/comments/<id-comentario>"
```

---

## Formato de fechas y horas
- Todas las fechas/horas se envían en formato ISO-8601: `YYYY-MM-DDTHH:MM:SS`
- Los campos booleanos aceptan `true` o `false`.

Este documento resume los endpoints disponibles y los payloads básicos para interactuar con el sistema HelpDesk.
