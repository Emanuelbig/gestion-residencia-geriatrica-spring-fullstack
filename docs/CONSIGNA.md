# Consigna del parcial

[← Volver al README](../README.md)

Parcial de **Programación II** (INSPT-UTN, Prof. Miguel Silva Campos). La consigna original pedía un sistema por consola; esta versión resuelve lo mismo como una aplicación web con Spring Boot: pantallas JSP para el administrativo y, en el mismo proyecto, una API REST.

## Sistema de Gestión de Residencia Geriátrica

Desarrollar una aplicación en Java para gestionar la información de una residencia geriátrica, incluyendo residentes, enfermeros y habitaciones. El sistema debe permitir realizar operaciones CRUD sobre cada entidad y gestionar sus relaciones.

### Objetos

| Objeto | Datos |
|---|---|
| Residente | ID, DNI, nombre, apellido |
| Enfermero | ID, DNI, nombre, apellido |
| Habitación | Código, estado (ocupada / libre) |
| Reserva | Código de habitación, ID de residente |
| Asignación | Código de habitación, ID de enfermero |

### Relaciones

- Residentes ↔ Habitaciones: **uno a uno**.
- Enfermeros → Habitaciones: **uno a muchos**.

### Funcionalidades

Un administrativo debe tener control absoluto sobre el sistema:

- Ingresar, modificar, consultar o eliminar datos de residentes, enfermeros y habitaciones.
- Asignar residentes a habitaciones.
- Asignar enfermeros a habitaciones.
- Consultar:
  - El nombre del residente con su habitación asignada.
  - Los residentes atendidos por un enfermero.
  - El enfermero asignado a un residente.

Cada entidad debe tener sus operaciones CRUD y funcionalidades relacionadas.

Los ids y los códigos se generan automáticamente y de manera incremental. El usuario solo los ingresa para consultar.

---

## Dónde se cumple cada punto

Cada punto se puede usar de dos formas: desde la **pantalla** (navegador) o desde la **API** (Postman). Las dos llaman al mismo servicio.

| Punto de la consigna | Pantalla | API | Dónde está la regla |
|---|---|---|---|
| CRUD de residentes | `/residentes` | `GET` / `POST` `/api/residentes`, `GET` / `PUT` / `DELETE` `/api/residentes/{id}` | `PersonaServicio<T>` (`ResidentePantallaController` y `ResidenteController` lo usan) |
| CRUD de enfermeros | `/enfermeros` | Los mismos, en `/api/enfermeros` | `PersonaServicio<T>` |
| CRUD de habitaciones | `/habitaciones` | `GET` / `POST` `/api/habitaciones`, `GET` / `DELETE` `/api/habitaciones/{codigo}` | `HabitacionServicio`. No hay "modificar": el código es la identidad; residente y enfermero se cambian con reservas y asignaciones |
| Estado ocupada / libre | `/habitaciones` (etiqueta Libre / Ocupada) | `GET /api/habitaciones` (campo `ocupada`) | `Habitacion.isOcupada()`: está ocupada si `id_residente` no es NULL. No hay columna `ocupada` |
| Reserva y Asignación | `/reservas` y `/asignaciones` | `/api/reservas` y `/api/asignaciones` | No son tablas aparte: son las columnas `id_residente` e `id_enfermero` de `habitaciones` (3 tablas), porque ninguna relación es muchos a muchos ni tiene datos propios |
| Residente ↔ habitación, uno a uno | `/reservas` | `POST /api/reservas?idResidente=&codigo=`, `DELETE /api/reservas/{codigo}` | `ReservaServicio.reservar()`. En la base, `habitaciones.id_residente` es FK y `UNIQUE` (`@OneToOne`) |
| Enfermero → habitaciones, uno a muchos | `/asignaciones` | `POST /api/asignaciones?idEnfermero=&codigo=`, `DELETE /api/asignaciones/{codigo}` | `AsignacionServicio.asignar()`. En la base, `habitaciones.id_enfermero` es FK sin `UNIQUE` (`@ManyToOne`) |
| Consulta 1: residente con su habitación | `/reportes` | `GET /api/reportes/residentes/{id}/habitacion` | `ReporteServicio.habitacionDeResidente()` |
| Consulta 2: residentes de un enfermero | `/reportes` | `GET /api/reportes/enfermeros/{id}/residentes` | `ReporteServicio.residentesDeEnfermero()` |
| Consulta 3: enfermero de un residente | `/reportes` | `GET /api/reportes/residentes/{id}/enfermero` | `ReporteServicio.enfermeroDeResidente()` |
| Menú principal y un menú por entidad | `/` y la barra de arriba | — | `InicioController` + un controller de pantalla por tema |
| Ids automáticos e incrementales | — | — | `@GeneratedValue(IDENTITY)` en `Persona`: MySQL los genera con `AUTO_INCREMENT` y nunca los reutiliza |
| Códigos de habitación automáticos | "+ Nueva habitación" | `POST /api/habitaciones` (sin body) | `HabitacionServicio.crear()`: HAB-101, HAB-102... |
| Validaciones y errores | Aviso rojo en la misma pantalla | 400 / 404 / 409 con `{"error": "..."}` | Excepciones propias. Las pantallas las atrapan con `try/catch`; la API, con `ManejadorDeErrores` (`@RestControllerAdvice`) |
