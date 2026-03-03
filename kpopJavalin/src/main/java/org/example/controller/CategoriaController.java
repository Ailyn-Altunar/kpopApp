package org.example.controller;

import io.javalin.http.Context;
import org.example.model.Categoria;
import org.example.repository.CategoriaRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CategoriaController {

    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }

    public void listar(Context ctx) {
        try {
            List<Categoria> categorias = repository.listarTodas();
            ctx.json(categorias);
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Error al listar categorías"));
        }
    }
}
