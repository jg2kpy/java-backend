# Guía de instalación de herramientas en Windows

Antes de la Clase 2 necesitás tener instaladas estas 5 herramientas. Calculá entre 25 y 50 minutos. Si algo falla, puedes consultar con el instructor para ayuda.

> **Importante:** vamos a trabajar con la terminal nativa de Windows (CMD o PowerShell).

---

## 1. Java (JDK)

El lenguaje de programación que usaremos. Vamos a instalar Eclipse Temurin JDK 21.

**Descarga:** https://adoptium.net/temurin/releases/?version=21&os=any&arch=any

**Filtros:** Windows, JDK 21

**Instalación:** Corré el `.msi`. Cuando llegues a "Custom Setup", expandir todas las opciones y activa manualmente (vienen apagadas por default):

- ✅ Set or override JAVA_HOME variable
- ✅ Add to PATH

**Verificación:** Abrí una terminal nueva y corré:

```
java -version
javac -version
```

---

## 2. Maven

Gestor de dependencias y build tool de Java. Maven no trae instalador: se descarga un `.zip` y se agrega manualmente al PATH.

**Descarga:** https://maven.apache.org/download.cgi

**Instalación:**

1. Descomprimí el `.zip` en una ruta sin espacios, por ejemplo:
   ```
   C:\apache-maven-3.9.x
   ```
2. Buscá "Variables de entorno" en el menú de inicio → **Editar las variables de entorno del sistema** → botón **Variables de entorno...**
3. En **Variables del sistema**, seleccioná `Path` → **Editar** → **Nuevo** → agregá:
   ```
   C:\apache-maven-3.9.x\bin
   ```
4. Aceptá en todas las ventanas.

**Verificación:** Terminal nueva:

```
mvn -version
```

---

## 3. Visual Studio Code + Extension Pack for Java

El editor que usaremos en todo el curso.

**Descarga:** https://code.visualstudio.com/download → instalador de Windows (64-bit)

**Instalación:** En "Additional Tasks", marcá:

- ✅ Add "Open with Code" action to Windows Explorer context menu
- ✅ Add to PATH

**Instalación de extensiones:**

1. Abrí VS Code
2. Vista de Extensiones (`Ctrl+Shift+X`)
3. Buscá "Extension Pack for Java" (publisher: Microsoft) → instalar

---

## 4. Bruno

Cliente HTTP para probar los endpoints que vamos a armar en el curso.

**Descarga:** https://www.usebruno.com/downloads → Windows

**Instalación:** instalador estándar, Next → Next → Finish. No requiere login ni configuración.

---

## 5. DB Browser for SQLite

GUI para ver e interactuar con la base de datos SQLite que usaremos en el curso.

**Descarga:** https://sqlitebrowser.org/dl/ → Standard installer for 64-bit Windows

**Instalación:** instalador estándar, sin opciones especiales.

---

## Checklist final

Antes de la Clase 2, abrí una terminal nueva y corré individualmente cada línea, todo debería devolver la versión del programa sin ningún error:

```
java -version
javac -version
mvn -version
```

Sumale la verificación manual de Visual Studio Code, Bruno, DB Browser. Si todo esto funciona, estás listo para arrancar.
