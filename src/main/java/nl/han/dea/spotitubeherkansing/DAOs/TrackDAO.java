package nl.han.dea.spotitubeherkansing.DAOs;

import nl.han.dea.spotitubeherkansing.DatabaseConnection;
import nl.han.dea.spotitubeherkansing.domains.Track;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackDAO {

    @Inject
    DatabaseConnection databaseConnection;

    public List<Track> getTracks(int playlistId) throws SQLException {
        Connection con = databaseConnection.get();

        PreparedStatement query = con.prepareStatement("SELECT t.*, tip.offlineAvailable FROM track_in_playlist tip INNER JOIN track t ON tip.track_id = t.id WHERE playlist_id = ?");
        query.setInt(1, playlistId);
        ResultSet results = query.executeQuery();

        if (!results.next()) {
            return new ArrayList<Track>();
        }

        List<Track> tracks = new ArrayList<>();
        SimpleDateFormat DateFor = new SimpleDateFormat("MM-dd-yyyy");
        do {
            Track track = new Track();
            track.setId(results.getInt("id"));
            track.setTitle(results.getString("title"));
            track.setPerformer(results.getString("performer"));
            track.setDuration(results.getInt("duration"));
            track.setAlbum(results.getString("album"));
            track.setPlaycount(results.getInt("playcount"));
            Date date = results.getDate("publicationDate");

            if(date == null){
                track.setPublicationDate(null);

            }else{
                track.setPublicationDate(DateFor.format(date));
            }
            track.setDescription(results.getString("description"));
            track.setOfflineAvailable(results.getBoolean("offlineAvailable"));
            tracks.add(track);
        }while(results.next());

        return tracks;
    }

    public List<Track> getAvailableTracks(int playlistId) throws SQLException {
        Connection con = databaseConnection.get();

        PreparedStatement query = con.prepareStatement("SELECT * FROM track t WHERE NOT EXISTS(SELECT null FROM track_in_playlist tip where t.id = tip.track_id AND playlist_id = ?)");
        query.setInt(1, playlistId);
        ResultSet results = query.executeQuery();

        if (!results.next()) {
            return new ArrayList<Track>();
        }

        List<Track> tracks = new ArrayList<>();
        SimpleDateFormat DateFor = new SimpleDateFormat("MM-dd-yyyy");
        do {
            Track track = new Track();
            track.setId(results.getInt("id"));
            track.setTitle(results.getString("title"));
            track.setPerformer(results.getString("performer"));
            track.setDuration(results.getInt("duration"));
            track.setAlbum(results.getString("album"));
            track.setPlaycount(results.getInt("playcount"));
            Date date = results.getDate("publicationDate");

            if(date == null){
                track.setPublicationDate(null);

            }else{
                track.setPublicationDate(DateFor.format(date));
            }
            track.setDescription(results.getString("description"));
            tracks.add(track);
        }while(results.next());

        return tracks;
    }

    public void addTrack(int playlistId, int trackId, boolean offlineAvailable) throws SQLException {
        Connection con = databaseConnection.get();

        PreparedStatement query = con.prepareStatement("INSERT INTO track_in_playlist VALUES(?, ?, ?)");
        query.setInt(1, playlistId);
        query.setInt(2, trackId);
        query.setBoolean(3, offlineAvailable);

        query.executeUpdate();
    }

    public void deleteTrack(int playlistId, int trackId) throws SQLException {
        Connection con = databaseConnection.get();

        PreparedStatement query = con.prepareStatement("DELETE FROM track_in_playlist WHERE playlist_id = ? AND track_id = ?");
        query.setInt(1, playlistId);
        query.setInt(2, trackId);

        query.executeUpdate();
    }
}
