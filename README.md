# Introducción al Desarrollo Backend en Java — FP-UNA

Taller de invierno de la **Facultad Politécnica – Universidad Nacional de Asunción (FP-UNA)**.

- **Fechas:** 20 al 31 de julio de 2026 (10 clases)
- **Instructor:** José Luis Gutiérrez (Junior)
- **Modalidad:** 1h teoría sincrónica (19–20h) + 1h práctica asíncrona (20–21h)

---

## Filosofía del curso

El eje central es simple: **toda comunicación en internet es comunicación entre procesos (IPC)**. A partir de eso, el curso sigue una progresión bottom-up deliberada: primero se trabaja con **sockets crudos** para entender qué significa que dos procesos se hablen por una red, después se construye el **protocolo HTTP a mano** sobre esos sockets para ver de dónde vienen los requests y responses, y recién entonces aparece **Spring Boot** como la abstracción que automatiza todo eso.

La idea es que cuando veas que Spring Boot "levanta un servidor en el puerto 8080 y maneja requests REST", no sea magia: ya sabés qué hay abajo. Cada clase construye sobre la anterior, mostrando primero el mecanismo de bajo nivel y después la herramienta que lo resuelve.

---

## Público objetivo y prerrequisitos

- Sabés programar en al menos un lenguaje (Python, C, JavaScript, o similar)
- Nunca trabajaste con Java ni con desarrollo backend
- No hace falta experiencia previa con bases de datos ni HTTP

---

## Cronograma

| Clase | Fecha | Tema | Contenido en el repo |
|-------|-------|------|----------------------|
| 1 | 20 jul | Presentación del curso, entorno, herramientas | — (sin carpeta propia) |
| 2 | 21 jul | Java básico: sintaxis, clases, compilación con `javac` | [`clase02/`](clase02/) |
| 3 | 22 jul | IPC: procesos, hilos, sockets, HTTP a mano | [`clase03/`](clase03/) |
| 4 | 24 jul | Spring Boot: primera API REST (ToDo sin persistencia) | [`clase04/`](clase04/) |
| 5 | 25 jul | Persistencia con SQLite y Spring Data JPA | [`clase05/`](clase05/) |
| 6 | 27 jul | Autenticación con JWT (Spring Security) | [`clase06/`](clase06/) |
| 7 | 29 jul | Jobs programados con Spring Scheduling | [`clase07/`](clase07/) |
| 8 | 30 jul | Testing (JUnit, MockMvc) | — (integrado en PoliBank) |
| 9 | 31 jul mañana | Enunciado del proyecto final | — (ver PoliBank) |
| 10 | 31 jul tarde | Cierre y presentación de proyectos | — (sin carpeta propia) |

> Las clases 8, 9 y 10 no tienen carpeta propia en el repo. El contenido de testing y el proyecto final están integrados en [`Proyecto final/PoliBank/`](Proyecto%20final/PoliBank/).

---

## Estructura del repositorio

```
.
├── LICENSE                          MIT
├── docs/
│   ├── Guia_de_instalacion_de_herramientas_en_Windows.md
│   └── Guia_de_instalacion_de_herramientas_en_Linux.md
├── clase02/                         Java básico: HelloWorld, clases, archivos .class compilados
├── clase03/
│   ├── procesos_hilos/              Demos de procesos e hilos en Java
│   ├── modelo-cliente-servidor/     Socket TCP cliente/servidor, servidor multihilo
│   └── http_ejemplos/              Cliente y servidor HTTP construidos a mano sobre sockets
├── clase04/todoapp/todoapp/         Primera API REST con Spring Boot (ToDo, sin persistencia real)
├── clase05/todoapp/todoapp/         ToDo app + persistencia en SQLite (JPA/Hibernate)
├── clase06/todoapp/todoapp/         + autenticación JWT (AuthController, SecurityConfig, User entity)
├── clase07/todoapp/todoapp/         + jobs programados (Jobs.java, Spring Scheduling)
└── Proyecto final/PoliBank/         App bancaria completa: cuentas, transferencias, saldo,
                                     autenticación, jobs, tests, frontend estático, Bruno collection
```

---

## Entorno técnico

El curso usa instalación nativa en Windows. **No se usa Docker ni máquinas virtuales.**

| Herramienta | Versión / Notas |
|-------------|-----------------|
| Java | JDK 21 (Eclipse Temurin) |
| Build tool | Maven (wrapper `mvnw.cmd` incluido en cada proyecto) |
| Editor | VS Code + Extension Pack for Java |
| Cliente HTTP | Bruno |
| Inspector de BD | DB Browser for SQLite |
| Terminal | CMD o PowerShell nativo de Windows |

Persistencia en SQLite vía `sqlite-jdbc` + `hibernate-community-dialects`. Spring Security + JWT para autenticación.

> Si usás Linux o Mac, los comandos son casi idénticos salvo que usás `./mvnw` en vez de `mvnw.cmd`.

---

## Cómo levantar un proyecto Spring Boot

Todos los proyectos de clase04 en adelante (y PoliBank) siguen la misma estructura Maven. Los pasos son:

**Requisitos previos:** JDK 21 instalado y en el PATH (verificá con `java -version`).

```cmd
cd clase04\todoapp\todoapp
mvnw.cmd spring-boot:run
```

Reemplazá la ruta según la clase que querés correr. Para PoliBank:

```cmd
cd "Proyecto final\PoliBank"
mvnw.cmd spring-boot:run
```

La app levanta por defecto en `http://localhost:8080`.

**Base de datos SQLite:** cada proyecto genera su archivo `.db` en la carpeta raíz del proyecto cuando corre por primera vez. No hace falta configurar nada. Para inspeccionarla, abrí el archivo con DB Browser for SQLite.

---

## Guías de instalación

Antes de la Clase 2, necesitás tener el entorno configurado. Hay guías paso a paso con video-tutoriales asociados:

- [Instalación en Windows](docs/Guia_de_instalacion_de_herramientas_en_Windows.md)
- [Instalación en Linux/Mac](docs/Guia_de_instalacion_de_herramientas_en_Linux.md)

Los links a los video-tutoriales están dentro de cada guía.

---

## Proyecto final — PoliBank

[`Proyecto final/PoliBank/`](Proyecto%20final/PoliBank/) es una aplicación bancaria de ejemplo que integra todo lo visto en el curso:

- Registro e inicio de sesión con JWT
- Gestión de cuentas bancarias y transferencias entre cuentas
- Consulta de saldo
- Job programado: `OfertaPrestamoJob` (genera ofertas de préstamo periódicamente)
- Tests de integración con JUnit y MockMvc
- Frontend estático (HTML/JS/CSS) servido por el mismo Spring Boot
- Bruno collection para probar los endpoints manualmente
- Base de datos SQLite incluida

Sirve como caso integrador: podés leer el código de PoliBank y reconocer cada capa que se fue construyendo clase a clase.

---

## Licencia

MIT — ver [`LICENSE`](LICENSE).
