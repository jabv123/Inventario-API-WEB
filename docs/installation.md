# 📥 Guía de Instalación y Configuración

Esta guía te llevará paso a paso a través del proceso de instalación y configuración del proyecto Inventario API WEB.

## 📋 Tabla de Contenidos

- [Prerrequisitos](#-prerrequisitos)
- [Instalación](#-instalación)
- [Ejecución](#-ejecución)
- [Verificación](#-verificación)

## 🛠️ Prerrequisitos

Antes de comenzar, asegúrate de tener instalados los siguientes componentes:

### ☕ Java Development Kit (JDK)

**Versión requerida:** Java 17 o superior

### 📦 Apache Maven

**Versión requerida:** Maven 3.6 o superior

### 🔧 IDE Recomendado (Opcional)

Cualquiera de los siguientes IDEs proporcionará una excelente experiencia de desarrollo:

- **[IntelliJ IDEA](https://www.jetbrains.com/idea/)** (Community o Ultimate)
- **[Visual Studio Code](https://code.visualstudio.com/)** con extensiones de Java
- **[Eclipse IDE](https://www.eclipse.org/downloads/)**

## 📥 Instalación

### 1. Clonar el Repositorio

```bash
# Usando HTTPS
git clone <URL_DEL_REPOSITORIO>

# O usando SSH (si tienes configurado)
git clone git@github.com:usuario/Inventario-API-WEB.git

# Navegar al directorio del proyecto
cd Inventario-API-WEB
```

### 2. Verificar la Estructura del Proyecto

Después de clonar, deberías ver una estructura similar a:

```
Inventario-API-WEB/
├── src/
│   └── main/
│       └── java/
│           └── org/
│               └── apirest/
├── pom.xml
├── README.md
└── docs/
```

### 3. Descargar Dependencias

Maven descargará automáticamente todas las dependencias necesarias:

```bash
mvn clean install
```

Este comando:
- 🧹 Limpia compilaciones anteriores
- 📦 Descarga todas las dependencias del `pom.xml`
- 🔨 Compila el código fuente
- 📋 Ejecuta las pruebas (si las hay)
- 📁 Empaqueta la aplicación en un archivo JAR

## 🚀 Ejecución

Tienes varias opciones para ejecutar la aplicación:

### 🎯 Opción 1: Ejecutar JAR con Dependencias (Recomendado)

Esta es la forma más confiable de ejecutar la aplicación:

```bash
# 1. Compilar y empaquetar
mvn clean package

# 2. Ejecutar el JAR generado
java -jar target/Inventario-API-WEB-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### 🔧 Opción 2: Ejecutar con Maven

```bash
mvn exec:java -Dexec.mainClass="org.apirest.Main"
```

**Nota:** Este método puede dar problemas en algunos entornos (especialmente PowerShell en Windows).

### 🖥️ Opción 3: Ejecutar desde IDE

#### IntelliJ IDEA:
1. Abre el proyecto
2. Navega a `src/main/java/org/apirest/Main.java`
3. Haz clic derecho → "Run 'Main.main()'"

#### Visual Studio Code:
1. Instala las extensiones de Java recomendadas
2. Abre el proyecto
3. Navega a `Main.java`
4. Haz clic en el botón "Run" que aparece sobre el método `main`

#### Eclipse:
1. Importa el proyecto como "Existing Maven Project"
2. Navega a `Main.java`
3. Clic derecho → "Run As" → "Java Application"

## ✅ Verificación

### Verificar que el Servidor está Ejecutándose

Después de ejecutar la aplicación, deberías ver un mensaje similar a:

```
[main] INFO io.javalin.Javalin - Listening on http://localhost:8080/
```

### Probar la API

#### 1. Endpoint de Salud (Health Check):
```bash
curl http://localhost:8080/api/health
```

#### 2. Listar Productos:
```bash
curl http://localhost:8080/api/productos
```

#### 3. Desde el Navegador:
Abre tu navegador y visita: `http://localhost:8080/api/productos`

### Verificar Logs

Los logs de la aplicación aparecerán en la consola. Si todo está funcionando correctamente, deberías ver:

```
[main] INFO io.javalin.Javalin - Starting Javalin ...
[main] INFO org.eclipse.jetty.server.Server - jetty-11.0.25; built: 2025-03-13T00:15:57.301Z; git: a2e9fae3ad8320f2a713d4fa29bba356a99d1295; jvm 21.0.6+8-LTS-188
[main] INFO org.eclipse.jetty.server.session.DefaultSessionIdManager - Session workerName=node0
[main] INFO org.eclipse.jetty.server.handler.ContextHandler - Started o.e.j.s.ServletContextHandler@4da4253{/,null,AVAILABLE}
[main] INFO org.eclipse.jetty.server.AbstractConnector - Started ServerConnector@7c0c77c7{HTTP/1.1, (http/1.1)}{0.0.0.0:8080}
[main] INFO org.eclipse.jetty.server.Server - Started Server@50a638b5{STARTING}[11.0.25,sto=0] @758ms
[main] INFO io.javalin.Javalin - Javalin started in 383ms \o/
[main] INFO io.javalin.Javalin - Listening on http://localhost:8080/
[main] INFO io.javalin.Javalin - You are running Javalin 6.6.0 (released April 13, 2025).
=====Servidor Javalin iniciado y escuchando en el puerto 8080=====
Accede a la API en http://localhost:8080/
```

## 🎉 ¡Listo!

Si has llegado hasta aquí y todo funciona correctamente, ¡felicidades! Ya tienes el proyecto Inventario API WEB ejecutándose en tu entorno local.

**Próximos pasos:**
- 📚 Lee la [Documentación de Arquitectura](architecture.md)
- 🌐 Explora la [Documentación de API](api-endpoints.md)
- 📮 Configura [Postman para pruebas](postman-guide.md)

---

🔙 **[Volver al README principal](../README.md)**
