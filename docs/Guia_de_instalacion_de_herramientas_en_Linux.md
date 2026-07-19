# Guía de instalación de herramientas en Linux (Ubuntu)

> **Video-tutorial asociado:** Seguir el video paso a paso 👉 https://www.youtube.com/watch?v=NKQ6CUucBuo

Antes de la Clase 2 necesitás tener instaladas estas 5 herramientas. Calculá entre 15 y 30 minutos (Linux suele ser más rápido que Windows para este setup). Si algo falla, podés consultar con el instructor para ayuda.

> **Importante:** vamos a trabajar con la terminal nativa de Ubuntu (bash/zsh, la que ya viene con el sistema).

Antes que nada, actualizá los repos:

```bash
sudo apt update
```

---

## 1. Java (JDK)

El lenguaje de programación que usaremos. Vamos a instalar Eclipse Temurin JDK 21, para tener exactamente la misma distribución que los alumnos de Windows.

**Instalación (repo oficial de Adoptium):**

```bash
sudo apt install -y wget apt-transport-https gnupg
wget -O - https://packages.adoptium.net/artifactory/api/gpg/key/public | sudo tee /etc/apt/keyrings/adoptium.asc
echo "deb [signed-by=/etc/apt/keyrings/adoptium.asc] https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" | sudo tee /etc/apt/sources.list.d/adoptium.list
sudo apt update
sudo apt install temurin-21-jdk -y
```

**Verificación:**

```bash
java -version
javac -version
```

---

## 2. Maven

Gestor de dependencias y build tool de Java. En Ubuntu está empaquetado en los repos oficiales, no hace falta bajar zip ni tocar PATH a mano.

**Instalación:**

```bash
sudo apt install maven -y
```

**Verificación:**

```bash
mvn -version
```

Tiene que mostrar la versión de Maven y confirmar que está usando el JDK 21 que instalaste en el paso anterior (fijate en la línea `Java version:` del output).

---

## 3. Visual Studio Code + Extension Pack for Java

El editor que usaremos en todo el curso.

**Instalación (repo oficial de Microsoft):**

```bash
sudo apt install -y wget gpg
wget -qO- https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor > packages.microsoft.gpg
sudo install -D -o root -g root -m 644 packages.microsoft.gpg /etc/apt/keyrings/packages.microsoft.gpg
echo "deb [arch=amd64,arm64,armhf signed-by=/etc/apt/keyrings/packages.microsoft.gpg] https://packages.microsoft.com/repos/code stable main" | sudo tee /etc/apt/sources.list.d/vscode.list
rm packages.microsoft.gpg
sudo apt update
sudo apt install code -y
```

**Instalación de extensiones:**

1. Abrí VS Code: `code .` desde la terminal, o buscalo en el menú de aplicaciones.
2. Vista de Extensiones (`Ctrl+Shift+X`).
3. Buscá "Extension Pack for Java" (publisher: Microsoft) → instalar.

---

## 4. Bruno

Cliente HTTP para probar los endpoints que vamos a armar en el curso.

**Instalación (Snap, la vía más simple en Ubuntu):**

```bash
sudo snap install bruno
```

**Alternativa si no usan snap:** descargar el `.deb` desde https://www.usebruno.com/downloads e instalar con:

```bash
sudo dpkg -i bruno_*.deb
```

No requiere login ni configuración adicional.

---

## 5. DB Browser for SQLite

GUI para ver e interactuar con la base de datos SQLite que usaremos en el curso.

**Instalación:**

```bash
sudo apt install sqlitebrowser -y
```

De paso, esto también te deja disponible el cliente `sqlite3` por línea de comandos (viene como dependencia o lo instalás directo con `sudo apt install sqlite3 -y` si no lo tenés).

---

## Checklist final

Antes de la Clase 2, abrí una terminal nueva y corré individualmente cada línea, todo debería devolver la versión del programa sin ningún error:

```bash
java -version
javac -version
mvn -version
```

Sumale la verificación manual de Visual Studio Code (`code --version` o abriéndolo), Bruno y DB Browser for SQLite. Si todo esto funciona, estás listo para arrancar.
