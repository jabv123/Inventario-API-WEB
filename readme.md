# Inventario API WEB

API REST simple para gestionar un inventario básico, incluyendo Clientes, Productos y Proveedores. Construido con Java y el framework Spark.

## Prerrequisitos

Asegúrate de tener instalado lo siguiente:

*   **Java Development Kit (JDK):** Versión 17 o superior. Puedes verificar tu versión con `java -version`.
*   **Apache Maven:** Para compilar y ejecutar el proyecto. Puedes verificar tu versión con `mvn -version`.

## Instalación

1.  Clona este repositorio o descarga el código fuente:
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd Inventario-API-WEB
    ```

## Ejecución del Proyecto

1.  Abre una terminal en el directorio raíz del proyecto (`Inventario-API-WEB`).
2.  Compila el proyecto usando Maven:
    ```bash
    mvn clean package
    ```
    Esto generará un archivo JAR ejecutable en el directorio `target/`.
3.  Ejecuta la aplicación:
    ```bash
    java -jar target/Inventario-API-WEB-1.0-SNAPSHOT.jar
    ```
    El servidor se iniciará y escuchará en el puerto `8080`. Verás un mensaje en la consola indicando que el servidor está listo: `Servidor Spark iniciado y escuchando en el puerto 8080`.

## API Endpoints

La API expone los siguientes endpoints:

### Área 1: Productos y Catálogo

#### Productos (`/productos`)

*   **`POST /productos`**: Crea un nuevo producto.
    *   **Request Body:**
        ```json
        {
          "nombre": "Nombre Producto",
          "descripcion": "Descripción del producto",
          "precio": 19.99,
          "stock": 100,
          "idCategoria": 1 // ID de la categoría a la que pertenece
        }
        ```
    *   **Respuesta Exitosa (201 Created):**
        ```json
        {
          "mensaje": "Producto agregado",
          "objeto": {
            "id": 1, // ID asignado automáticamente
            "nombre": "Nombre Producto",
            "descripcion": "Descripción del producto",
            "precio": 19.99,
            "stock": 100,
            "idCategoria": 1
          }
        }
        ```
    *   **Respuesta Error (400 Bad Request):**
        ```json
        {
          "mensaje": "Error al crear producto",
          "objeto": "Mensaje de error detallado"
        }
        ```
*   **`GET /productos`**: Lista todos los productos.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Lista de productos",
          "objeto": [
            {
              "id": 1,
              "nombre": "Nombre Producto",
              "descripcion": "Descripción del producto",
              "precio": 19.99,
              "stock": 100,
              "idCategoria": 1
            }
            // ... más productos
          ]
        }
        ```
*   **`GET /productos/{id}`**: Obtiene un producto específico por su ID.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Producto encontrado",
          "objeto": {
            "id": 1,
            "nombre": "Nombre Producto",
            "descripcion": "Descripción del producto",
            "precio": 19.99,
            "stock": 100,
            "idCategoria": 1
          }
        }
        ```
    *   **Respuesta Error (404 Not Found):**
        ```json
        {
          "mensaje": "Producto no encontrado",
          "objeto": null
        }
        ```
    *   **Respuesta Error (400 Bad Request):** (Si el ID es inválido)
        ```json
        {
          "mensaje": "ID de producto inválido",
          "objeto": "{id}"
        }
        ```
*   **`PUT /productos/{id}`**: Actualiza un producto existente por su ID.
    *   **Request Body:**
        ```json
        {
          "nombre": "Producto Actualizado",
          "descripcion": "Descripción actualizada",
          "precio": 25.50,
          "stock": 90,
          "idCategoria": 2
        }
        ```
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Producto actualizado",
          "objeto": {
            "id": 1, // ID del producto actualizado
            "nombre": "Producto Actualizado",
            "descripcion": "Descripción actualizada",
            "precio": 25.50,
            "stock": 90,
            "idCategoria": 2
          }
        }
        ```
    *   **Respuesta Error (404 Not Found):**
        ```json
        {
          "mensaje": "Producto no encontrado para actualizar",
          "objeto": null
        }
        ```
    *   **Respuesta Error (400 Bad Request):** (Si el ID es inválido o hay error en el body)
        ```json
        {
          "mensaje": "ID de producto inválido", // o "Error al actualizar producto"
          "objeto": "{id}" // o mensaje de error
        }
        ```
