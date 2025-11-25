# 📦 Inventario API WEB

[![Java](https://img.shields.io/badge/Java-21%2B-orange.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue.svg)](https://maven.apache.org/)
[![Javalin](https://img.shields.io/badge/Javalin-5.6-green.svg)](https://javalin.io/)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/jabv123/Inventario-API-WEB)
> **API REST para gestión de inventarios** desarrollada con Java 21 y Javalin (Soporte para Java 17+)

**👥 Autores:** [Neider Guindigua](https://github.com/Neid-09) • [Andres Botina](https://github.com/jabv123)

Esta API RESTful está diseñada para gestionar inventarios de productos, permitiendo operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los productos almacenados. Construida con Java 21 y el framework Javalin, ofrece una solución ligera y eficiente para aplicaciones de inventario.

## ⚡ Inicio Rápido

```bash
git clone <URL_DEL_REPOSITORIO>
cd Inventario-API-WEB
mvn clean package
java -jar target/Inventario-API-WEB-1.0-SNAPSHOT-jar-with-dependencies.jar
```

🌐 **Servidor:** [http://localhost:8080](http://localhost:8080)

## 🔗 Documentación

| 📖 **Tipo** | 📂 **Enlace** | 📝 **Descripción** |
|--------------|----------------|-------------------|
| 🚀 **Ejecutar** | [Inicio](docs/quick-start.md) | Configura y ejecuta rápidamente |
| 📡 **API** | [Endpoints](docs/api-endpoints.md) | Referencia completa de la API |
| 🧪 **Testing** | [Postman](docs/postman-guide.md) | Pruebas y ejemplos |
| 🏗️ **Código** | [Arquitectura](docs/architecture.md) | Patrones y diseño |
| ⚙️ **Config** | [Configuración](docs/config-package.md) | Sistema de dependencias |
| 📥 **Setup** | [Instalación](docs/installation.md) | Guía completa de instalación |

## ✨ Características Principales

- 🏗️ **Arquitectura MVC** con separación clara de responsabilidades
- 🔄 **Patrón Repository** para abstracción de datos  
- 💉 **Inyección de dependencias** personalizada
- 🛡️ **Manejo centralizado** de excepciones
- 📝 **Respuestas JSON** estandarizadas

## 🧪 Prueba Rápida

```bash
# Obtener productos
curl http://localhost:8080/productos

# Crear producto
curl -X POST http://localhost:8080/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Laptop", "precio": 1000, "stock": 10, "idCategoria": 1}'
```

## Copyright

© 2025 Neider Guindigua, Andres Botina
Todos los derechos reservados.
