package entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
    private String name;
    private String email;
    private int phone;
    private String role;
    private int userid;
    private String password;
    private Connection connection;

    public Admin(String name, String email, int phone, String role, int userid, String password, Connection connection) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.userid = userid;
        this.password = password;
        this.connection = connection;
    }


    public void depositFunds(int customerId, double amount) {
        try {
            // SQL-forespørgsel til at opdatere kundens saldo
            String sql = "UPDATE customers SET balance = balance + ? WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, amount);
            statement.setInt(2, customerId);
            statement.executeUpdate();
            System.out.println( amount + " blev indsat på kundens konto.");
        } catch (SQLException e) {
            System.err.println("Fejl ved indsættelse af penge på kundens konto: " + e.getMessage());
        }
    }

    public void viewAllOrders() {
        try {
            // SQL-forespørgsel til at hente alle ordrer
            String sql = "SELECT * FROM orders";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                int customerId = resultSet.getInt("customer_id");
                // Hent og udskriv andre relevante kolonner
                System.out.println("Order ID: " + orderId + ", Customer ID: " + customerId);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Fejl ved visning af ordrer: " + e.getMessage());
        }
    }

    // Metode til at se alle kunder og deres ordrer i systemet
    public void viewAllCustomersAndOrders() {
        try {
            // SQL-forespørgsel til at hente alle kunder og deres ordrer
            String sql = "SELECT customers.customer_id, customers.name AS customer_name, orders.order_id, orders.order_date " +
                    "FROM customers LEFT JOIN orders ON customers.customer_id = orders.customer_id";

            // Opret PreparedStatement
            PreparedStatement statement = connection.prepareStatement(sql);

            // Udfør forespørgslen og få resultatet
            ResultSet resultSet = statement.executeQuery();

            // Her udskrives resultatet
            while (resultSet.next()) {
                int customerId = resultSet.getInt("customer_id");
                String customerName = resultSet.getString("customer_name");
                int orderId = resultSet.getInt("order_id");
                String orderDate = resultSet.getString("order_date");
                System.out.println("Customer ID: " + customerId + ", Customer Name: " + customerName +
                        ", Order ID: " + orderId + ", Order Date: " + orderDate);
            }

            // Luk ressourcer
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            System.err.println("Fejl ved visning af kunder og deres ordrer: " + e.getMessage());
        }
    }

    // Metode til at fjerne en ordre baseret på dens ID
    public void removeOrder(int orderId) {
        try {
            // SQL-forespørgsel til at slette en ordre
            String sql = "DELETE FROM orders WHERE order_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, orderId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ordre med ID " + orderId + " er blevet fjernet fra systemet.");
            } else {
                System.out.println("Kunne ikke finde ordre med ID " + orderId + ".");
            }
            statement.close();
        } catch (SQLException e) {
            System.err.println("Fejl ved fjernelse af ordre: " + e.getMessage());
        }
    }
}
