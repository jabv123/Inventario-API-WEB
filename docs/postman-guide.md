# 🧪 Guía de Postman para API de Inventario

Esta guía te ayudará a probar todos los endpoints de la API utilizando **Postman**.

## 📁 **Workspace de Apoyo**

- **[Enlace de invitación](https://app.getpostman.com/join-team?invite_code=73668ca022d84b576830d384b9457fcdf9fb01a92e7b5cea0abdbc9939d48970&target_code=3021fe12daa083177e93eb458ffba154)** - Colección completa

---

## 📋 Índice de APIs y Controladores

La API de Inventario cuenta con los siguientes módulos y controladores:

### 🎯 **APIs Principales**
- [🏷️ **Categorías**](#️-categorías) - Gestión de categorías de productos
- [📦 **Productos**](#-productos) - Gestión completa de productos  
- [🖼️ **Imágenes de Productos**](#️-imágenes-de-productos) - Gestión de imágenes
- [👥 **Clientes**](#-clientes) - Gestión de clientes
- [💰 **Ventas**](#-ventas) - Gestión de ventas y facturación
- [👤 **Usuarios**](#-usuarios) - Gestión de usuarios del sistema
- [🛒 **Carrito de Compras**](#-carrito-de-compras) - Gestión de carritos de compra
- [🏢 **Proveedores**](#-proveedores) - Gestión de proveedores
- [💳 **Métodos de Pago**](#-métodos-de-pago) - Gestión de métodos de pago
- [🎫 **Cupones de Descuento**](#-cupones-de-descuento) - Gestión de cupones y descuentos

### 📊 **APIs de Control**
- [📈 **Ajustes de Stock**](#-ajustes-de-stock) - Control de inventario y ajustes
- [📝 **Logs del Sistema**](#-logs-del-sistema) - Monitoreo y auditoría

---

## 📥 Configuración Inicial

### 1. Importar Colección
Descarga e importa la colección de Postman con todos los endpoints preconfigurados:

**[📁 Colección Postman](./TEST-API.postman_collection.json)**

### 2. Configurar Variables de Entorno

Crea un nuevo entorno en Postman con las siguientes variables:

| Variable | Valor | Descripción |
|----------|-------|-------------|
| `baseUrl` | `http://localhost:8080` | URL base de la API |

## 🚀 Configuración Rápida Manual

Si prefieres configurar manualmente, sigue estos pasos:

### 1. Crear Nueva Colección
- Abre Postman
- Clic en "New" → "Collection"
- Nombre: "Inventario API"
- Descripción: "API REST para gestión de inventario"

### 2. Configurar Headers Globales
En la configuración de la colección, añade:
```
Content-Type: application/json
Accept: application/json
```

## 📋 Flujo de Pruebas Recomendado

### 🎯 Prueba Básica (5 minutos)

1. **Verificar que la API está funcionando**
2. **Crear una categoría**
3. **Crear un producto**
4. **Crear un cliente**
5. **Realizar una venta**

### 🔄 Flujo Completo de Pruebas

#### **Paso 1: Verificar API**
```http
GET {{baseUrl}}/categorias
```
**Resultado esperado:** Lista vacía o categorías existentes

#### **Paso 2: Crear Usuario Administrador**
```http
POST {{baseUrl}}/usuarios
Content-Type: application/json

{
    "nombre":"{{$randomUserName}}",
    "email":"{{$randomEmail}}",
    "contraseña":"{{$randomPassword}}",
    "rol":"Admin"
}
```

#### **Paso 3: Crear Proveedor**
```http
POST {{baseUrl}}/proveedores
Content-Type: application/json

{
    "nombre": "GW",
    "contacto": "Jouse31",
    "telefono": "312236432",
    "email": "gw@gmail.com",
    "direccion": "Bogota CR 13 #22"
}
```

#### **Paso 4: Crear Categoría**
```http
POST {{baseUrl}}/categorias
Content-Type: application/json

{
    "nombre": "Electrónicos",
    "descripcion": "Productos electrónicos y gadgets"
}
```
**Resultado esperado:** Categoría creada con ID asignado

#### **Paso 5: Crear Producto**
```http
POST {{baseUrl}}/productos
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

#### **Paso 6: Agregar Imagen al Producto**
```http
POST {{baseUrl}}/imgProductos
Content-Type: application/json

{
    "idProducto":2,
    "url":"Url.test",
    "orden":2
}
```

#### **Paso 7: Crear Cliente**
```http
POST {{baseUrl}}/clientes
Content-Type: application/json

{
    "nombre":"newName",
    "email":"test",
    "contraseña":"test123",
    "direccion":"av"
}
```

#### **Paso 8: Crear Método de Pago para Cliente**
```http
POST {{baseUrl}}/metodos-pago
Content-Type: application/json

{
  "idCliente": 1,
  "tipoPago": "Tarjeta de Crédito", 
  "activo": true,
  "detalles": [
    {"clave": "numeroEnmascarado", "valor": "12312 3231231 32 1234"},
    {"clave": "fechaVencimiento", "valor": "12/25"},
    {"clave": "tipoTarjeta", "valor": "Visa"}
  ]
}
```

#### **Paso 9: Crear Cupón de Descuento**
```http
POST {{baseUrl}}/cupones
Content-Type: application/json

{
    //tipo valor: porcentaje ó fijo
    "codigo": "DESCUENTO15",
    "valor": 15.0, //
    "tipoValor": "porcentaje"
}
```

#### **Paso 10: Crear Carrito y Agregar Productos**
```http
# Crear carrito
POST {{baseUrl}}/carrito
Content-Type: application/json

{
    "idCliente": {{clienteId}}
}

# Agregar producto al carrito
POST {{baseUrl}}/carrito/{{carritoId}}/item
Content-Type: application/json

{
    "idProducto": {{productoId}},
}
```

#### **Paso 11: Realizar Venta**
```http
POST {{baseUrl}}/ventas
Content-Type: application/json

{
    "idCliente":1,
    "estado":"COMPLETADA",
    "idMetodoPago": 1
    //"codigoCuponAplicado":"DESCUENTO15" //Opcional
}
```

#### **Paso 12: Registrar Ajuste de Stock**
```http
POST {{baseUrl}}/ajustes-stock
Content-Type: application/json

{
    "idProducto": {{productoId}},
    "cantidadAjuste": 5,
    "motivo": "Reposición de inventario",
    "idUsuario": {{usuarioId}}
}
```

#### **Paso 13: Verificar Logs del Sistema**
```http
GET {{baseUrl}}/logs-sistema
```

---

## 🚀 **Flujos de Prueba Especializados**

### 🛒 **Flujo E-commerce Completo**

1. **Setup Inicial**
   - Crear usuario cliente
   - Crear categorías de productos
   - Crear productos con imágenes
   - Crear cupones de descuento

2. **Proceso de Compra**
   - Cliente crea carrito
   - Agrega múltiples productos
   - Aplica cupón de descuento
   - Configura método de pago
   - Procesa venta

3. **Post-Venta**
   - Actualiza stock automáticamente
   - Registra logs de transacción

### 📊 **Flujo de Gestión de Inventario**

1. **Recepción de Mercancía**
   - Crear proveedor
   - Registrar productos nuevos
   - Ajustar stock inicial
   - Asignar imágenes

2. **Control de Stock**
   - Monitorear niveles de inventario
   - Registrar ajustes por devoluciones

3. **Análisis**
   - Consultar ajustes
   - Revisar logs de sistema

## 📚 Colección Completa de Endpoints

### 🏷️ **Categorías**

#### Listar Categorías
```http
GET {{baseUrl}}/categorias
```

#### Obtener Categoría por ID
```http
GET {{baseUrl}}/categorias/{{categoriaId}}
```

#### Crear Categoría
```http
POST {{baseUrl}}/categorias
Content-Type: application/json

{
    "nombre": "Hogar y Jardín",
    "descripcion": "Artículos para el hogar y jardín"
}
```

#### Actualizar Categoría
```http
PUT {{baseUrl}}/categorias/{{categoriaId}}
Content-Type: application/json

{
    "nombre": "Electrónicos Premium",
    "descripcion": "Productos electrónicos de alta gama"
}
```

#### Eliminar Categoría
```http
DELETE {{baseUrl}}/categorias/{{categoriaId}}
```

### 📦 **Productos**

#### Listar Productos
```http
GET {{baseUrl}}/productos
```

#### Obtener Producto por ID
```http
GET {{baseUrl}}/productos/{{productoId}}
```

#### Crear Producto
```http
POST {{baseUrl}}/productos
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

#### Actualizar Producto
```http
PUT {{baseUrl}}/productos/{{productoId}}
Content-Type: application/json

{
  "nombre": "Monitor 27 pulgadas",
  "marca": "LG",
  "precio": 1850000,
  "cantidad": 15,
  "descripcion": "Monitor Full HD con panel IPS y tasa de refresco de 75Hz",
  "categoria": "Monitores"
}
```

#### Eliminar Producto
```http
DELETE {{baseUrl}}/productos/{{productoId}}
```

### 🖼️ **Imágenes de Productos**

#### Listar Todas las Imágenes
```http
GET {{baseUrl}}/api/imgProductos
```

#### Crear Imagen de Producto
```http
POST {{baseUrl}}/api/imgProductos
Content-Type: application/json

{
    "idProducto":2,
    "url":"Url.test",
    "orden":2
}
```

#### Obtener Imagen por ID
```http
GET {{baseUrl}}/api/imgProductos/{{imagenId}}
```

#### Obtener Imágenes por Producto
```http
GET {{baseUrl}}/api/imgProductos/producto/{{productoId}}
```

#### Actualizar Imagen
```http
PUT {{baseUrl}}/api/imgProductos/{{imagenId}}
Content-Type: application/json

{
    "idProducto":2,
    "url":"Url.testNEW",
    "orden":2
}
```

#### Eliminar Imagen
```http
DELETE {{baseUrl}}/api/imgProductos/{{imagenId}}
```

#### Eliminar Imágenes por Producto
```http
DELETE {{baseUrl}}/api/imgProductos/producto/{{productoId}}
```

### 👥 **Clientes**

#### Listar Clientes
```http
GET {{baseUrl}}/api/clientes
```

#### Obtener Cliente por ID
```http
GET {{baseUrl}}/api/clientes/{{clienteId}}
```

#### Crear Cliente
```http
POST {{baseUrl}}/api/clientes
Content-Type: application/json

{
    "nombre":"newName",
    "email":"test",
    "contraseña":"test123",
    "direccion":"av"
}
```

#### Actualizar Cliente
```http
PUT {{baseUrl}}/clientes/{{clienteId}}
Content-Type: application/json

{
    "nombre":"nameActualizado",
    "email":"test",
    "contraseña":"test123",
    "direccion":"av"
}
```

#### Eliminar Cliente
```http
DELETE {{baseUrl}}/clientes/{{clienteId}}
```

### 💰 **Ventas**

#### Listar Ventas
```http
GET {{baseUrl}}/ventas
```

#### Crear Venta (Se valida el carrito del cliente)
```http
POST {{baseUrl}}/ventas
Content-Type: application/json

{
    "idCliente":1,
    "estado":"COMPLETADA",
    "idMetodoPago": 1
    //"codigoCuponAplicado":"DESCUENTO15" //Opcional
}
```

#### Actualizar Venta
```http
PUT {{baseUrl}}/ventas/{{ventaId}}
Content-Type: application/json

{
    "idCliente":2,
    "estado":"CANCELADA"
}
```

### 👤 **Usuarios**
#### Listar Usuarios
```http
GET {{baseUrl}}/api/usuarios
```

#### Obtener Usuario por ID
```http
GET {{baseUrl}}/api/usuarios/{{usuarioId}}
```

#### Crear Usuario
```http
POST {{baseUrl}}/api/usuarios
Content-Type: application/json

{
    "nombre":"{{$randomUserName}}",
    "email":"{{$randomEmail}}",
    "contraseña":"{{$randomPassword}}",
    "rol":"Admin"
}
```

#### Actualizar Usuario
```http
PUT {{baseUrl}}/api/usuarios/{{usuarioId}}
Content-Type: application/json

{
    "nombre":"Pedro",
    "email":"email.pedro",
    "contraseña":"contraseña.pedro",
    "rol":"Admin32"
}
```

#### Eliminar Usuario
```http
DELETE {{baseUrl}}/api/usuarios/{{usuarioId}}
```

### 🛒 **Carrito de Compras**

#### Listar Todos los Carritos
```http
GET {{baseUrl}}/api/carrito
```

#### Crear Carrito
```http
POST {{baseUrl}}/api/carrito
Content-Type: application/json

{
    "idCliente": {{clienteId}}
}
```

#### Actualizar Carrito
```http
POST {{baseUrl}}/api/carrito
Content-Type: application/json

{
    "idCliente": {{clienteIdNEW}}
}
```

#### Obtener Carrito por ID
```http
GET {{baseUrl}}/api/carrito/{{carritoId}}
```

#### Obtener Carrito por Cliente
```http
GET {{baseUrl}}/api/carrito/cliente/{{clienteId}}
```

#### Obtener Total del Carrito
```http
GET {{baseUrl}}/api/carrito/total/{{carritoId}}
```

#### Agregar Item al Carrito
```http
POST {{baseUrl}}/api/carrito/{{carritoId}}/item
Content-Type: application/json

{
    "idProducto": {{productoId}},
}
```

#### Actualizar Cantidad de Item
```http
PUT {{baseUrl}}/api/carrito/{{carritoId}}/item/{{itemId}}
Content-Type: application/json

{
    "cantidad": 3
}
```

#### Eliminar Item del Carrito
```http
DELETE {{baseUrl}}/api/carrito/{{carritoId}}/item/{{itemId}}
```

#### Eliminar Carrito por Cliente
```http
DELETE {{baseUrl}}/api/carrito/cliente/{{clienteId}}
```

#### Eliminar Carrito
```http
DELETE {{baseUrl}}/api/carrito/{{carritoId}}
```

### 🏢 **Proveedores**

#### Listar Proveedores
```http
GET {{baseUrl}}/proveedores
```

#### Obtener Proveedor por ID
```http
GET {{baseUrl}}/proveedores/{{proveedorId}}
```

#### Crear Proveedor
```http
POST {{baseUrl}}/api/proveedores
Content-Type: application/json

{
    "nombre": "GW",
    "contacto": "Jouse31",
    "telefono": "312236432",
    "email": "gw@gmail.com",
    "direccion": "Bogota CR 13 #22"
}
```

#### Actualizar Proveedor
```http
PUT {{baseUrl}}/proveedores/{{proveedorId}}
Content-Type: application/json

{
    "nombre": "GW TEST NEW",
    "contacto": "Jouse TEST",
    "telefono": "312236432",
    "email": "gw@gmail.TEST",
    "direccion": "Bogota CR 13 #22"
}
```

#### Eliminar Proveedor
```http
DELETE {{baseUrl}}/proveedores/{{proveedorId}}
```

### 💳 **Métodos de Pago**

#### Listar Métodos de Pago
```http
GET {{baseUrl}}/api/metodos-pago
```

#### Crear Método de Pago
```http
POST {{baseUrl}}/api/metodos-pago
Content-Type: application/json

{
  "idCliente": 1,
  "tipoPago": "Tarjeta de Crédito", 
  "activo": true,
  "detalles": [
    {"clave": "numeroEnmascarado", "valor": "12312 3231231 32 1234"},
    {"clave": "fechaVencimiento", "valor": "12/25"},
    {"clave": "tipoTarjeta", "valor": "Visa"}
  ]
}
```

#### Obtener Métodos de Pago por Cliente
```http
GET {{baseUrl}}/api/metodos-pago/cliente/{{clienteId}}
```

#### Obtener Detalles de Método de Pago
```http
GET {{baseUrl}}/api/metodos-pago/{{metodoPagoId}}/detalles
```

#### Actualizar Método de Pago
```http
PUT {{baseUrl}}/api/metodos-pago/{{metodoPagoId}}
Content-Type: application/json

{
  //"idCliente": 1,
  "tipoPago": "Tarjeta de Crédito NEW", 
  "activo": true,
  "detalles": [
    {"clave": "numeroEnmascarado NEW", "valor": "090123 120933 12331"},
    {"clave": "fechaVencimiento", "valor": "12/31"},
    {"clave": "tipoTarjeta", "valor": "Visa NEW"}
  ]
}
```

#### Actualizar Estado de Método de Pago
```http
PATCH {{baseUrl}}/api/metodos-pago/{{metodoPagoId}}?estado=false
```

### 🎫 **Cupones de Descuento**

#### Listar Cupones
```http
GET {{baseUrl}}/api/cupones
```

#### Crear Cupón
```http
POST {{baseUrl}}/api/cupones
Content-Type: application/json

{
    //tipo valor: porcentaje ó fijo
    "codigo": "DESCUENTO15",
    "valor": 15.0,
    "tipoValor": "porcentaje"
}
```

#### Obtener Cupón por ID
```http
GET {{baseUrl}}/api/cupones/{{cuponId}}
```

#### Obtener Cupón por Código
```http
GET {{baseUrl}}/api/cupones/codigo/DESCUENTO15
```

#### Obtener Cupones Activos
```http
GET {{baseUrl}}/api/cupones/activos
```

#### Obtener Cupones Válidos
```http
GET {{baseUrl}}/api/cupones/validos
```

#### Actualizar Cupón
```http
PUT {{baseUrl}}/api/cupones/{{cuponId}}
Content-Type: application/json

{
    "codigo": "DESCUENTO20",
    "valor": 20.0,
    "tipoValor": "porcentaje",
    "fechaExpiracion": "2025-12-31",
    "activo": true
}
```

#### Activar Cupón
```http
PATCH {{baseUrl}}/api/cupones/{{cuponId}}/activar
```

#### Desactivar Cupón
```http
PATCH {{baseUrl}}/api/cupones/{{cuponId}}/desactivar
```

#### Eliminar Cupón
```http
DELETE {{baseUrl}}/api/cupones/{{cuponId}}
```

---

## 📊 **APIs de Control**

### 📈 **Ajustes de Stock**

#### Procesar Ajuste de Stock
```http
POST {{baseUrl}}/api/ajustes-stock
Content-Type: application/json

{
    "idProducto": {{productoId}},
    "cantidadAjuste": 10,
    "motivo": "Inventario físico - diferencia encontrada",
    "idUsuario": {{usuarioId}}
}
```

#### Listar Todos los Ajustes
```http
GET {{baseUrl}}/api/ajustes-stock
```

#### Obtener Ajuste por ID
```http
GET {{baseUrl}}/api/ajustes-stock/{{ajusteId}}
```

#### Obtener Ajustes por Producto
```http
GET {{baseUrl}}/api/ajustes-stock/producto/{{productoId}}
```

#### Obtener Ajustes por Usuario
```http
GET {{baseUrl}}/api/ajustes-stock/usuario/{{usuarioId}}
```

#### Actualizar Ajuste
```http
PUT {{baseUrl}}/api/ajustes-stock/{{ajusteId}}
Content-Type: application/json

{
    "idProducto": {{productoId}},
    "cantidadAjuste": 15,
    "motivo": "Corrección por daño de mercancía",
    "idUsuario": {{usuarioId}}
}
```

#### Eliminar Ajuste
```http
DELETE {{baseUrl}}/ajustes-stock/{{ajusteId}}
```

### 📝 **Logs del Sistema**

#### Listar Todos los Logs
```http
GET {{baseUrl}}/api/logs-sistema
```

#### Obtener Logs por ID de Referencia
```http
GET {{baseUrl}}/api/logs-sistema/{{referenciaId}}
```

---

## 📊 **Resumen de Endpoints por Controlador**

| Controlador | Endpoints | Funcionalidades Principales |
|-------------|-----------|------------------------------|
| **Categorías** | 5 | CRUD completo de categorías |
| **Productos** | 6 | Gestión completa + control de stock |
| **Clientes** | 6 | CRUD + búsqueda por email |
| **Ventas** | 5 | Ventas + consultas por cliente/fecha |
| **Usuarios** | 5 | Gestión de usuarios del sistema |
| **Carrito** | 9 | Carrito completo + gestión de items |
| **Proveedores** | 5 | CRUD de proveedores |
| **Métodos de Pago** | 6 | Gestión + detalles de pago |
| **Cupones** | 9 | Gestión completa de descuentos |
| **Ajustes de Stock** | 7 | Control de inventario |
| **Imágenes** | 7 | Gestión de imágenes de productos |
| **Logs** | 2 | Auditoría y monitoreo |

**Total:** 🎯 **72 endpoints** para testing completo

---

🎯 **¡Con esta guía completa tendrás todo lo necesario para probar exhaustivamente los 12 controladores de la API de Inventario usando Postman!**

📚 **Documentación Relacionada:**
- [🚀 Inicio Rápido](quick-start.md)
- [📡 Endpoints de API](api-endpoints.md)
- [🏗️ Arquitectura](architecture.md)
- [📦 Estructura de Paquetes](package-structure.md)

---

**✨ Guía actualizada para incluir todos los controladores: Categorías, Productos, Clientes, Ventas, Usuarios, Carrito, Proveedores, Métodos de Pago, Cupones, Ajustes de Stock, Imágenes y Logs del Sistema ✨**
