# 🚀 Guía de Inicio Rápido

Esta guía te permitirá ejecutar la API de Inventario en **menos de 5 minutos**.

## ⚡ Prerrequisitos Mínimos

- ☕ **Java 17+** instalado
- 📦 **Maven 3.6+** instalado
- 🌐 **Navegador web** o **Postman**

## 🏃‍♂️ Pasos Rápidos

### 1. Clonar y Navegar
```bash
git clone <repository-url>
cd Inventario-API-WEB
```

### 2. Compilar y Ejecutar
```bash
mvn clean compile exec:java
```

### 3. Verificar que Funciona
Abre tu navegador en: http://localhost:8080

## 🧪 Prueba Rápida de la API

### Listar Productos
```bash
GET http://localhost:8080/productos
```

### Crear una Categoría
```bash
POST http://localhost:8080/categorias
Content-Type: application/json

{
    "nombre": "Electrónicos",
    "descripcion": "Productos electrónicos"
}
```

### Crear un Producto
```bash
POST http://localhost:8080/productos
Content-Type: application/json

{
  "nombre": "Monitor 25 pulgadas",
  "marca": "LG",
  "precio": 850000,
  "cantidad": 3,
  "descripcion": "Monitor Full HD con panel IPS y tasa de refresco de 75Hz",
  "categoria": "Monitores"
}
```

## 🛠️ Comandos Útiles

### Ejecutar con perfil de desarrollo
```bash
mvn clean compile exec:java -Dexec.args="dev"
```

### Ejecutar tests
```bash
mvn test
```

### Generar JAR ejecutable
```bash
mvn clean package
java -jar target/Inventario-API-WEB-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## 🔧 Solución de Problemas Rápidos

### Puerto 8080 ocupado
```bash
# Cambiar puerto en AppDependencies.java
# O matar proceso en puerto 8080
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Error de compilación Maven
```bash
mvn clean
mvn compile
```

### Error de dependencias
```bash
mvn clean install -U
```

## 📚 ¿Qué sigue?

1. 📖 **[Documentación de API](api-endpoints.md)** - Explora todos los endpoints
2. 🧪 **[Guía de Postman](postman-guide.md)** - Prueba la API con Postman
3. 🏗️ **[Arquitectura](architecture.md)** - Entiende cómo está construida
4. 👨‍💻 **[Desarrollo](development.md)** - Contribuye al proyecto

## 🆘 ¿Necesitas Ayuda?

- 📋 **Instalación completa**: [installation.md](installation.md)
- 🏗️ **Arquitectura del proyecto**: [architecture.md](architecture.md)
- 📝 **Estructura de paquetes**: [package-structure.md](package-structure.md)

---

⭐ Ahora puedes explorar y experimentar con los diferentes endpoints.
