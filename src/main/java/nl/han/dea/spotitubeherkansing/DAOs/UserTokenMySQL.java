package nl.han.dea.spotitubeherkansing.DAOs;

import nl.han.dea.spotitubeherkansing.DatabaseConnection;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.interfaces.DAOs.IUserTokenDAO;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTokenMySQL implements IUserTokenDAO {

    @Inject
    DatabaseConnection databaseConnection;

    public void setUserToken(int UserId, String token) {
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query;

            if(userHasToken(UserId)){
                query = con.prepareStatement("UPDATE usertoken SET token = ? WHERE user_id = ?");
                query.setString(1, token);
                query.setInt(2, UserId);
            }
            else{
                query = con.prepareStatement("INSERT INTO usertoken VALUES(?, ?)");
                query.setInt(1, UserId);
                query.setString(2, token);
            }

            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean tokenExists(String token) {
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("SELECT * FROM usertoken WHERE token = ?");
            query.setString(1, token);
            ResultSet results = query.executeQuery();

            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsernameByToken(String token) {
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("SELECT username FROM usertoken ut INNER JOIN user u ON u.id = ut.user_id WHERE token = ?");
            query.setString(1, token);
            ResultSet results = query.executeQuery();

            if (!results.next()) {
                throw new UnauthorizedUserException("Invalid token!");
            }

            return results.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean userHasToken(int userId) {
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("SELECT * FROM usertoken WHERE user_id = ?");
            query.setInt(1, userId);
            ResultSet results = query.executeQuery();

            return results.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
