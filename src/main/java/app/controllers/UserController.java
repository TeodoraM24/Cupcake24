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

import static app.persistence.OrderMapper.addToBasket;

public class UserController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("logout", ctx -> logout(ctx));
        app.get("createuser", ctx -> ctx.render("createuser.html"));
        app.post("createuser", ctx -> createUser(ctx, connectionPool));
        app.get("orderForm", ctx -> showOrderForm(ctx, connectionPool));
        app.post("/order", ctx -> buyCupcake(ctx, connectionPool));
        app.get("customerpage", ctx -> ctx.render("customerpage.html"));
    }

    private static List<Cupcake> getCupcakes(ConnectionPool connectionPool) throws DatabaseException {
        CupcakeMapper cupcakeMapper = new CupcakeMapper(connectionPool);
        return cupcakeMapper.getAllCupcakes();
    }

    private static Cupcake getCupcakeByDescription(String description, List<Cupcake> cupcakes) throws DatabaseException {
        for (Cupcake cupcake : cupcakes) {
            if (cupcake.getDescription().equalsIgnoreCase(description)) {
                return cupcake;
            }
        }
        throw new DatabaseException("Cupcake not found with description: " + description);
    }

    private static void buyCupcake(Context ctx, ConnectionPool connectionPool) {
        User user = ctx.sessionAttribute("currentUser");
        String cupcakeDescription = ctx.formParam("bottom");
        String toppingDescription = ctx.formParam("topping");
        int quantity = Integer.parseInt(ctx.formParam("amount"));

        try {
            List<Cupcake> cupcakes = getCupcakes(connectionPool); //Henter listen af cupcakes


            Cupcake bottom = getCupcakeByDescription(cupcakeDescription, cupcakes); //Henter info om cupcaken ud fra deres description
            Cupcake topping = getCupcakeByDescription(toppingDescription, cupcakes);

            addToBasket(connectionPool, user, bottom, topping, quantity);

            ctx.render("/orderForm.html");
        } catch (Exception e) {

            ctx.render("error.html");
        }
    }


    private static List<Cupcake> getOrders(Context ctx, ConnectionPool connectionPool) throws DatabaseException {
        CupcakeMapper cupcakeMapper = new CupcakeMapper(connectionPool);
        return cupcakeMapper.getOrderedCupcakes();
    }


    private static void showOrderForm(Context ctx, ConnectionPool connectionPool) { //Laver listen cupcakes
        CupcakeMapper cupcakeMapper = new CupcakeMapper(connectionPool);
        try {
            List<Cupcake> cupcakes = cupcakeMapper.getAllCupcakes();
            ctx.attribute("cupcakes", cupcakes);
        } catch (DatabaseException e) {
            ctx.attribute("error", e.getMessage());
            ctx.render("error.html");
        }
    }

    private static void createUser(Context ctx, ConnectionPool connectionPool) {
        String username = ctx.formParam("username");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");
        String email = ctx.formParam("email");
        String phone = ctx.formParam("phone");

        if (password1.equals(password2)) {
            try {
                UserMapper.createuser(username, password1, email, phone, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet med brugernavn: " + username +
                        ". Nu skal du logge på.");
                ctx.render("WelcomePage.html");
            } catch (DatabaseException e) {
                ctx.attribute("message", "Dit brugernavn findes allerede. Prøv igen, eller log ind");
                ctx.render("createuser.html");
            }
        } else {
            ctx.attribute("message", "Dine to passwords matcher ikke! Prøv igen");
            ctx.render("createuser.html");
        }

    }

    public static void login(Context ctx, ConnectionPool connectionPool) {
        String name = ctx.formParam("username");
        String password = ctx.formParam("password");

        showOrderForm(ctx, connectionPool); //Kalder metoden for at lave listen over cupcakes der skal bruges


        try {
            User user = UserMapper.login(name, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);

            List<Cupcake> cupcakes = getCupcakes(connectionPool);
            ctx.attribute("cupcakes", cupcakes);


            ctx.render("orderForm.html"); //Her bliver man sendt til hjemmesiden når man logger ind
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