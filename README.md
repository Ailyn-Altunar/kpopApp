# 🎵 Kpop Store 

App de Android para gestionar el inventario de una tienda K-pop.

## ¿Qué puedes hacer?

- Ver todos los productos de la tienda
- Buscar productos por nombre, grupo o categoría
- Agregar nuevos productos con foto, precio y stock
- Editar o eliminar productos existentes
- Ver un resumen general en la pantalla de inicio

## Pantallas

| Pantalla | Descripción |
|----------|-------------|
| Inicio | Resumen con total de productos y stock |
| Inventario | Lista de todos los productos con buscador |
| Agregar Producto | Formulario para registrar un producto nuevo |
| Detalle / Editar | Ver info del producto, editarlo o eliminarlo |

## Tecnologías usadas

**App Android**
- Kotlin + Jetpack Compose

**Servidor (Backend)**
- Java + Javalin
- Base de datos MySQL

## ¿Cómo ejecutar el servidor?

1. Crea un archivo `.env` con los datos de tu base de datos:
```
DB_HOST=localhost
DB_PORT=3306
DB_NAME=kpopstore
DB_USER=tu_usuario
DB_PASS=tu_contraseña
```

2. Ejecuta el servidor:
```bash
./gradlew run
```

3. El servidor queda disponible en `http://localhost:3000`

## ¿Cómo conectar la app?

En el archivo `RetrofitClient.kt` cambia la IP por la de tu computadora:

```kotlin
private const val BASE_URL = "http://TU_IP:3000/"
```

> ⚠️ El teléfono y la computadora deben estar conectados al mismo WiFi.
