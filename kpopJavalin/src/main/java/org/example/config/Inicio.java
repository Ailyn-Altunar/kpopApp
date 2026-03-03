package org.example.config;

import org.example.controller.CategoriaController;
import org.example.controller.GrupoController;
import org.example.controller.ProductoController;
import org.example.repository.CategoriaRepository;
import org.example.repository.GrupoRepository;
import org.example.repository.ProductoRepository;
import org.example.routers.RouteProducto;
import org.example.service.FileService;
import org.example.service.ProductoService;

import javax.sql.DataSource;

public class Inicio {

    private final DataSource dataSource;

    public Inicio(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public RouteProducto inicioProducto() {
        ProductoRepository productoRepository  = new ProductoRepository(dataSource);
        CategoriaRepository categoriaRepository = new CategoriaRepository(dataSource);
        GrupoRepository grupoRepository         = new GrupoRepository(dataSource);

        FileService fileService          = new FileService();
        ProductoService productoService  = new ProductoService(productoRepository, fileService);

        ProductoController productoController   = new ProductoController(productoService);
        CategoriaController categoriaController = new CategoriaController(categoriaRepository);
        GrupoController grupoController         = new GrupoController(grupoRepository);

        return new RouteProducto(productoController, categoriaController, grupoController);
    }
}
