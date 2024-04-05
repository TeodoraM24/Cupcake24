
package app.controllers;

import app.entities.User;
import app.entities.Cupcake;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class OrderController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/", ctx -> ctx.render("orderForm.html"));
        app.post("/order", ctx -> addToBasket(ctx, connectionPool));

    }

    private static void addToBasket(Context ctx, ConnectionPool connectionPool) {
        User user = getUserFromSession(ctx);
        Cupcake cupcake = getCupcakeFromRequest(ctx);
        int quantity = getQuantityFromRequest(ctx);
        int totalPrice = calculateTotalPrice(cupcake.getPrice(), quantity);

        try {
            OrderMapper.addToBasket(connectionPool, user, cupcake, quantity, totalPrice);
            ctx.status(201).result("Order added to basket successfully");
        } catch (DatabaseException e) {
            ctx.status(500).result("Failed to add order to basket: " + e.getMessage());
        }
    }


    private static User getUserFromSession(Context ctx) {
        // Retrieve the current user from the session
        User currentUser = ctx.sessionAttribute("currentUser");
        return currentUser;
    }

    private static Cupcake getCupcakeFromRequest(Context ctx) {
        // Implement this method to retrieve cupcake details from the request
        String description = ctx.formParam("description");
        String cupcakepart = ctx.formParam("cupcakepart");
        int price = Integer.parseInt(ctx.formParam("price"));
        int productcode = Integer.parseInt(ctx.formParam("productcode"));
        return new Cupcake(description, cupcakepart, price, productcode);
    }

    private static int getQuantityFromRequest(Context ctx) {
        return Integer.parseInt(ctx.queryParam("quantity"));
    }

    private static int calculateTotalPrice(int price, int quantity) {
        return price * quantity;
    }
}
