package org.example.controller;

import io.javalin.http.Context;
import org.example.model.Grupo;
import org.example.repository.GrupoRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GrupoController {

    private final GrupoRepository repository;

    public GrupoController(GrupoRepository repository) {
        this.repository = repository;
    }

    public void listar(Context ctx) {
        try {
            List<Grupo> grupos = repository.listarTodos();
            ctx.json(grupos);
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Error al listar grupos"));
        }
    }
}
