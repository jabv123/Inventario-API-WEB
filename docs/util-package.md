# 🛠️ Paquete Util - Documentación Detallada

Esta documentación proporciona un análisis exhaustivo del paquete `Util`, que contiene herramientas auxiliares y utilidades comunes utilizadas en toda la aplicación.

## 📋 Tabla de Contenidos

- [Visión General](#-visión-general)
- [Mensaje.java](#-mensajejava)
- [Patrones de Diseño Implementados](#-patrones-de-diseño-implementados)
- [Casos de Uso](#-casos-de-uso)
- [Mejores Prácticas](#-mejores-prácticas)
- [Extensibilidad](#-extensibilidad)

## 🔍 Visión General

El paquete `Util` centraliza las utilidades comunes y herramientas auxiliares que se utilizan transversalmente en toda la aplicación. Su diseño modular y reutilizable facilita:

- ✅ **Estandarización** de respuestas API
- ✅ **Reutilización** de código común
- ✅ **Consistencia** en la estructura de datos
- ✅ **Mantenimiento** simplificado
- ✅ **Testing** unificado

```
src/main/java/org/apirest/Util/
└── 📦 Mensaje.java                 # Estandarización de respuestas JSON
```

## 📦 Mensaje.java

### Propósito y Responsabilidades

`Mensaje` es una **clase de utilidad fundamental** que estandariza todas las respuestas JSON de la API, proporcionando un formato consistente para la comunicación entre el servidor y los clientes.

**Responsabilidades principales:**
- 📝 **Estandarización** de respuestas JSON
- 📦 **Encapsulación** de datos de respuesta
- 🔄 **Facilitar** serialización/deserialización
- ✅ **Consistencia** en toda la API
- 🎯 **Simplificación** del manejo de respuestas

### Estructura del Código

#### Declaración de la Clase

```java
package org.apirest.Util;

//CLASE DE UTILIDAD PARA ENVIAR MENSAJES EN JSON
public class Mensaje {
    private String mensaje;
    private Object data;
    
    // Constructores, getters y setters
}
```

#### Campos de la Clase

**1. Campo `mensaje` (String):**
- **Propósito:** Contiene un mensaje descriptivo sobre el resultado de la operación
- **Uso:** Información para el usuario o desarrollador
- **Ejemplos:** "Producto creado exitosamente", "Error: ID no válido"

**2. Campo `data` (Object):**
- **Propósito:** Contiene los datos específicos de la respuesta
- **Uso:** Payload principal de la respuesta
- **Ejemplos:** Un objeto Producto, una lista de Clientes, null para errores

#### Constructores

**Constructor 1: Solo Mensaje**
```java
public Mensaje(String mensaje) {
    this.mensaje = mensaje;
}
```
- **Uso:** Para respuestas que solo necesitan un mensaje (confirmaciones, errores simples)
- **Ejemplo:** `new Mensaje("Producto eliminado exitosamente")`

**Constructor 2: Mensaje con Datos**
```java
public Mensaje(String mensaje, Object data) {
    this.mensaje = mensaje;
    this.data = data;
}
```
- **Uso:** Para respuestas que incluyen tanto mensaje como datos
- **Ejemplo:** `new Mensaje("Producto encontrado", producto)`

#### Métodos Getter y Setter

```java
// Getters
public String getMensaje() { return mensaje; }
public Object getData() { return data; }

// Setters
public void setMensaje(String mensaje) { this.mensaje = mensaje; }
public void setData(Object data) { this.data = data; }
```

### Análisis Técnico

#### Características de Diseño

**1. Simplicidad:**
- Solo dos campos esenciales
- Constructores intuitivos
- API simple y directa

**2. Flexibilidad:**
- Campo `data` como `Object` permite cualquier tipo
- Constructores múltiples para diferentes casos
- Modificable después de creación

**3. Serialización JSON:**
- Compatible con Jackson (biblioteca JSON de Javalin)
- Nombres de campos claros
- Estructura plana y simple

#### Representación JSON

```json
{
  "mensaje": "Descripción de la operación",
  "data": {
    // Cualquier objeto serializable
  }
}
```

**Nota:** En el código JSON, el campo aparece como `data`, pero en algunos contextos de la documentación se menciona como `objeto`. Esto puede indicar una inconsistencia menor en la implementación.

### Casos de Uso Detallados

#### 1. Respuesta de Éxito con Datos

**Caso:** Obtener un producto por ID

```java
// En ProductoService
public Mensaje obtenerPorId(int id) {
    Optional<Producto> producto = productoRepo.obtenerPorId(id);
    
    if (producto.isPresent()) {
        return new Mensaje("Producto encontrado", producto.get());
    } else {
        return new Mensaje("Producto no encontrado", null);
    }
}
```

**Respuesta JSON:**
```json
{
  "mensaje": "Producto encontrado",
  "data": {
    "id": 1,
    "nombre": "Laptop Gaming",
    "precio": 1299.99,
    "stock": 10,
    "idCategoria": 1
  }
}
```

#### 2. Respuesta de Éxito con Lista

**Caso:** Obtener todos los productos

```java
// En ProductoService
public Mensaje obtenerTodos() {
    List<Producto> productos = productoRepo.obtenerTodos();
    return new Mensaje("Lista de productos obtenida", productos);
}
```

**Respuesta JSON:**
```json
{
  "mensaje": "Lista de productos obtenida",
  "data": [
    {
      "id": 1,
      "nombre": "Laptop Gaming",
      "precio": 1299.99,
      "stock": 10,
      "idCategoria": 1
    },
    {
      "id": 2,
      "nombre": "Mouse Inalámbrico",
      "precio": 29.99,
      "stock": 50,
      "idCategoria": 2
    }
  ]
}
```

#### 3. Respuesta de Confirmación Simple

**Caso:** Eliminar un producto

```java
// En ProductoService
public Mensaje eliminar(int id) {
    boolean eliminado = productoRepo.eliminar(id);
    
    if (eliminado) {
        return new Mensaje("Producto eliminado exitosamente");
    } else {
        return new Mensaje("No se pudo eliminar el producto");
    }
}
```

**Respuesta JSON:**
```json
{
  "mensaje": "Producto eliminado exitosamente",
  "data": null
}
```

#### 4. Respuesta de Error de Validación

**Caso:** Error en validación de datos

```java
// En ProductoService
public Mensaje crear(Producto producto) {
    if (producto.getPrecio() <= 0) {
        return new Mensaje("Error: El precio debe ser mayor a 0", null);
    }
    
    Producto savedProducto = productoRepo.guardar(producto);
    return new Mensaje("Producto creado exitosamente", savedProducto);
}
```

**Respuesta JSON (Error):**
```json
{
  "mensaje": "Error: El precio debe ser mayor a 0",
  "data": null
}
```

#### 5. Respuesta de Operación Compleja

**Caso:** Procesar una venta

```java
// En VentaService
public Mensaje procesarVenta(VentaRequest request) {
    try {
        // Lógica compleja de venta
        VentaResult result = procesarVentaCompleta(request);
        
        return new Mensaje("Venta procesada exitosamente", result);
    } catch (Exception e) {
        return new Mensaje("Error al procesar venta: " + e.getMessage(), null);
    }
}
```

**Respuesta JSON (Éxito):**
```json
{
  "mensaje": "Venta procesada exitosamente",
  "data": {
    "ventaId": 123,
    "total": 1329.98,
    "fecha": "2025-06-01T10:30:00",
    "cliente": "Juan Pérez",
    "productos": [...]
  }
}
```

### Uso en Controllers

#### Ejemplo Completo en Controller

```java
public class ProductoController {
    private final ProductoService productoService;
    
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }
    
    // GET /api/productos/{id}
    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Mensaje resultado = productoService.obtenerPorId(id);
            
            // La respuesta siempre es un objeto Mensaje
            if (resultado.getData() != null) {
                ctx.status(200).json(resultado);
            } else {
                ctx.status(404).json(resultado);
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(new Mensaje("ID inválido", null));
        }
    }
    
    // POST /api/productos
    public void crear(Context ctx) {
        try {
            Producto producto = ctx.bodyAsClass(Producto.class);
            Mensaje resultado = productoService.crear(producto);
            
            if (resultado.getData() != null) {
                ctx.status(201).json(resultado);
            } else {
                ctx.status(400).json(resultado);
            }
        } catch (Exception e) {
            ctx.status(500).json(new Mensaje("Error interno", null));
        }
    }
}
```

### Beneficios del Diseño

#### ✅ Ventajas

1. **Consistencia Total:**
   - Todas las respuestas siguen el mismo formato
   - Facilita el desarrollo del frontend
   - Documentación de API simplificada

2. **Flexibilidad Máxima:**
   - Campo `data` acepta cualquier tipo
   - Funciona para respuestas simples y complejas
   - Extensible sin romper compatibilidad

3. **Debugging Facilitado:**
   - Mensajes descriptivos incluidos
   - Estructura clara y predecible
   - Fácil identificación de errores

4. **Integración Sencilla:**
   - Serialización automática con Jackson
   - Compatible con herramientas JSON
   - Fácil parsing en clientes

5. **Mantenimiento Reducido:**
   - Una sola clase para gestionar
   - Cambios centralizados
   - Testing simplificado

#### ⚠️ Limitaciones

1. **Tipado Débil:**
   - Campo `data` como `Object` pierde información de tipos
   - Sin validación de tipos en tiempo de compilación
   - Requiere casting en algunos casos

2. **Falta de Metadatos:**
   - No incluye códigos de error específicos
   - Sin información de paginación
   - No hay timestamps automáticos

3. **Semántica Simple:**
   - Solo dos campos pueden ser limitantes
   - No distingue entre diferentes tipos de éxito
   - Sin información de contexto adicional

### Mejoras Posibles

#### 1. Tipado Genérico

```java
public class Mensaje<T> {
    private String mensaje;
    private T data;
    
    public Mensaje(String mensaje, T data) {
        this.mensaje = mensaje;
        this.data = data;
    }
    
    // Getters y setters tipados
}
```

**Beneficios:**
- Type safety en tiempo de compilación
- Mejor IntelliSense en IDEs
- Eliminación de casting

#### 2. Códigos de Estado

```java
public class Mensaje {
    private String mensaje;
    private Object data;
    private String codigo; // Nuevo campo
    
    public Mensaje(String codigo, String mensaje, Object data) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.data = data;
    }
}
```

**Ejemplo de uso:**
```java
return new Mensaje("PRD001", "Producto no encontrado", null);
```

#### 3. Metadatos Adicionales

```java
public class Mensaje {
    private String mensaje;
    private Object data;
    private long timestamp;
    private Map<String, Object> metadata;
    
    public Mensaje(String mensaje, Object data) {
        this.mensaje = mensaje;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        this.metadata = new HashMap<>();
    }
}
```

#### 4. Builders Pattern

```java
public class Mensaje {
    // ... campos existentes
    
    public static MensajeBuilder builder() {
        return new MensajeBuilder();
    }
    
    public static class MensajeBuilder {
        private String mensaje;
        private Object data;
        
        public MensajeBuilder mensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }
        
        public MensajeBuilder data(Object data) {
            this.data = data;
            return this;
        }
        
        public Mensaje build() {
            return new Mensaje(mensaje, data);
        }
    }
}
```

**Uso:**
```java
return Mensaje.builder()
    .mensaje("Producto creado")
    .data(producto)
    .build();
```

## 🎨 Patrones de Diseño Implementados

### 1. 📦 Data Transfer Object (DTO)

**Descripción:** `Mensaje` actúa como un DTO que transporta datos entre capas.

**Implementación:**
- Encapsula datos de respuesta
- Facilita serialización JSON
- Estructura simple y plana

**Beneficios:**
- Abstracción de la estructura de respuesta
- Versionado simplificado de API
- Desacoplamiento entre capas

### 2. 🏭 Factory Method (Implícito)

**Descripción:** Los constructores actúan como factory methods para diferentes tipos de respuesta.

**Implementación:**
- Constructor para solo mensaje
- Constructor para mensaje + datos
- Setters para modificación posterior

**Beneficios:**
- Creación simplificada de respuestas
- Múltiples formas de inicialización
- Flexibilidad en la construcción

### 3. 🎯 Strategy Pattern (Potencial)

**Descripción:** La clase puede extenderse para implementar diferentes estrategias de respuesta.

**Implementación futura:**
```java
public interface MensajeStrategy {
    Mensaje createResponse(String mensaje, Object data);
}

public class SuccessMensajeStrategy implements MensajeStrategy {
    public Mensaje createResponse(String mensaje, Object data) {
        return new Mensaje("SUCCESS: " + mensaje, data);
    }
}
```

## 💡 Mejores Prácticas

### ✅ Buenas Prácticas Implementadas

1. **Mensajes Descriptivos:**
   ```java
   // ✅ Bueno
   return new Mensaje("Producto creado exitosamente", producto);
   
   // ❌ Malo
   return new Mensaje("OK", producto);
   ```

2. **Consistencia en Nulos:**
   ```java
   // ✅ Bueno - Siempre usar null para errores
   return new Mensaje("Error: Producto no encontrado", null);
   
   // ❌ Malo - Inconsistente
   return new Mensaje("Error: Producto no encontrado"); // Missing data field
   ```

3. **Mensajes Contextuales:**
   ```java
   // ✅ Bueno
   return new Mensaje("Lista de productos obtenida: " + productos.size() + " elementos", productos);
   
   // ❌ Malo
   return new Mensaje("Lista", productos);
   ```

### 🔧 Mejoras Recomendadas

1. **Validación de Entrada:**
   ```java
   public Mensaje(String mensaje, Object data) {
       this.mensaje = Objects.requireNonNull(mensaje, "Mensaje no puede ser null");
       this.data = data;
   }
   ```

2. **Constantes para Mensajes Comunes:**
   ```java
   public class MensajeConstants {
       public static final String OPERACION_EXITOSA = "Operación completada exitosamente";
       public static final String ERROR_INTERNO = "Error interno del servidor";
       public static final String DATOS_INVALIDOS = "Datos de entrada inválidos";
   }
   ```

3. **Factory Methods Específicos:**
   ```java
   public static Mensaje success(String mensaje, Object data) {
       return new Mensaje(mensaje, data);
   }
   
   public static Mensaje error(String mensaje) {
       return new Mensaje(mensaje, null);
   }
   ```

## 🚀 Extensibilidad

### Agregar Nuevas Utilidades

El paquete `Util` puede expandirse con nuevas clases de utilidad:

#### 1. Validador de Datos

```java
// src/main/java/org/apirest/Util/Validator.java
public class Validator {
    public static boolean isValidEmail(String email) {
        // Implementación de validación
    }
    
    public static boolean isValidPrice(double price) {
        return price > 0;
    }
}
```

#### 2. Formateador de Respuestas

```java
// src/main/java/org/apirest/Util/ResponseFormatter.java
public class ResponseFormatter {
    public static Mensaje formatSuccess(String operation, Object data) {
        return new Mensaje(operation + " ejecutada exitosamente", data);
    }
    
    public static Mensaje formatError(String operation, String error) {
        return new Mensaje("Error en " + operation + ": " + error, null);
    }
}
```

#### 3. Conversor de Datos

```java
// src/main/java/org/apirest/Util/DataConverter.java
public class DataConverter {
    public static String toJson(Object obj) {
        // Implementación de conversión
    }
    
    public static <T> T fromJson(String json, Class<T> clazz) {
        // Implementación de conversión
    }
}
```

### Evolucionando la Clase Mensaje

#### Versión 2.0 con Backward Compatibility

```java
public class Mensaje {
    private String mensaje;
    private Object data;
    
    // Nuevos campos opcionales
    private String version = "2.0";
    private Long timestamp;
    private String requestId;
    
    // Constructores existentes (mantener compatibilidad)
    public Mensaje(String mensaje) {
        this(mensaje, null);
    }
    
    public Mensaje(String mensaje, Object data) {
        this.mensaje = mensaje;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Nuevos constructores
    public Mensaje(String mensaje, Object data, String requestId) {
        this(mensaje, data);
        this.requestId = requestId;
    }
}
```

## 📊 Métricas de Uso

### Estadísticas de la Clase

- **Líneas de código:** 32
- **Complejidad ciclomática:** 1 (muy simple)
- **Número de métodos:** 6 (2 constructores + 4 accessors)
- **Dependencias:** 0 (completamente independiente)

### Cobertura de Casos de Uso

| Tipo de Respuesta | Cobertura | Constructor Usado |
|-------------------|-----------|-------------------|
| Éxito con datos | ✅ | `Mensaje(String, Object)` |
| Éxito sin datos | ✅ | `Mensaje(String)` |
| Error con detalles | ✅ | `Mensaje(String, null)` |
| Error simple | ✅ | `Mensaje(String)` |
| Lista de elementos | ✅ | `Mensaje(String, List)` |
| Confirmación | ✅ | `Mensaje(String)` |

### Frecuencia de Uso por Capa

| Capa | Uso de Mensaje | Propósito |
|------|----------------|-----------|
| Controllers | Alto | Respuestas HTTP |
| Services | Alto | Resultados de operaciones |
| Repository | Medio | Confirmaciones de persistencia |
| Config | Alto | Respuestas de error |

---

## 🔗 Referencias Relacionadas

- 🏛️ [Arquitectura del Proyecto](architecture.md)
- 📁 [Estructura de Paquetes](package-structure.md)
- ⚙️ [Documentación Config](config-package.md)
- 🌐 [Documentación API](api-endpoints.md)

---

🔙 **[Volver al README principal](../README.md)**
