package nl.han.dea.spotitubeherkansing.DAOs;

import nl.han.dea.spotitubeherkansing.DatabaseConnection;
import nl.han.dea.spotitubeherkansing.domains.Track;
import nl.han.dea.spotitubeherkansing.interfaces.DAOs.ITrackDAO;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackMySQL implements ITrackDAO {

    @Inject
    DatabaseConnection databaseConnection;

    public List<Track> getAll(int playlistId){
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("SELECT t.*, tip.offlineAvailable FROM track_in_playlist tip INNER JOIN track t ON tip.track_id = t.id WHERE playlist_id = ?");
            query.setInt(1, playlistId);
            ResultSet results = query.executeQuery();

            if (!results.next()) {
                return new ArrayList<>();
            }

            List<Track> tracks = new ArrayList<>();
            do {
                tracks.add(convertToTrack(results));
            }while(results.next());

            return tracks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Track> getAvailable(int playlistId) {
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("SELECT t.*, 0 as offlineAvailable FROM track t WHERE NOT EXISTS(SELECT null FROM track_in_playlist tip where t.id = tip.track_id AND playlist_id = ?)");
            query.setInt(1, playlistId);
            ResultSet results = query.executeQuery();

            if (!results.next()) {
                return new ArrayList<>();
            }

            List<Track> tracks = new ArrayList<>();
            do {
                tracks.add(convertToTrack(results));
            }while(results.next());

            return tracks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(int playlistId, int trackId, boolean offlineAvailable){
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("INSERT INTO track_in_playlist VALUES(?, ?, ?)");
            query.setInt(1, playlistId);
            query.setInt(2, trackId);
            query.setBoolean(3, offlineAvailable);

            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int playlistId, int trackId){
        try {
            Connection con = databaseConnection.get();

            PreparedStatement query = con.prepareStatement("DELETE FROM track_in_playlist WHERE playlist_id = ? AND track_id = ?");
            query.setInt(1, playlistId);
            query.setInt(2, trackId);

            query.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Track convertToTrack(ResultSet results) {
        try {
            SimpleDateFormat DateFor = new SimpleDateFormat("MM-dd-yyyy");
            Date date = results.getDate("publicationDate");
            String publicationDate = null;
            if (date != null) {
                publicationDate = DateFor.format(date);
            }

            return new Track(
                    results.getInt("id"),
                    results.getString("title"),
                    results.getString("performer"),
                    results.getInt("duration"),
                    results.getString("album"),
                    results.getInt("playcount"),
                    publicationDate,
                    results.getString("description"),
                    results.getBoolean("offlineAvailable")
            );
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
