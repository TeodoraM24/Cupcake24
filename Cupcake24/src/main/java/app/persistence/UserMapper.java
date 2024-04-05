package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;

public class UserMapper
{

    public static User login(String name, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "select * from users where name=? and password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, name);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int user_id = rs.getInt("user_id");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String role = rs.getString("role");
                int bank = rs.getInt("bank");
                return new User(name, password, email, phone, role, bank, user_id);
            } else
            {
                throw new DatabaseException("Fejl i login. Prøv igen");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static void createuser(String name, String password, String email, String phone, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "insert into users (name, password, email, phone) values (?,?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, email);

            if (phone != null) {
                ps.setString(4, phone);
            } else {
                ps.setNull(4, Types.VARCHAR);
            }

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af ny bruger");
            }
        }
        catch (SQLException e)
        {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value "))
            {
                msg = "Brugernavnet findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }
}