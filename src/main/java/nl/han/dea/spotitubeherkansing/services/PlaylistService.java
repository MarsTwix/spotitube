package nl.han.dea.spotitubeherkansing.services;

import nl.han.dea.spotitubeherkansing.DTOs.playlists.PlaylistDTO;
import nl.han.dea.spotitubeherkansing.DTOs.playlists.PlaylistsResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.Playlist;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.DAOs.PlaylistDAO;
import nl.han.dea.spotitubeherkansing.exceptions.UnautorizedEditException;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistService {

    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private PlaylistDAO playlistDAO;

    @Inject
    public void setPlaylistDAO(PlaylistDAO playlistDAO){ this.playlistDAO = playlistDAO; }

    public PlaylistsResponseDTO getAllPlaylists(String token) throws SQLException, UnauthorizedUserException {
        User user = userService.getUserByToken(token);
        return convertPlaylistToResponseDTO(user);
    }

    public PlaylistsResponseDTO deletePlaylist(String token, int id) throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        User user = userService.getUserByToken(token);
        if(isOwner(id, user.getId())){
            playlistDAO.delete(id);
            return convertPlaylistToResponseDTO(user);
        }
        else{
            throw new UnautorizedEditException();
        }
    }

    public PlaylistsResponseDTO addPlaylist(String token, String name) throws SQLException, UnauthorizedUserException {
        User user = userService.getUserByToken(token);
        playlistDAO.add(name, user.getId());
        return convertPlaylistToResponseDTO(user);
    }

    public PlaylistsResponseDTO editPlaylist(String token, int id, String name) throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        User user = userService.getUserByToken(token);
        if(isOwner(id, user.getId())) {
            playlistDAO.edit(name, id);
            return convertPlaylistToResponseDTO(user);
        }
        else{
            throw new UnautorizedEditException();
        }
    }

    private boolean isOwner(int id, int ownerId) throws SQLException {
        Playlist playlist = playlistDAO.get(id);
        return playlist != null && playlist.getOwner() == ownerId;
    }

    private PlaylistsResponseDTO convertPlaylistToResponseDTO(User user) throws SQLException {
        List<Playlist> playlists = playlistDAO.getAll();
        List<PlaylistDTO> playlistDTOs = new ArrayList<PlaylistDTO>();

        playlists.forEach((playlist) -> playlistDTOs.add(convertPlaylistToDTO(playlist, user)));

        //TODO: fix length
        return new PlaylistsResponseDTO(playlistDTOs, 123445);
    }

    private PlaylistDTO convertPlaylistToDTO(Playlist playlist, User user){
        //TODO: fix tracks
        PlaylistDTO playlistDTO = new PlaylistDTO();

        playlistDTO.setId(playlist.getId());
        playlistDTO.setName(playlist.getName());
        playlistDTO.setOwner(playlist.getOwner() == user.getId());
        playlistDTO.setTracks(null);

        return playlistDTO;
    }
}
