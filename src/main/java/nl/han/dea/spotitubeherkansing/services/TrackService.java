package nl.han.dea.spotitubeherkansing.services;

import nl.han.dea.spotitubeherkansing.DTOs.tracks.TracksResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.interfaces.DAOs.ITrackDAO;
import nl.han.dea.spotitubeherkansing.interfaces.services.IPlaylistService;
import nl.han.dea.spotitubeherkansing.interfaces.services.ITrackService;
import nl.han.dea.spotitubeherkansing.interfaces.services.IUserService;

import javax.inject.Inject;

public class TrackService implements ITrackService {

    private ITrackDAO trackDAO;

    @Inject
    public void setTrackDAO(ITrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    private IUserService userService;

    @Inject
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    private IPlaylistService playlistService;

    @Inject
    public void setPlaylistService(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    public TracksResponseDTO getPlaylistTracks(String token, int playlistId) {
        userService.authenticateToken(token);
        return new TracksResponseDTO(trackDAO.getAll(playlistId));
    }

    public TracksResponseDTO getAvailableTracks(String token, int playlistId){
        userService.authenticateToken(token);
        return new TracksResponseDTO(trackDAO.getAvailable(playlistId));
    }

    public TracksResponseDTO addTrack(String token, int playlistId, int trackId, boolean offlineAvailable){
        User user = userService.authenticateToken(token);
        if(playlistService.isOwner(playlistId, user.getId())) {
            trackDAO.add(playlistId, trackId, offlineAvailable);
            return new TracksResponseDTO(trackDAO.getAll(playlistId));
        }
        return null;
    }

    public TracksResponseDTO deleteTrack(String token, int playlistId, int trackId){
        User user = userService.authenticateToken(token);
        if(playlistService.isOwner(playlistId, user.getId())) {
            trackDAO.delete(playlistId, trackId);
            return new TracksResponseDTO(trackDAO.getAll(playlistId));
        }
        return null;
    }
}
