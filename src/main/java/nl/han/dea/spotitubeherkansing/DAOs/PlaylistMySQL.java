package nl.han.dea.spotitubeherkansing.DAOs;

import nl.han.dea.spotitubeherkansing.DatabaseConnection;
import nl.han.dea.spotitubeherkansing.domains.Playlist;
import nl.han.dea.spotitubeherkansing.interfaces.DAOs.IPlaylistDAO;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistMySQL implements IPlaylistDAO {

    @Inject
    DatabaseConnection databaseConnection;

    public List<Playlist> getAll(){
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("SELECT * FROM playlist");
            ResultSet results = query.executeQuery();

            if (!results.next()) {
                return null;
            }

            List<Playlist> playlists = new ArrayList<>();
            do {
                playlists.add(new Playlist(
                        results.getInt("id"),
                        results.getString("name"),
                        results.getInt("owner")
                ));
            }
            while(results.next());

            return playlists;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Playlist get(int playlistId) {
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("SELECT * FROM playlist WHERE id = ?");
            query.setInt(1, playlistId);
            ResultSet results = query.executeQuery();

            if (!results.next()) {
                return null;
            }
            return new Playlist(
                    playlistId,
                    results.getString("name"),
                    results.getInt("owner")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int PlaylistId) {
        try {
            Connection con = databaseConnection.get();


            PreparedStatement query = con.prepareStatement("DELETE FROM playlist WHERE id = ?");
            query.setInt(1, PlaylistId);

            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(String playlistName, int ownerId) {
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("INSERT INTO playlist(name, owner) VALUES(?, ?)");
            query.setString(1, playlistName);
            query.setInt(2, ownerId);

            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void edit(String newPlaylistName, int playlistId) {
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("UPDATE playlist SET name = ? WHERE id = ?");
            query.setString(1, newPlaylistName);
            query.setInt(2, playlistId);

            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLength(int playlistId) {
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("SELECT SUM(duration) as length FROM track_in_playlist tip INNER JOIN track t ON tip.track_id = t.id WHERE tip.playlist_id = ? GROUP BY playlist_id");
            query.setInt(1, playlistId);
            ResultSet results = query.executeQuery();

            if (!results.next()) {
                return 0;
            }

            return results.getInt("length");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
