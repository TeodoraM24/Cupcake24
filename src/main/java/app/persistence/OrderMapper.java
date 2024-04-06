package app.persistence;

import app.entities.Order;
import app.entities.Cupcake;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static void addToBasket(ConnectionPool connectionPool, User user, Cupcake cupcake, int quantity, int totalPrice) throws DatabaseException {
        String sql = "INSERT INTO cupcakeschema.orderdetails (productcode, quantityorder, pricheach, user_id) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            //Har fjernet ordernumber da DB selv laver et unikt nummer
            ps.setInt(1, cupcake.getProductcode());
            ps.setInt(2, quantity);
            ps.setInt(3, cupcake.getPrice());
            ps.setInt(4, user.getUser_id());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new DatabaseException("Failed to add order to basket");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error adding order to basket", e.getMessage());
        }
    }
/*
    public static List<Order> getPreviousOrders(ConnectionPool connectionPool, User user) throws DatabaseException {
        List<Order> previousOrders = new ArrayList<>();
        String sql = "SELECT od.ordernumber, od.productcode, od.quantityorder, od.pricheach " +
                "FROM cupcakeschema.orderdetails od " +
                "JOIN cupcakeschema.\"order\" o ON od.ordernumber = o.ordernumber " +
                "WHERE o.user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, user.getUser_id());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderNumber = rs.getInt("ordernumber");
                int productCode = rs.getInt("productcode");
                int quantity = rs.getInt("quantityorder");
                int priceEach = rs.getInt("pricheach");

                // details here using productCode
                // object + add to the list
                Order order = new Order(orderNumber, null, priceEach, productCode, quantity);
                previousOrders.add(order);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error retrieving previous orders", e.getMessage());
        }

        return previousOrders;
    }*/

    //  unique order number
    private static int generateOrderNumber() {

        return 0;
    }
}