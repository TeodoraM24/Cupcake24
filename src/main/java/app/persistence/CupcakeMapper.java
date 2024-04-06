package app.persistence;

import app.entities.Cupcake;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.*;

public class CupcakeMapper {
    private final ConnectionPool connectionPool;

    public CupcakeMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public List<Cupcake> getAllCupcakes() throws DatabaseException {
        List<Cupcake> cupcakes = new ArrayList<>();

        String sql = "SELECT * FROM cupcakeschema.cupcake WHERE cupcakepart = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "Bottom");   //Tilføjer de cupcakes med cupcakepart 'Buttom' til List<Cupcake> cupcakes
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                int productcode = resultSet.getInt("productcode");
                Cupcake cupcake = new Cupcake(description, "Buttom", price, productcode);
                cupcakes.add(cupcake);
            }


            preparedStatement.setString(1, "Topping");  //Samme her bare med 'Topping' for at være sikker på der ikke sniger sig nogle forbudte cupcakes med
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String description = resultSet.getString("description");
                int price = resultSet.getInt("price");
                int productcode = resultSet.getInt("productcode");
                Cupcake cupcake = new Cupcake(description, "Topping", price, productcode);
                cupcakes.add(cupcake);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred at cupcake table", e.getMessage());
        }

        return cupcakes;
    }

}
