package nl.han.dea.spotitubeherkansing.DAOs;

import nl.han.dea.spotitubeherkansing.DatabaseConnection;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTokenDAO {

    @Inject
    DatabaseConnection databaseConnection;

    public void setUserToken(int id, String token) throws SQLException {
        Connection con = databaseConnection.get();
        PreparedStatement query;

        if(userHasToken(id)){
            query = con.prepareStatement("UPDATE usertoken SET token = ? WHERE user_id = ?");
            query.setString(1, token);
            query.setInt(2, id);
        }
        else{
            query = con.prepareStatement("INSERT INTO usertoken VALUES(?, ?)");
            query.setInt(1, id);
            query.setString(2, token);
        }

        query.executeUpdate();
    }

    public boolean tokenExists(String token) throws SQLException {
        Connection con = databaseConnection.get();
        PreparedStatement query = con.prepareStatement("SELECT * FROM usertoken WHERE token = ?");
        query.setString(1, token);
        ResultSet results = query.executeQuery();

        return results.next();
    }

    public String getUsernameByToken(String token) throws SQLException, UnauthorizedUserException {
        Connection con = databaseConnection.get();
        PreparedStatement query = con.prepareStatement("SELECT username FROM usertoken ut INNER JOIN user u ON u.id = ut.user_id WHERE token = ?");
        query.setString(1, token);
        ResultSet results = query.executeQuery();

        if (!results.next()) {
            throw new UnauthorizedUserException();
        }
        String username = results.getString("username");

        return username;
    }

    private boolean userHasToken(int id) throws SQLException {
        Connection con = databaseConnection.get();
        PreparedStatement query = con.prepareStatement("SELECT * FROM usertoken WHERE user_id = ?");
        query.setInt(1, id);
        ResultSet results = query.executeQuery();

        return results.next();
    }
}
