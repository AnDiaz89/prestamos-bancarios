# Gestión de Préstamos Bancarios

Prototipo de aplicación full-stack para el registro, consulta y evaluación (aprobación o rechazo) de solicitudes de préstamos. Desarrollado como prueba técnica.

> **Nota de seguridad:** Este proyecto es una demostración local. No implementa mecanismos de autenticación ni autorización, por lo que cualquier cliente con acceso a la API puede modificar el estado de los préstamos. No utilizar en entornos de producción ni con datos reales.

---

## Tech Stack

* **Backend:** Java 17+, Spring Boot 4.0.9, Spring Data JPA
* **Base de datos:** PostgreSQL (vía Docker Compose)
* **Frontend:** Angular, Node.js, npm

---

## Funcionalidades

* Registro de nuevas solicitudes de préstamo.
* Listado y visualización de solicitudes guardadas.
* Cambio de estado (`APROBAR` / `RECHAZAR`) únicamente para solicitudes en estado `SOLICITADO`.
* Validación de datos de entrada en el servidor.
* Persistencia en PostgreSQL.
* Interfaz reactiva en Angular sincronizada con la API REST.

---

## Estructura del Proyecto

```text
prestamos/
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
├── src/
│   └── main/
│       ├── java/com/ejemplo/prestamos/
│       │   ├── api/
│       │   ├── dominio/
│       │   └── repositorio/
│       └── resources/
│           └── application.properties
└── prestamos-frontend/
    ├── package.json
    └── src/
        ├── app/
        └── main.ts
