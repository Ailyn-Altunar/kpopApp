package org.example.repository;

import org.example.model.Grupo;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GrupoRepository {

    private final DataSource dataSource;

    public GrupoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Grupo> listarTodos() throws SQLException {
        String sql = "SELECT id, nombre FROM grupo ORDER BY nombre ASC";
        List<Grupo> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Grupo(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return lista;
    }
}
