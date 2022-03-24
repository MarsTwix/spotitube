package nl.han.dea.spotitubeherkansing.interfaces.services;

import nl.han.dea.spotitubeherkansing.DTOs.tracks.TracksResponseDTO;

public interface ITrackService {
    TracksResponseDTO getPlaylistTracks(String token, int playlistId);
    TracksResponseDTO getAvailableTracks(String token, int playlistId);
    TracksResponseDTO addTrack(String token, int playlistId, int trackId, boolean offlineAvailable);
    TracksResponseDTO deleteTrack(String token, int playlistId, int trackId);
}
