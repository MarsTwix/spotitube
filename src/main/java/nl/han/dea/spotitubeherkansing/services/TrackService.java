package nl.han.dea.spotitubeherkansing.services;

import nl.han.dea.spotitubeherkansing.DAOs.TrackDAO;
import nl.han.dea.spotitubeherkansing.DTOs.tracks.TracksResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.Track;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.exceptions.UnautorizedEditException;

import javax.inject.Inject;
import java.sql.SQLException;

public class TrackService {

    private TrackDAO trackDAO;

    @Inject
    public void setTrackDAO(TrackDAO trackDAO) {
        this.trackDAO = trackDAO;
    }

    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private PlaylistService playlistService;

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    public TracksResponseDTO getPlaylistTracks(String token, int playlistId) throws SQLException, UnauthorizedUserException {
        userService.authenticateToken(token);
        return new TracksResponseDTO(trackDAO.getTracks(playlistId));
    }

    public TracksResponseDTO getAvailableTracks(String token, int playlistId) throws SQLException, UnauthorizedUserException {
        userService.authenticateToken(token);
        return new TracksResponseDTO(trackDAO.getAvailableTracks(playlistId));
    }

    public TracksResponseDTO addTrack(String token, int playlistId, int trackId, boolean offlineAvailable) throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        User user = userService.authenticateToken(token);
        if(playlistService.isOwner(playlistId, user.getId())) {
            trackDAO.addTrack(playlistId, trackId, offlineAvailable);
            return new TracksResponseDTO(trackDAO.getTracks(playlistId));
        }else{
            throw new UnautorizedEditException();
        }
    }

    public TracksResponseDTO deleteTrack(String token, int playlistId, int trackId) throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        User user = userService.authenticateToken(token);
        if(playlistService.isOwner(playlistId, user.getId())) {
            trackDAO.deleteTrack(playlistId, trackId);
            return new TracksResponseDTO(trackDAO.getTracks(playlistId));
        }else{
            throw new UnautorizedEditException();
        }
    }
}
