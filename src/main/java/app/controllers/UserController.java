package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/signup", ctx -> ctx.render("signup.html"));
        app.post("/signup", ctx -> createUser(ctx, connectionPool));
        app.get("/login", ctx -> ctx.render("login.html"));
        app.post("/login", ctx -> login(ctx, connectionPool));
        app.get("/logout", ctx -> logout(ctx));
    }

    public static void createUser(Context ctx, ConnectionPool connectionPool) {
        String name = ctx.formParam("name");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");
        String email = ctx.formParam("email");
        int phone = Integer.parseInt(ctx.formParam("phone"));
        String role = "customer"; // default role?
        int bank = 0; // default bank value?

        if (password1.equals(password2)) {
            try {
                UserMapper.createUser(name, password1, email, phone, role, bank, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet med brugernavn: " + name + ". Nu skal du logge på.");
                ctx.render("index.html");//change
            } catch (DatabaseException e) {
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen, eller log ind");
                ctx.render("signup.html");
            }
        } else {
            ctx.attribute("message", "Dine to passwords matcher ikke! Prøv igen");
            ctx.render("signup.html");
        }
    }

    public static void login(Context ctx, ConnectionPool connectionPool) {
        ctx.render("index.html");
        String username = ctx.formParam("name");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(username, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);
            ctx.attribute("message", "Du er nu logget ind");
            ctx.render("index"); //change
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("WelcomePage.html");
        }
    }

    public static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.redirect("WelcomePage.html");
    }
}
