import entities.Admin;
import app.exceptions.DatabaseException;
import persistance.AdminMapper;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class AdminController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/admin", ctx -> ctx.render("admin.html")); // Endpoint for admin dashboard
        app.post("/admin/deposit", ctx -> depositFunds(ctx, connectionPool)); // Endpoint for depositing funds
        app.post("/admin/removeOrder", ctx -> removeOrder(ctx, connectionPool)); // Endpoint for removing orders
        // Add more routes as needed
    }

    private static void depositFunds(Context ctx, ConnectionPool connectionPool) {
        Admin currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser == null || !currentUser.getRole().equals("admin")) {
            ctx.status(401).result("Unauthorized");
            return;
        }

        // Get form parameters
        int customerId = Integer.parseInt(ctx.formParam("customerId"));
        double amount = Double.parseDouble(ctx.formParam("amount"));

        try {
            Admin admin = AdminMapper.getAdminByUsername(currentUser.getUsername(), connectionPool);
            admin.depositFunds(customerId, amount);
            ctx.result("Funds deposited successfully");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error: " + e.getMessage());
        }
    }

    private static void removeOrder(Context ctx, ConnectionPool connectionPool) {
        Admin currentUser = ctx.sessionAttribute("currentUser");
        if (currentUser == null || !currentUser.getRole().equals("admin")) {
            ctx.status(401).result("Unauthorized");
            return;
        }

        // Get form parameter
        int orderId = Integer.parseInt(ctx.formParam("orderId"));

        try {
            Admin admin = AdminMapper.getAdminByUsername(currentUser.getUsername(), connectionPool);
            admin.removeOrder(orderId);
            ctx.result("Order removed successfully");
        } catch (DatabaseException e) {
            ctx.status(500).result("Error: " + e.getMessage());
        }
    }

    // Add more methods for other admin actions as needed
}
