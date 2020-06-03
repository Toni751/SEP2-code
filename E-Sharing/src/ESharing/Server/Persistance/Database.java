package ESharing.Server.Persistance;

import ESharing.Shared.TransferedObject.Address;
import ESharing.Shared.TransferedObject.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Abstract class which establishes connection with the database
 * @version 1.0
 * @author Group1
 */
public abstract class Database {

    /**
     * No-argument constructor which registers the database driver
     */
    public Database()
    {
        try
        {
            DriverManager.registerDriver(new org.postgresql.Driver());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Establishes connection with the database
     * @return a connection instance with the database
     * @throws SQLException if the database connection cannot be established
     */
    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/sep2",
                "postgres", "password");
    }
}
