package org.example.controller;

import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.example.model.Producto;
import org.example.service.ProductoService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    public void listar(Context ctx) {
        try {
            String busqueda = ctx.queryParam("busqueda");
            List<Producto> productos = service.listar(busqueda);
            ctx.json(productos);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Error al listar productos"));
        }
    }

    public void obtenerPorId(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Producto p = service.obtenerPorId(id);
            if (p == null) ctx.status(404).json(Map.of("error", "Producto no encontrado"));
            else ctx.json(p);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "ID inválido"));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Error al obtener producto"));
        }
    }

    public void crear(Context ctx) {
        try {
            Producto body = ctx.bodyAsClass(Producto.class);

            if (body.getNombre() == null || body.getNombre().isBlank()
                    || body.getGrupo() == null || body.getGrupo().isBlank()
                    || body.getCategoria() == null || body.getCategoria().isBlank()
                    || body.getPrecio() == null) {
                ctx.status(400).json(Map.of("error", "Faltan campos obligatorios: nombre, grupo, categoria, precio"));
                return;
            }

            Producto nuevo = service.crear(
                    body.getNombre(), body.getGrupo(), body.getCategoria(),
                    body.getPrecio(), body.getStock(), body.getDescripcion(),
                    body.getImagen()   // imagen_url desde el JSON
            );

            if (nuevo == null) ctx.status(500).json(Map.of("error", "No se pudo crear el producto"));
            else ctx.status(201).json(nuevo);

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Error al crear producto"));
        }
    }

    public void actualizar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Producto body = ctx.bodyAsClass(Producto.class);

            if (body.getNombre() == null || body.getNombre().isBlank()
                    || body.getGrupo() == null || body.getGrupo().isBlank()
                    || body.getCategoria() == null || body.getCategoria().isBlank()
                    || body.getPrecio() == null) {
                ctx.status(400).json(Map.of("error", "Faltan campos obligatorios"));
                return;
            }

            Producto actualizado = service.actualizar(
                    id, body.getNombre(), body.getGrupo(), body.getCategoria(),
                    body.getPrecio(), body.getStock(), body.getDescripcion()
            );

            if (actualizado == null) ctx.status(404).json(Map.of("error", "Producto no encontrado"));
            else ctx.json(actualizado);

        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "ID inválido"));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Error al actualizar producto"));
        }
    }

    public void subirImagen(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            UploadedFile archivo = ctx.uploadedFile("imagen");

            if (archivo == null) {
                ctx.status(400).json(Map.of("error", "No se recibió ningún archivo (campo: imagen)"));
                return;
            }

            Producto actualizado = service.actualizarImagen(id, archivo);
            if (actualizado == null) ctx.status(404).json(Map.of("error", "Producto no encontrado"));
            else ctx.json(actualizado);

        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "ID inválido"));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Error al subir imagen"));
        }
    }

    public void eliminar(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean eliminado = service.eliminar(id);
            if (eliminado) ctx.json(Map.of("mensaje", "Producto eliminado correctamente"));
            else ctx.status(404).json(Map.of("error", "Producto no encontrado"));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "ID inválido"));
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Error al eliminar producto"));
        }
    }

    public void resumen(Context ctx) {
        try {
            ctx.json(service.resumen());
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Error al obtener resumen"));
        }
    }
}
