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

### Clientes (`/api/clientes`)

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

### Proveedores (`/proveedores`)

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

### Productos (`/api/productos`)

*(Nota: El controlador de productos parece estar incompleto en el código fuente actual. Los endpoints deberán ser implementados)*

## Pruebas con Postman

Puedes usar Postman para probar los endpoints de la API:

1.  **Inicia la aplicación** como se describe en la sección "Ejecución del Proyecto".
2.  Abre Postman.
3.  Crea una nueva solicitud:
    *   **Método:** Selecciona el método HTTP correspondiente (GET, POST, PUT, DELETE).
    *   **URL:** Ingresa la URL completa del endpoint, por ejemplo: `http://localhost:8080/api/clientes` o `http://localhost:8080/proveedores/1`.
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