package org.example;

import io.javalin.Javalin;
import io.javalin.http.Header;
import io.javalin.http.staticfiles.Location;
import org.example.config.DBconfig;
import org.example.config.Inicio;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {

        try {
            Files.createDirectories(Paths.get("uploads"));
        } catch (Exception e) {
            System.err.println("No se pudo crear la carpeta uploads: " + e.getMessage());
        }

        Javalin app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
            config.staticFiles.add(staticFiles -> {
                staticFiles.hostedPath = "/uploads";
                staticFiles.directory  = "uploads";
                staticFiles.location   = Location.EXTERNAL;
            });

        }).start(3000);

        app.options("/*", ctx -> {
            String origin = ctx.header("Origin");
            if (origin != null) ctx.header(Header.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            ctx.header(Header.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            ctx.header(Header.ACCESS_CONTROL_ALLOW_HEADERS, "Authorization, Content-Type, X-Requested-With");
            ctx.header(Header.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
            ctx.status(200).result("OK");
        });

        Inicio inicio = new Inicio(DBconfig.getDataSource());
        inicio.inicioProducto().register(app);

        System.out.println("API iniciada en http://localhost:3000");
    }
}