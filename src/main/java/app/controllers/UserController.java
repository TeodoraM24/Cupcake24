package app.controllers;

import app.entities.Cupcake;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class UserController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("logout", ctx -> logout(ctx));
        app.get("login", ctx -> ctx.render("login.html"));
        app.get("createuser", ctx -> ctx.render("createuser.html"));
        app.post("createuser", ctx -> createUser(ctx, connectionPool));
    }

    private static void showOrderForm(Context ctx, ConnectionPool connectionPool) { //Laver listen cupcakes, det indenholder cupcakes
        CupcakeMapper cupcakeMapper = new CupcakeMapper(connectionPool);
        try {
            List<Cupcake> cupcakes = cupcakeMapper.getAllCupcakes();
            ctx.attribute("cupcakes", cupcakes);
        } catch (DatabaseException e) {
            ctx.attribute("error", e.getMessage());
            ctx.render("error.html");
        }
    }

    private static void createUser(Context ctx, ConnectionPool connectionPool)
    {
        String username = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");
        String email = ctx.formParam("email");
        String phone = ctx.formParam("phone");

        if (password1.equals(password2))
        {
            try
            {
                UserMapper.createuser(username, password1, email, phone, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet med brugernavn: " + username +
                        ". Nu skal du logge på.");
                ctx.render("WelcomePage.html");
            }

            catch (DatabaseException e)
            {
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen, eller log ind");
                ctx.render("createuser.html");
            }
        } else
        {
            ctx.attribute("message", "Dine to passwords matcher ikke! Prøv igen");
            ctx.render("createuser.html");
        }

    }

    public static void login(Context ctx, ConnectionPool connectionPool){
        String name = ctx.formParam("username");
        String password = ctx.formParam("password");

        showOrderForm(ctx, connectionPool); //Kalder metoden for at lave listen over cupcakes der skal bruges

        try {
            User user = UserMapper.login(name, password,connectionPool);
            ctx.render("orderForm.html"); //skal ændres til den rigtige hjemmeside man logger ind på
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("WelcomePage.html");
        }
    }


    public static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.render("WelcomePage.html");
    }
}
