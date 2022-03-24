package nl.han.dea.spotitubeherkansing.DAOs;

import nl.han.dea.spotitubeherkansing.DatabaseConnection;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.interfaces.DAOs.IUserDAO;
import nl.han.dea.spotitubeherkansing.interfaces.DAOs.IUserTokenDAO;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMySQL implements IUserDAO {

    @Inject
    DatabaseConnection databaseConnection;

    @Inject
    IUserTokenDAO userTokenDAO;

    public User get(String username){
        try {
            Connection con = databaseConnection.get();

        PreparedStatement query = con.prepareStatement("SELECT * FROM user WHERE username = ?");
            query.setString(1, username);
            ResultSet results = query.executeQuery();

            if (!results.next()) {
                throw new UnauthorizedUserException("Invalid username!");
            }

            return new User(
                    results.getInt("id"),
                    username,
                    results.getString("password"),
                    results.getString("name")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getByToken(String token){
        return get(userTokenDAO.getUsernameByToken(token));
    }
}
