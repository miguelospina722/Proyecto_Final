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

### Reglas de validación
- `username` es obligatorio, no puede contener solo espacios y debe ser único en el sistema. Intentar registrar un nombre ya existente responde con **400** y mensaje `"Este usuario ya está creado"`.
- `role` debe ser uno de los valores definidos en `UserRole` (`ADMIN`, `TECHNICIAN`, `AGENT`, `USER`). Alias comunes como `administrador`, `tecnico`, `practicante` se normalizan automáticamente. Cualquier valor inválido responde con **400** y mensaje `"Asigna un rol correcto"`.
- `contact.email` es obligatorio y debe contener `@`. Valores inválidos producen **400** y mensaje `"Debe poner un correo válido."`.
- `active` controla el acceso del usuario. Si es `false`, toda operación que requiera al usuario (por ejemplo, asignarlo a un ticket) responderá **400** con el mensaje `"Este usuario no está activo."`.

### Ejemplos exitosos
**GET (colección)**
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

**GET (detalle)**
```json
{
  "id": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111",
  "createdAt": "2025-11-13T17:05:12.345678",
  "updatedAt": "2025-11-13T17:10:01.123456",
  "username": "jsmith",
  "fullName": "John Smith",
  "role": "AGENT",
  "active": true,
  "contact": {
    "email": "john.smith@example.com",
    "phoneNumber": "+57-3009876543"
  }
}
```

**POST / PUT (válido)**
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

### Ejemplos de error
| Escenario | Petición | Respuesta |
|-----------|----------|-----------|
| Username duplicado | `POST /api/users` con `{"username":"jsmith"...}` cuando ya existe | `400 Bad Request`<br>`{"message":"Este usuario ya está creado"}` |
| Rol inválido | `POST /api/users` con `"role":"super"` | `400 Bad Request`<br>`{"message":"Asigna un rol correcto"}` |
| Correo inválido | `POST /api/users` con `"email":"john"` | `400 Bad Request`<br>`{"message":"Debe poner un correo válido."}` |

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
    "name": "HARDWARE",
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
  "name": "HARDWARE",
  "description": "Incidencias relacionadas con equipos y periféricos"
}
```

**POST / PUT JSON**
```json
{
  "name": "HARDWARE",
  "description": "Incidencias relacionadas con equipos y periféricos"
}
```

Valores permitidos para `name`: `INFRASTRUCTURE`, `HARDWARE`, `SOFTWARE`, `APPS`, `SERVICES`. Alias como "infraestructura", "aplicaciones" o "servicios" se normalizan automáticamente. Valores inválidos generan **400** con mensaje "Asigna una categoría correcta".

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
  "description": "El ticket está siendo trabajado por un agente"
}
```

**POST / PUT JSON**
```json
{
  "code": "IN_PROGRESS",
  "description": "El ticket está siendo trabajado por un agente"
}
```

Estados válidos para `code`: `OPEN`, `ASSIGNED`, `IN_PROGRESS`, `ON_HOLD`, `RESOLVED`, `CLOSED`. Alias como "en progreso" o "cerrado" se aceptan. Valores inválidos devuelven **400** con el mensaje "Escribe un estado correcto".

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

> **Importante:** `requester.id` y `assignee.id` deben pertenecer a usuarios activos. Si alguno está inactivo se retorna **400** con el mensaje `"Este usuario no está activo."`

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55",
    "createdAt": "2025-11-13T17:05:45.000000",
    "updatedAt": "2025-11-13T17:05:45.000000",
    "title": "Equipo no enciende",
    "description": "El portátil no responde al botón de encendido",
    "status": {
      "id": "ec4de150-e3dc-4596-8d2f-2503d5c239a6",
      "name": "En progreso"
    },
    "category": {
      "id": "1f3d64a0-7df4-4b92-a6f0-4ef34f4ae222",
      "name": "Hardware"
    },
    "priority": {
      "id": "2c5f9d10-1234-4091-9fb2-0f5c3dbbaa33",
      "name": "Alta"
    },
    "requester": {
      "id": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111",
      "fullName": "John Smith"
    },
    "assignee": {
      "id": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a222",
      "fullName": "Jane Smith"
    },
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
  "status": {
    "id": "ec4de150-e3dc-4596-8d2f-2503d5c239a6",
    "name": "En progreso"
  },
  "category": {
    "id": "1f3d64a0-7df4-4b92-a6f0-4ef34f4ae222",
    "name": "Hardware"
  },
  "priority": {
    "id": "2c5f9d10-1234-4091-9fb2-0f5c3dbbaa33",
    "name": "Alta"
  },
  "requester": {
    "id": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111",
    "fullName": "John Smith"
  },
  "assignee": {
    "id": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a333",
    "fullName": "Ana Ruiz"
  },
  "dueDate": "2025-11-20T18:00:00"
}
```

**POST / PUT JSON**
```json
{
  "title": "Equipo no enciende",
  "description": "El portátil no responde al botón de encendido",
  "status": {
    "id": "<id-status>"
  },
  "category": {
    "id": "<id-categoria>"
  },
  "priority": {
    "id": "<id-prioridad>"
  },
  "requester": {
    "id": "<id-usuario-reporta>"
  },
  "assignee": {
    "id": "<id-usuario-asignado>"
  },
  "dueDate": "2025-11-20T18:00:00"
}
```
Estados válidos: `OPEN`, `ASSIGNED`, `IN_PROGRESS`, `ON_HOLD`, `RESOLVED`, `CLOSED`. Si el código enviado no coincide con un registro existente, la respuesta será **400** con el mensaje **"Id no valido de los estados existentes"**.

### Validaciones

- **Estado (`status`)**: El código debe representar un estado registrado. Valor inválido → **400** `"Id no valido de los estados existentes"`.
- **Categoría (`category`)**: Se referencia por `id`. Si no existe → **400** `"Id no valido de las categorias existentes"`.
- **Prioridad (`priority`)**: Se referencia por `id`. Si no existe → **400** `"Id no valido de las prioridades existentes"`.
- **Solicitante (`requester`)** y **asignado (`assignee`)**: Deben ser usuarios activos. Usuario inexistente/inactivo → **400** `"Id usuario no existente, no activo o no valido"`.

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

> **Importante:** `ticket.id` debe corresponder a un ticket existente y `technician.id` debe pertenecer a un usuario activo. Consulta las validaciones para conocer los mensajes de error.

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "b930b3af-08d0-41d8-95bc-8ecc1f05c444",
    "createdAt": "2025-11-13T17:06:30.000000",
    "updatedAt": "2025-11-13T17:06:30.000000",
    "ticket": {
      "id": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55"
    },
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
  "ticket": {
    "id": "b6e793eb-cb3c-4b62-a1ac-872d85f1c111"
  },
  "technician": {
    "id": "6f8e0d1e-dccd-4f66-9f37-228d1a8c3333"
  },
  "assignedAt": "2025-11-13T10:30:00",
  "notes": "Asignado por disponibilidad del turno matutino"
}
```

