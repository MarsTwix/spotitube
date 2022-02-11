package nl.han.dea.spotitubeherkansing.mappers;

import nl.han.dea.spotitubeherkansing.DatabaseConnection;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    @Inject
    DatabaseConnection databaseConnection;

    public User find(String username) throws SQLException, UnauthorizedUserException {
            Connection con = databaseConnection.get();
            PreparedStatement query = con.prepareStatement("SELECT * FROM user WHERE username = ?");
            query.setString(1, username);
            ResultSet results = query.executeQuery();

            if (!results.next()) {
                throw new UnauthorizedUserException();
            }
            int id = results.getInt("id");
            String password = results.getString("password");
            String name = results.getString("name");

            return new User(id, username, password, name);
    }
}
