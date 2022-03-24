package nl.han.dea.spotitubeherkansing.interfaces.DAOs;

import nl.han.dea.spotitubeherkansing.domains.Playlist;

import java.util.List;

public interface IPlaylistDAO {
    List<Playlist> getAll();
    Playlist get(int playlistId);
    void delete(int PlaylistId);
    void add(String playlistName, int ownerId);
    void edit(String newPlaylistName, int playlistId);
    int getLength(int playlistId);
}
