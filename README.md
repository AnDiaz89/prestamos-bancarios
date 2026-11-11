# Gestión de préstamos bancarios

Aplicación de demostración para registrar y consultar solicitudes de préstamos, y aprobarlas o rechazarlas.

El proyecto incluye un backend con Spring Boot, una base de datos PostgreSQL y una interfaz Angular.

> **Importante:** es un prototipo para una prueba técnica. No tiene autenticación ni autorización: actualmente, cualquier persona que pueda acceder a la API puede aprobar o rechazar préstamos. No usar con datos reales.

## Tecnologías

- Java 17 o superior
- Spring Boot 4.0.9
- Spring Data JPA
- PostgreSQL
- Docker Compose
- Angular
- Node.js y npm

## Funcionalidades

- Registrar solicitudes de préstamo.
- Consultar la lista de préstamos.
- Aprobar o rechazar solicitudes en estado `SOLICITADO`.
- Validar los datos básicos de las solicitudes.
- Guardar los préstamos en PostgreSQL.
- Mostrar y actualizar la lista desde la interfaz Angular.

## Estructura del proyecto

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

Requisitos previos
Java 17 o superior.
Docker Desktop.
Node.js y npm.
Verifica las herramientas desde PowerShell:

powershell
Copiar código
java -version
docker --version
node --version
npm --version
Iniciar PostgreSQL
Desde PowerShell, en la carpeta raíz del proyecto, ejecuta:

powershell
Copiar código
docker compose up -d
Comprueba que el contenedor esté activo:

powershell
Copiar código
docker compose ps
El puerto externo de Windows es 5433; el puerto interno de PostgreSQL es 5432.

Configuración local:

Parámetro	Valor
Base de datos	prestamos
Usuario	prestamos
Contraseña	prestamos
Puerto	5433
Estas credenciales son solo para desarrollo local. No deben usarse en producción.

Iniciar el backend
Abre una ventana de PowerShell en la carpeta raíz del proyecto y ejecuta:

powershell
Copiar código
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://127.0.0.1:5433/prestamos"
$env:SPRING_DATASOURCE_USERNAME = "prestamos"
$env:SPRING_DATASOURCE_PASSWORD = "prestamos"
.\mvnw.cmd spring-boot:run
Deja abierta esa ventana mientras usas la aplicación. El backend estará disponible en:

text
Copiar código
http://localhost:8080
La API de préstamos está en:

text
Copiar código
http://localhost:8080/api/prestamos
Iniciar Angular
Abre una segunda ventana de PowerShell y ejecuta:

powershell
Copiar código
cd .\prestamos-frontend
npm install
npm start
Abre en el navegador:

text
Copiar código
http://localhost:4200
Para usar la aplicación, mantén activos Docker Desktop, el backend y Angular.

API disponible
Listar préstamos
http
Copiar código
GET /api/prestamos
Crear un préstamo
http
Copiar código
POST /api/prestamos
Content-Type: application/json
Ejemplo de solicitud:

json
Copiar código
{
  "referencia": "SOL-002",
  "monto": 10000,
  "tasaAnual": 12,
  "plazoMeses": 24
}
La referencia debe ser única. El préstamo se crea con estado SOLICITADO.

Aprobar o rechazar un préstamo
http
Copiar código
PATCH /api/prestamos/{id}/decision
Content-Type: application/json
Para aprobar:

json
Copiar código
{
  "decision": "APROBAR"
}
Para rechazar:

json
Copiar código
{
  "decision": "RECHAZAR"
}
Solo se permite cambiar el estado de préstamos que estén en SOLICITADO.

Reglas y limitaciones
La tasa anual se expresa como porcentaje: 12 significa 12 % anual.
El monto se guarda con dos decimales.
La tasa se guarda con hasta seis decimales.
La API permite llamadas desde http://localhost:4200.
No se implementan autenticación ni roles: cualquier usuario con acceso a la API puede aprobar o rechazar préstamos.
No se incluyen pagos ni cálculo de cuotas.
La configuración de creación automática del esquema está pensada para desarrollo, no para producción.
Detener los servicios
Para detener el backend o Angular, pulsa Ctrl+C en su ventana de PowerShell.

Para detener PostgreSQL sin borrar los datos:

powershell
Copiar código
docker compose down
Para iniciar PostgreSQL de nuevo:

powershell
Copiar código
docker compose up -d
docker compose down -v elimina también el volumen y los datos locales de PostgreSQL.