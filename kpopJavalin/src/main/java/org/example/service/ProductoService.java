package org.example.service;

import io.javalin.http.UploadedFile;
import org.example.model.Producto;
import org.example.repository.ProductoRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoService {

    private final ProductoRepository repository;
    private final FileService fileService;

    public ProductoService(ProductoRepository repository, FileService fileService) {
        this.repository = repository;
        this.fileService = fileService;
    }

    public List<Producto> listar(String busqueda) {
        try {
            return repository.listarTodos(busqueda);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Producto obtenerPorId(int id) {
        try {
            return repository.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Producto crear(String nombre, String grupo, String categoria,
                          BigDecimal precio, int stock, String descripcion, String imagenUrl) {
        try {
            Producto p = new Producto();
            p.setNombre(nombre);
            p.setGrupo(grupo);
            p.setCategoria(categoria);
            p.setPrecio(precio);
            p.setStock(stock);
            p.setDescripcion(descripcion);
            p.setImagen(imagenUrl);   // puede ser null si no se mandó

            int nuevoId = repository.crear(p);
            if (nuevoId < 0) return null;

            return repository.buscarPorId(nuevoId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Producto actualizar(int id, String nombre, String grupo, String categoria,
                               BigDecimal precio, int stock, String descripcion) {
        try {
            Producto existente = repository.buscarPorId(id);
            if (existente == null) return null;

            existente.setNombre(nombre);
            existente.setGrupo(grupo);
            existente.setCategoria(categoria);
            existente.setPrecio(precio);
            existente.setStock(stock);
            existente.setDescripcion(descripcion);

            boolean ok = repository.actualizar(existente);
            return ok ? repository.buscarPorId(id) : null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Producto actualizarImagen(int id, UploadedFile archivo) {
        try {
            Producto existente = repository.buscarPorId(id);
            if (existente == null) return null;

            fileService.borrarImagen(existente.getImagen());

            String nuevaRuta = fileService.guardarImagenProducto(archivo, id);
            if (nuevaRuta == null) return null;

            existente.setImagen(nuevaRuta);
            repository.actualizar(existente);

            return repository.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean eliminar(int id) {
        try {
            Producto p = repository.buscarPorId(id);
            if (p == null) return false;
            fileService.borrarImagen(p.getImagen());
            return repository.eliminar(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> resumen() {
        try {
            List<Producto> todos = repository.listarTodos(null);
            List<Object[]> porCategoria = repository.contarPorCategoria();

            Map<String, Object> stats = new HashMap<>();
            stats.put("totalProductos", todos.size());
            stats.put("totalStock", todos.stream().mapToInt(Producto::getStock).sum());

            Map<String, Integer> conteoCategoria = new HashMap<>();
            for (Object[] fila : porCategoria) {
                conteoCategoria.put((String) fila[0], (Integer) fila[1]);
            }
            stats.put("porCategoria", conteoCategoria);

            return stats;
        } catch (SQLException e) {
            e.printStackTrace();
            return Map.of("error", "No se pudo obtener el resumen");
        }
    }
}
