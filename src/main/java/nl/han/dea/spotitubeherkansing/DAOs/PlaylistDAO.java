package nl.han.dea.spotitubeherkansing.DAOs;

import nl.han.dea.spotitubeherkansing.DatabaseConnection;
import nl.han.dea.spotitubeherkansing.domains.Playlist;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    @Inject
    DatabaseConnection databaseConnection;

    public List<Playlist> getAll() throws SQLException {
        Connection con = databaseConnection.get();

        PreparedStatement query = con.prepareStatement("SELECT * FROM playlist");
        ResultSet results = query.executeQuery();

        if (!results.next()) {
            return null;
        }

        List<Playlist> playlists = new ArrayList<>();
        do {
            int id = results.getInt("id");
            String name = results.getString("name");
            int owner = results.getInt("owner");
            playlists.add(new Playlist(id,name,owner));
        }
        while(results.next());

        return playlists;
    }

    public Playlist get(int id) throws SQLException {
        Connection con = databaseConnection.get();

        PreparedStatement query = con.prepareStatement("SELECT * FROM playlist WHERE id = ?");
        query.setInt(1, id);
        ResultSet results = query.executeQuery();

        if (!results.next()) {
            return null;
        }

        String name = results.getString("name");
        int owner = results.getInt("owner");

        return new Playlist(id,name,owner);
    }

    public void delete(int id) throws SQLException {
        Connection con = databaseConnection.get();

        PreparedStatement query = con.prepareStatement("DELETE FROM playlist WHERE id = ?");
        query.setInt(1, id);

        query.executeUpdate();
    }

    public void add(String name, int id) throws SQLException {
        Connection con = databaseConnection.get();

        PreparedStatement query = con.prepareStatement("INSERT INTO playlist(name, owner) VALUES(?, ?)");
        query.setString(1, name);
        query.setInt(2, id);

        query.executeUpdate();
    }

    public void edit(String name, int id) throws SQLException {
        Connection con = databaseConnection.get();

        PreparedStatement query = con.prepareStatement("UPDATE playlist SET name = ? WHERE id = ?");
        query.setString(1, name);
        query.setInt(2, id);

        query.executeUpdate();
    }
}
