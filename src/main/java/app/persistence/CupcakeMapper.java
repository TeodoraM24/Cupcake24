package app.persistence;

import app.entities.Cupcake;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.*;
public class CupcakeMapper {
    public List<Cupcake> getAllCupcakes() throws DatabaseException{
        List<Cupcake> cupcakes = new ArrayList<>();
        ConnectionPool connectionPool = null;

        String sql = "Select * From cupcake.cupcake";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String description = resultSet.getString("description");
                String cupcakepart = resultSet.getString("cupcakepart");
                int price = resultSet.getInt("price");
                int productcode = resultSet.getInt("productcode");
                Cupcake cupcake = new Cupcake(description, cupcakepart, price, productcode);
                cupcakes.add(cupcake);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error occured at cupcake table", e.getMessage());
        }

        return cupcakes;
    }
}