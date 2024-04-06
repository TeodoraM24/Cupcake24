package app;

import app.config.ThymeleafConfig;
import app.controllers.UserController;
import app.entities.Cupcake;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.List;

public class Main {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=cupcakeschema";
    private static final String DB = "cupcake";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        CupcakeMapper cupcakeMapper = new CupcakeMapper(connectionPool);

        try {
            System.out.println(cupcakeMapper.getAllCupcakes().size());
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }/*
        app.get("/", ctx -> {
            ctx.attribute("cupcakes", cupcakeMapper.getAllCupcakes()); // Tilføjer listen til javalin så den kan bruges på hjemmesiden
            ctx.render("WelcomePage.html");
        });*/

        app.get("/", ctx -> {
            List<Cupcake> cupcakes = cupcakeMapper.getAllCupcakes();
            System.out.println("Number of cupcakes retrieved: " + cupcakes.size());
            ctx.attribute("cupcakes", cupcakes);
            ctx.render("WelcomePage.html");
        });

        UserController.addRoutes(app, connectionPool);

    }
}