*   **`DELETE /productos/{id}`**: Elimina un producto por su ID.
    *   **Respuesta Exitosa (204 No Content):** (Sin cuerpo de respuesta)
    *   **Respuesta Error (404 Not Found):**
        ```json
        {
          "mensaje": "Producto no encontrado para eliminar",
          "objeto": null
        }
        ```
    *   **Respuesta Error (400 Bad Request):** (Si el ID es inválido)
        ```json
        {
          "mensaje": "ID de producto inválido",
          "objeto": "{id}"
        }
        ```

#### Proveedores (`/proveedores`)

*   **`GET /proveedores`**: Lista todos los proveedores.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Lista de proveedores",
          "objeto": [
            {
              "id": 1,
              "nombre": "Proveedor Uno",
              "direccion": "Calle Falsa 123",
              "telefono": "555-1111"
            }
            // ... más proveedores
          ]
        }
        ```
*   **`GET /proveedores/{id}`**: Obtiene un proveedor específico por su ID.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Proveedor encontrado",
          "objeto": {
            "id": 1,
            "nombre": "Proveedor Uno",
            "direccion": "Calle Falsa 123",
            "telefono": "555-1111"
          }
        }
        ```
    *   **Respuesta Error (404 Not Found):**
        ```json
        {
          "mensaje": "Proveedor no encontrado",
          "objeto": null
        }
        ```
*   **`POST /proveedores`**: Crea un nuevo proveedor.
    *   **Request Body:**
        ```json
        {
          "nombre": "Nuevo Proveedor",
          "direccion": "Avenida Siempre Viva 742",
          "telefono": "555-2222"
        }
        ```
    *   **Respuesta Exitosa (201 Created):**
        ```json
        {
          "mensaje": "Proveedor agregado",
          "objeto": {
            "id": 2, // ID asignado automáticamente
            "nombre": "Nuevo Proveedor",
            "direccion": "Avenida Siempre Viva 742",
            "telefono": "555-2222"
          }
        }
        ```
*   **`PUT /proveedores/{id}`**: Actualiza un proveedor existente por su ID.
    *   **Request Body:**
        ```json
        {
          "nombre": "Proveedor Actualizado",
          "direccion": "Dirección Cambiada 456",
          "telefono": "555-3333"
        }
        ```
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Proveedor actualizado",
          "objeto": {
            "id": 1, // ID del proveedor actualizado
            "nombre": "Proveedor Actualizado",
            "direccion": "Dirección Cambiada 456",
            "telefono": "555-3333"
          }
        }
        ```
    *   **Respuesta Error (404 Not Found):**
        ```json
        {
          "mensaje": "Proveedor no encontrado para actualizar",
          "objeto": null
        }
        ```
*   **`DELETE /proveedores/{id}`**: Elimina un proveedor por su ID.
    *   **Respuesta Exitosa (204 No Content):** (Sin cuerpo de respuesta)
    *   **Respuesta Error (404 Not Found):**
        ```json
        {
          "mensaje": "Proveedor no encontrado para eliminar",
          "objeto": null
        }
        ```

#### Imágenes de Productos (`/api/imgProductos`)

*   **`GET /api/imgProductos`**: Lista todas las imágenes de productos.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Imagenes de productos cargados",
          "objeto": [
            {
              "id": 1,
              "idProducto": 101,
              "url": "http://example.com/imagen1.jpg"
            },
            {
              "id": 2,
              "idProducto": 102,
              "url": "http://example.com/imagen2.png"
            }
            // ... más imágenes
          ]
        }
        ```
    *   **Respuesta Error (404 Not Found):** `No hay imagenes de productos registrados`
*   **`GET /api/imgProductos/{id}`**: Obtiene una imagen de producto específica por su ID.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Imagen cargada",
          "objeto": {
            "id": 1,
            "idProducto": 101,
            "url": "http://example.com/imagen1.jpg"
          }
        }
        ```
    *   **Respuesta Error (404 Not Found):** `No hay imagenes con el id: {id}`
*   **`GET /api/imgProductos/producto/{id}`**: Obtiene todas las imágenes asociadas a un producto por el ID del producto.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Imagenes de producto {idProducto} cargadas",
          "objeto": [
            {
              "id": 1,
              "idProducto": 101,
              "url": "http://example.com/imagen1.jpg"
            },
            {
              "id": 3,
              "idProducto": 101,
              "url": "http://example.com/imagen3.gif"
            }
            // ... más imágenes para el producto
          ]
        }
        ```
    *   **Respuesta Error (404 Not Found):** `No hay imagenes para el producto con id: {idProducto}`
