package org.example.repository;

import org.example.model.Categoria;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository {

    private final DataSource dataSource;

    public CategoriaRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Categoria> listarTodas() throws SQLException {
        String sql = "SELECT id, nombre FROM categoria ORDER BY nombre ASC";
        List<Categoria> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return lista;
    }
}
