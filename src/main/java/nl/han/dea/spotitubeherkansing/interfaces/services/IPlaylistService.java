package nl.han.dea.spotitubeherkansing.interfaces.services;

import nl.han.dea.spotitubeherkansing.DTOs.playlists.PlaylistsResponseDTO;

public interface IPlaylistService {
    PlaylistsResponseDTO getAllPlaylists(String token);
    PlaylistsResponseDTO deletePlaylist(String token, int playlistId);
    PlaylistsResponseDTO addPlaylist(String token, String playlistName);
    PlaylistsResponseDTO editPlaylist(String token, int playlistId, String newPlaylistName);
    boolean isOwner(int PlaylistId, int ownerId);

}