*   **`POST /api/imgProductos`**: Crea una nueva imagen de producto.
    *   **Request Body:**
        ```json
        {
          "idProducto": 103,
          "url": "http://example.com/nueva_imagen.jpeg"
        }
        ```
    *   **Respuesta Exitosa (201 Created):**
        ```json
        {
          "mensaje": "Imagen de producto creada",
          "objeto": {
            "id": 4, // ID asignado automáticamente
            "idProducto": 103,
            "url": "http://example.com/nueva_imagen.jpeg"
          }
        }
        ```
*   **`PUT /api/imgProductos/{id}`**: Actualiza una imagen de producto existente por su ID.
    *   **Request Body:**
        ```json
        {
          "idProducto": 101, // Puede o no ser necesario actualizar el idProducto
          "url": "http://example.com/imagen_actualizada.jpg"
        }
        ```
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Imagen de producto actualizada",
          "objeto": {
            "id": 1, // ID de la imagen actualizada
            "idProducto": 101,
            "url": "http://example.com/imagen_actualizada.jpg"
          }
        }
        ```
    *   **Respuesta Error (404 Not Found):** `No hay imagenes de productos registradas`
*   **`DELETE /api/imgProductos/{id}`**: Elimina una imagen de producto por su ID.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Imagen de producto eliminada",
          "objeto": true
        }
        ```
    *   **Respuesta Error (404 Not Found):** `No hay imagenes de productos registradas`
*   **`DELETE /api/imgProductos/producto/{id}`**: Elimina todas las imágenes asociadas a un producto por el ID del producto.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Imagenes de producto eliminadas",
          "objeto": true
        }
        ```
    *   **Respuesta Error (404 Not Found):** `No hay imagenes de productos registradas para el producto con id: {idProducto}`

### Área 2: Clientes y Usuarios

#### Clientes (`/api/clientes`)

*   **`GET /api/clientes`**: Lista todos los clientes.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Clientes listados correctamente",
          "objeto": [
            {
              "idCliente": 1,
              "nombre": "Nombre Cliente",
              "apellido": "Apellido Cliente",
              "direccion": "Dirección Cliente",
              "telefono": "1234567890",
              "email": "cliente@example.com"
            }
            // ... más clientes
          ]
        }
        ```
*   **`GET /api/clientes/{id}`**: Obtiene un cliente específico por su ID.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Cliente listado correctamente",
          "objeto": {
            "idCliente": 1,
            "nombre": "Nombre Cliente",
            "apellido": "Apellido Cliente",
            "direccion": "Dirección Cliente",
            "telefono": "1234567890",
            "email": "cliente@example.com"
          }
        }
        ```
    *   **Respuesta Error (404 Not Found):** `Cliente no encontrado`
*   **`POST /api/clientes`**: Crea un nuevo cliente.
    *   **Request Body:**
        ```json
        {
          "nombre": "Nuevo Cliente",
          "apellido": "Apellido Nuevo",
          "direccion": "Dirección Nueva",
          "telefono": "0987654321",
          "email": "nuevo@example.com"
        }
        ```
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Cliente creado correctamente",
          "objeto": {
            "idCliente": 2, // ID asignado automáticamente
            "nombre": "Nuevo Cliente",
            "apellido": "Apellido Nuevo",
            "direccion": "Dirección Nueva",
            "telefono": "0987654321",
            "email": "nuevo@example.com"
          }
        }
        ```
*   **`PUT /api/clientes/{idCliente}`**: Actualiza un cliente existente por su ID.
    *   **Request Body:**
        ```json
        {
          "nombre": "Cliente Actualizado",
          "apellido": "Apellido Actualizado",
          "direccion": "Dirección Actualizada",
          "telefono": "1122334455",
          "email": "actualizado@example.com"
        }
        ```
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Cliente actualizado correctamente",
          "objeto": {
            "idCliente": 1, // ID del cliente actualizado
            "nombre": "Cliente Actualizado",
            "apellido": "Apellido Actualizado",
            "direccion": "Dirección Actualizada",
            "telefono": "1122334455",
            "email": "actualizado@example.com"
          }
        }
        ```
    *   **Respuesta Error (404 Not Found):** `Cliente no encontrado`
*   **`DELETE /api/clientes/{idCliente}`**: Elimina un cliente por su ID.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Cliente eliminado correctamente",
          "objeto": {
             // Detalles del cliente eliminado (o null/vacío dependiendo de la implementación)
          }
        }
        ```
    *   **Respuesta Error (404 Not Found):** `Cliente no encontrado`

#### Usuarios (`/api/usuarios`)

