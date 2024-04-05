package app;

import app.config.ThymeleafConfig;
import app.controllers.UserController;
import app.entities.User;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

public class Main
{
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=cupcakeschema";
    private static final String DB = "cupcake"; // ÆNDRE DB TIL VORES

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args)
    {

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        app.get("/", ctx -> ctx.render("WelcomePage.html"));
        UserController.addRoutes(app, connectionPool);

    }
}
