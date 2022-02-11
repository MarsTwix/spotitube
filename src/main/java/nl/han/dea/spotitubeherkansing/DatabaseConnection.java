package nl.han.dea.spotitubeherkansing;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private Properties properties;

    public DatabaseConnection() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            Class.forName(properties.getProperty("driver"));
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private String connectionString()
    {
        return properties.getProperty("connectionstring");
    }

    public Connection get() throws SQLException {
        return DriverManager.getConnection(connectionString());
    }

}
