package nl.han.dea.spotitubeherkansing.interfaces.DAOs;

import nl.han.dea.spotitubeherkansing.domains.Track;

import java.util.List;

public interface ITrackDAO {
    List<Track> getAll(int playlistId);
    List<Track> getAvailable(int playlistId);
    void add(int playlistId, int trackId, boolean offlineAvailable);
    void delete(int playlistId, int trackId);
}
