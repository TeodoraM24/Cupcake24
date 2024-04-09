import entities.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper {
    private Connection connection;

    public AdminMapper(Connection connection) {
        this.connection = connection;
    }

    public Admin findByUsernameAndPassword(String username, String password) {
        try {
            String sql = "SELECT * FROM admins WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int phone = resultSet.getInt("phone");
                String role = resultSet.getString("role");
                int userid = resultSet.getInt("userid");
                // Hvis en admin blev fundet, opret en Admin-objekt og returner det
                return new Admin(name, email, phone, role, userid, username, password, connection);
            }
        } catch (SQLException e) {
            System.err.println("Fejl ved opslag af admin: " + e.getMessage());
        }
        return null; // Returner null, hvis ingen admin blev fundet med det givne brugernavn og adgangskode
    }
}
