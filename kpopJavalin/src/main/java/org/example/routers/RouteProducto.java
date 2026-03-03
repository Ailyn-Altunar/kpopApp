package org.example.routers;

import io.javalin.Javalin;
import org.example.controller.CategoriaController;
import org.example.controller.GrupoController;
import org.example.controller.ProductoController;

public class RouteProducto {

    private final ProductoController controller;
    private final CategoriaController categoriaController;
    private final GrupoController grupoController;

    public RouteProducto(ProductoController controller,
                         CategoriaController categoriaController,
                         GrupoController grupoController) {
        this.controller = controller;
        this.categoriaController = categoriaController;
        this.grupoController = grupoController;
    }

    public void register(Javalin app) {
        app.get("/api/productos/resumen", controller::resumen);

        app.get   ("/api/productos",        controller::listar);
        app.get   ("/api/productos/{id}",   controller::obtenerPorId);
        app.post  ("/api/productos",        controller::crear);
        app.put   ("/api/productos/{id}",   controller::actualizar);
        app.delete("/api/productos/{id}",   controller::eliminar);

        app.post("/api/productos/{id}/imagen", controller::subirImagen);

        app.get("/api/categorias", categoriaController::listar);
        app.get("/api/grupos",     grupoController::listar);
    }
}