*   **`GET /api/usuarios`**: Lista todos los usuarios.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Usuarios listados correctamente",
          "objeto": [
            {
              "idUsuario": 1,
              "nombreUsuario": "usuario1",
              "rol": "admin" // Ejemplo, ajustar según modelo real
              // ... otros campos del usuario
            }
            // ... más usuarios
          ]
        }
        ```
    *   **Respuesta Error (404 Not Found):** `No hay usuarios registrados`
*   **`GET /api/usuarios/{id}`**: Obtiene un usuario específico por su ID.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Usuario encontrado",
          "objeto": {
            "idUsuario": 1,
            "nombreUsuario": "usuario1",
            "rol": "admin" // Ejemplo
            // ... otros campos del usuario
          }
        }
        ```
    *   **Respuesta Error (404 Not Found):** `Usuario no encontrado`
*   **`POST /api/usuarios`**: Crea un nuevo usuario.
    *   **Request Body:**
        ```json
        {
          "nombreUsuario": "nuevoUsuario",
          "contrasena": "secreto", // Asegúrate de manejar esto de forma segura
          "rol": "user" // Ejemplo
          // ... otros campos necesarios
        }
        ```
    *   **Respuesta Exitosa (201 Created):**
        ```json
        {
          "mensaje": "Usuario creado correctamente",
          "objeto": {
            "idUsuario": 2, // ID asignado automáticamente
            "nombreUsuario": "nuevoUsuario",
            "rol": "user" // Ejemplo
            // ... otros campos del usuario creado
          }
        }
        ```
*   **`PUT /api/usuarios/{id}`**: Actualiza un usuario existente por su ID.
    *   **Request Body:**
        ```json
        {
          "nombreUsuario": "usuarioActualizado",
          "rol": "editor" // Ejemplo
          // ... otros campos a actualizar
        }
        ```
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Usuario actualizado correctamente",
          "objeto": {
            "idUsuario": 1, // ID del usuario actualizado
            "nombreUsuario": "usuarioActualizado",
            "rol": "editor" // Ejemplo
            // ... otros campos actualizados
          }
        }
        ```
    *   **Respuesta Error (404 Not Found):** `Usuario no encontrado`
*   **`DELETE /api/usuarios/{id}`**: Elimina un usuario por su ID.
    *   **Respuesta Exitosa (200 OK):**
        ```json
        {
          "mensaje": "Usuario eliminado correctamente",
          "objeto": true
        }
        ```
    *   **Respuesta Error (404 Not Found):** `Usuario no encontrado`

## Pruebas con Postman

Puedes usar Postman para probar los endpoints de la API. También puedes unirte a nuestro workspace de Postman para tener acceso a una colección preconfigurada:

*   **Únete al Workspace de Postman:** [Enlace de Invitación](https://app.getpostman.com/join-team?invite_code=27761a4d74998519608ce0a722f7bff5ee0936cdf51ab64921f9e215d47ba0c6&target_code=ded9bd18a3ed59b49d5d4e94c2d79eea)

Si prefieres configurar las solicitudes manualmente:

1.  **Inicia la aplicación** como se describe en la sección "Ejecución del Proyecto".
2.  Abre Postman.
3.  Crea una nueva solicitud:
    *   **Método:** Selecciona el método HTTP correspondiente (GET, POST, PUT, DELETE).
    *   **URL:** Ingresa la URL completa del endpoint, por ejemplo: `http://localhost:8080/productos` o `http://localhost:8080/proveedores/1`.
    *   **Headers (para POST y PUT):** Asegúrate de que el header `Content-Type` esté configurado como `application/json`.
    *   **Body (para POST y PUT):** Selecciona la pestaña `Body`, elige `raw` y selecciona `JSON` en el menú desplegable. Pega el cuerpo JSON de la solicitud como se muestra en los ejemplos de los endpoints.
4.  Haz clic en **Send**.
5.  Observa la respuesta recibida en la parte inferior (Status Code, Body, Headers).

**Ejemplo de solicitud POST para crear un cliente en Postman:**

*   **Método:** `POST`
*   **URL:** `http://localhost:8080/api/clientes`
*   **Headers:** `Content-Type: application/json`
*   **Body (raw, JSON):**
    ```json
    {
      "nombre": "Cliente Postman",
      "apellido": "Prueba",
      "direccion": "Calle Postman 1",
      "telefono": "9876543210",
      "email": "postman@example.com"
    }
    ```

Repite estos pasos para probar los diferentes métodos y endpoints de la API.