package org.example.service;

import io.javalin.http.UploadedFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileService {

    private static final String UPLOAD_DIR = "uploads";

    public String guardarImagenProducto(UploadedFile archivo, int idProducto) {
        try {
            String extension = "";
            int i = archivo.filename().lastIndexOf('.');
            if (i > 0) extension = archivo.filename().substring(i);

            String nombreArchivo = "producto_" + idProducto + "_"
                    + UUID.randomUUID().toString().substring(0, 8) + extension;
            Path rutaDestino = Paths.get(UPLOAD_DIR, nombreArchivo);

            try (InputStream is = archivo.content()) {
                Files.copy(is, rutaDestino, StandardCopyOption.REPLACE_EXISTING);
            }
            return "/uploads/" + nombreArchivo;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void borrarImagen(String rutaRelativa) {
        if (rutaRelativa == null || rutaRelativa.isBlank()) return;
        try {
            String rutaLimpia = rutaRelativa.startsWith("/")
                    ? rutaRelativa.substring(1)
                    : rutaRelativa;
            Path rutaArchivo = Paths.get(rutaLimpia);
            File archivo = rutaArchivo.toFile();
            if (archivo.exists()) {
                if (archivo.delete()) {
                    System.out.println("Imagen eliminada: " + rutaLimpia);
                } else {
                    System.err.println("No se pudo eliminar: " + rutaLimpia);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al borrar imagen: " + e.getMessage());
        }
    }
}