### Validaciones

- **Ticket (`ticket`)**: Debe enviarse un `id` válido. Si no existe → **400** `"Ticket no encontrado"`.
- **Técnico (`technician`)**: Debe enviarse un `id` válido de usuario activo. Si el usuario no existe, está inactivo o el id es inválido → **400** `"Id usuario no existente, no activo o no valido"`.

#### Errores comunes

| Escenario | Código | Mensaje |
|----------|--------|---------|
| `ticket.id` no suministrado o inexistente | 400 | **`Ticket no encontrado`** |
| `technician.id` inexistente o inactivo | 400 | **`Id usuario no existente, no activo o no valido`** |

**POST / PUT JSON**
```json
{
  "ticket": {
    "id": "<id-ticket>"
  },
  "technician": {
    "id": "<id-tecnico>"
  },
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

> **Importante:** `author.id` debe referenciar un usuario activo. Si el usuario no existe, está inactivo o el id es inválido la API responde **400** con "Id usuario no existente, no activo o no valido".

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "c4f107f1-0a4e-48d9-9d08-b5016b765555",
    "createdAt": "2025-11-13T17:07:00.000000",
    "updatedAt": "2025-11-13T17:07:00.000000",
    "ticket": {
      "id": "9fe5217d-1140-4c6f-a7f5-7cbe3394b222"
    },
    "author": {
      "id": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111"
    },
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
  "ticket": {
    "id": "9fe5217d-1140-4c6f-a7f5-7cbe3394b222"
  },
  "author": {
    "id": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111"
  },
  "content": "Se validó con el usuario, problema resuelto",
  "commentedAt": "2025-11-13T11:45:00"
}
```

**POST / PUT JSON**
```json
{
  "ticket": {
    "id": "<id-ticket>"
  },
  "author": {
    "id": "<id-autor>"
  },
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

### Validaciones
- **Ticket (`ticket.id`)**: Debe existir en el sistema. Valor faltante o inexistente → **400** con mensaje `"Ticket no encontrado"`.

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

### Validaciones
- **Ticket (`ticket.id`)**: Debe existir en el sistema. Valor faltante o inexistente → **400** con mensaje `"Ticket no encontrado"`.

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

### Validaciones
- **Ticket (`ticket.id`)**: Debe existir en el sistema. Valor faltante o inexistente → **400** con mensaje `"Ticket no encontrado"`.
- **Destinatario (`recipient.id`)**: Debe ser un usuario activo. Valor faltante, usuario inactivo o inexistente → **400** con mensaje `"Id usuario no existente, no activo o no valido"`.

**Ejemplo respuesta GET (colección)**
```json
[
  {
    "id": "f7c9bd6e-e2e3-4127-9f1d-1c6fa6c68888",
    "createdAt": "2025-11-13T17:08:30.000000",
    "updatedAt": "2025-11-13T17:08:30.000000",
    "ticket": {
      "id": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55"
    },
    "recipient": {
      "id": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111"
    },
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
  "ticket": {
    "id": "6d4c9b68-5a2f-43a2-9d5a-71f06a1cce55"
  },
  "recipient": {
    "id": "8b9f9a24-9c6c-4a53-9e2e-7120f0d7a111"
  },
  "message": "El ticket ha sido actualizado",
  "sentAt": "2025-11-13T12:05:00",
  "read": true
}
```

**POST / PUT JSON**
```json
{
  "ticket": {
    "id": "<id-ticket>"
  },
  "recipient": {
    "id": "<id-usuario>"
  },
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
  -d "{\"username\":\"jsmith\",\"fullName\":\"John Smith\",\"role\":\"AGENT\",\"password\":\"Str0ngP@ssw0rd!\",\"active\":true}"
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
