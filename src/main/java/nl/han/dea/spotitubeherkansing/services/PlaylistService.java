package nl.han.dea.spotitubeherkansing.services;

import nl.han.dea.spotitubeherkansing.DTOs.playlists.PlaylistDTO;
import nl.han.dea.spotitubeherkansing.DTOs.playlists.PlaylistsResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.Playlist;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedEditException;
import nl.han.dea.spotitubeherkansing.interfaces.DAOs.IPlaylistDAO;
import nl.han.dea.spotitubeherkansing.interfaces.services.IPlaylistService;
import nl.han.dea.spotitubeherkansing.interfaces.services.IUserService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PlaylistService implements IPlaylistService {

    private IUserService userService;

    @Inject
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    private IPlaylistDAO playlistDAO;

    @Inject
    public void setPlaylistDAO(IPlaylistDAO playlistDAO){ this.playlistDAO = playlistDAO; }

    public PlaylistsResponseDTO getAllPlaylists(String token){
        User user = userService.authenticateToken(token);
        return convertPlaylistToResponseDTO(user);
    }

    public PlaylistsResponseDTO deletePlaylist(String token, int PlaylistId)  {
        User user = userService.authenticateToken(token);
        if(isOwner(PlaylistId, user.getId())){
            playlistDAO.delete(PlaylistId);
            return convertPlaylistToResponseDTO(user);
        }
        return null;
    }

    public PlaylistsResponseDTO addPlaylist(String token, String playlistName) {
        User user = userService.authenticateToken(token);
        playlistDAO.add(playlistName, user.getId());
        return convertPlaylistToResponseDTO(user);
    }

    public PlaylistsResponseDTO editPlaylist(String token, int playlistId, String newPlaylistName){
        User user = userService.authenticateToken(token);
        if(isOwner(playlistId, user.getId())) {
            playlistDAO.edit(newPlaylistName, playlistId);
            return convertPlaylistToResponseDTO(user);
        }
        return null;
    }

    public boolean isOwner(int PlaylistId, int ownerId){
        Playlist playlist = playlistDAO.get(PlaylistId);
        if(playlist == null){
            throw new UnauthorizedEditException("Playlist does not exist!");
        }
        else if(playlist.getOwner() != ownerId)
            throw new UnauthorizedEditException("You're not the owner of the playlist!");
        else{
            return true;
        }
    }

    private PlaylistsResponseDTO convertPlaylistToResponseDTO(User user) {
        List<PlaylistDTO> playlistDTOs = new ArrayList<>();
        int totalLength = 0;

        for(Playlist playlist: playlistDAO.getAll()){
            playlistDTOs.add(new PlaylistDTO(
                    playlist.getId(),
                    playlist.getName(),
                    playlist.getOwner() == user.getId()
            ));
            totalLength += playlistDAO.getLength(playlist.getId());
        }

        return new PlaylistsResponseDTO(playlistDTOs, totalLength);
    }
}
