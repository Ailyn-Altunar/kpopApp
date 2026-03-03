package org.example.repository;

import org.example.model.Producto;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepository {

    private final DataSource dataSource;

    public ProductoRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Producto> listarTodos(String busqueda) throws SQLException {
        String sql = "SELECT p.id AS idProducto, p.nombre, g.nombre AS grupo, " +
                     "c.nombre AS categoria, p.precio, p.stock, p.descripcion, p.imagen_url AS imagen " +
                     "FROM producto p " +
                     "LEFT JOIN grupo g ON p.grupo_id = g.id " +
                     "LEFT JOIN categoria c ON p.categoria_id = c.id";

        if (busqueda != null && !busqueda.isBlank()) {
            sql += " WHERE p.nombre LIKE ? OR g.nombre LIKE ? OR c.nombre LIKE ?";
        }
        sql += " ORDER BY p.fecha_creacion DESC";  // FIX: era createdAt

        List<Producto> lista = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (busqueda != null && !busqueda.isBlank()) {
                String like = "%" + busqueda + "%";
                ps.setString(1, like);
                ps.setString(2, like);
                ps.setString(3, like);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    public Producto buscarPorId(int id) throws SQLException {
        String sql = "SELECT p.id AS idProducto, p.nombre, g.nombre AS grupo, " +
                     "c.nombre AS categoria, p.precio, p.stock, p.descripcion, p.imagen_url AS imagen " +
                     "FROM producto p " +
                     "LEFT JOIN grupo g ON p.grupo_id = g.id " +
                     "LEFT JOIN categoria c ON p.categoria_id = c.id " +
                     "WHERE p.id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public int crear(Producto p) throws SQLException {
        // Resolvemos grupo_id y categoria_id por nombre
        String sql = "INSERT INTO producto (nombre, grupo_id, categoria_id, precio, stock, descripcion, imagen_url) " +
                     "VALUES (?, " +
                     "(SELECT id FROM grupo WHERE nombre = ? LIMIT 1), " +
                     "(SELECT id FROM categoria WHERE nombre = ? LIMIT 1), " +
                     "?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getGrupo());
            ps.setString(3, p.getCategoria());
            ps.setBigDecimal(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getDescripcion());
            ps.setString(7, p.getImagen());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        }
        return -1;
    }

    public boolean actualizar(Producto p) throws SQLException {
        String sql = "UPDATE producto SET nombre=?, " +
                     "grupo_id=(SELECT id FROM grupo WHERE nombre=? LIMIT 1), " +
                     "categoria_id=(SELECT id FROM categoria WHERE nombre=? LIMIT 1), " +
                     "precio=?, stock=?, descripcion=?, imagen_url=? " +
                     "WHERE id=?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getGrupo());
            ps.setString(3, p.getCategoria());
            ps.setBigDecimal(4, p.getPrecio());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getDescripcion());
            ps.setString(7, p.getImagen());
            ps.setInt(8, p.getIdProducto());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM producto WHERE id = ?";
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Object[]> contarPorCategoria() throws SQLException {
        String sql = "SELECT c.nombre, COUNT(*) AS total, SUM(p.stock) AS totalStock " +
                     "FROM producto p " +
                     "LEFT JOIN categoria c ON p.categoria_id = c.id " +
                     "GROUP BY c.nombre";
        List<Object[]> resultado = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                resultado.add(new Object[]{
                        rs.getString("nombre"),
                        rs.getInt("total"),
                        rs.getInt("totalStock")
                });
            }
        }
        return resultado;
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getInt("idProducto"));
        p.setNombre(rs.getString("nombre"));
        p.setGrupo(rs.getString("grupo"));
        p.setCategoria(rs.getString("categoria"));
        p.setPrecio(rs.getBigDecimal("precio"));
        p.setStock(rs.getInt("stock"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setImagen(rs.getString("imagen"));
        return p;
    }
}
