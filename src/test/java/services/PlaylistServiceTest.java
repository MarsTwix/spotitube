package services;

import nl.han.dea.spotitubeherkansing.DAOs.PlaylistDAO;
import nl.han.dea.spotitubeherkansing.DTOs.playlists.PlaylistsResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.Playlist;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.exceptions.UnautorizedEditException;
import nl.han.dea.spotitubeherkansing.services.PlaylistService;
import nl.han.dea.spotitubeherkansing.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistServiceTest {
    PlaylistService playlistService;
    List<Playlist> playlists = new ArrayList<>();
    PlaylistDAO playlistDAOMock;
    int id = 1;
    String token = UUID.randomUUID().toString();
    Playlist playlist = new Playlist(1, "Heavy Metal", 1);
    Playlist unOwnedPlaylist = new Playlist(2, "Pop", 3);

    @BeforeEach
    public void setup() throws SQLException, UnauthorizedUserException {
        playlistService = new PlaylistService();

        UserService userServiceMock = mock(UserService.class);
        User userMock = new User(1,"meron", DigestUtils.sha256Hex("MySuperSecretPassword12341"),"Meron Brouwer");
        when(userServiceMock.getUserByToken(token)).thenReturn(userMock);
        playlistService.setUserService(userServiceMock);


        playlistDAOMock = mock(PlaylistDAO.class);
        playlists.add(playlist);
        playlists.add(unOwnedPlaylist);

    }

    @Test
    void getAllPlaylistsSuccessful() throws SQLException, UnauthorizedUserException {
        when(playlistDAOMock.getAll()).thenReturn(playlists);
        playlistService.setPlaylistDAO(playlistDAOMock);

        PlaylistsResponseDTO playlistsResponseDTO = playlistService.getAllPlaylists(token);

        assertEquals(123445, playlistsResponseDTO.getLength());
        assertNotNull(playlistsResponseDTO.getPlaylists());
        assertEquals(2, playlistsResponseDTO.getPlaylists().size());
        assertTrue(playlistsResponseDTO.getPlaylists().get(0).getOwner());
        assertFalse(playlistsResponseDTO.getPlaylists().get(1).getOwner());

    }

    @Test
    void deletePlaylistSuccessful() throws SQLException, UnauthorizedUserException, UnautorizedEditException {

        playlists = playlists.stream()
                .filter(p -> p.getId() != id)
                .collect(Collectors.toList());

        when(playlistDAOMock.getAll()).thenReturn(playlists);
        when(playlistDAOMock.get(id)).thenReturn(playlist);
        playlistService.setPlaylistDAO(playlistDAOMock);

        PlaylistsResponseDTO playlistsResponseDTO = playlistService.deletePlaylist(token, id);

        assertEquals(123445, playlistsResponseDTO.getLength());
        assertNotNull(playlistsResponseDTO.getPlaylists());
        assertEquals(1, playlistsResponseDTO.getPlaylists().size());


    }

    @Test
    void deletePlaylistNullError() throws SQLException {
        when(playlistDAOMock.getAll()).thenReturn(playlists);
        when(playlistDAOMock.get(id)).thenReturn(null);
        playlistService.setPlaylistDAO(playlistDAOMock);

        assertThrows(UnautorizedEditException.class, ()-> playlistService.deletePlaylist(token, id));
    }

    @Test
    void deletePlaylistNullNotOwnerError() throws SQLException {
        when(playlistDAOMock.getAll()).thenReturn(playlists);
        when(playlistDAOMock.get(id)).thenReturn(unOwnedPlaylist);
        playlistService.setPlaylistDAO(playlistDAOMock);

        assertThrows(UnautorizedEditException.class, ()-> playlistService.deletePlaylist(token, id));
    }

    @Test
    void addPlaylistSuccessful() throws SQLException, UnauthorizedUserException {
        playlists.add(new Playlist(3, "Progressive Rock", 1));
        when(playlistDAOMock.getAll()).thenReturn(playlists);
        playlistService.setPlaylistDAO(playlistDAOMock);

        PlaylistsResponseDTO playlistsResponseDTO = playlistService.addPlaylist(token, "Progressive Rock");

        assertEquals(123445, playlistsResponseDTO.getLength());
        assertNotNull(playlistsResponseDTO.getPlaylists());
        assertEquals(3, playlistsResponseDTO.getPlaylists().size());
        assertTrue(playlistsResponseDTO.getPlaylists().get(0).getOwner());
        assertFalse(playlistsResponseDTO.getPlaylists().get(1).getOwner());
        assertTrue(playlistsResponseDTO.getPlaylists().get(2).getOwner());

    }

    @Test
    void editPlaylistSuccessful() throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        String newName = "Death Metal";
        playlist.setName(newName);
        when(playlistDAOMock.get(id)).thenReturn(playlist);
        playlists.set(0, playlist);
        when(playlistDAOMock.getAll()).thenReturn(playlists);
        playlistService.setPlaylistDAO(playlistDAOMock);

        PlaylistsResponseDTO playlistsResponseDTO = playlistService.editPlaylist(token, id, newName);

        assertEquals(123445, playlistsResponseDTO.getLength());
        assertNotNull(playlistsResponseDTO.getPlaylists());
        assertEquals(2, playlistsResponseDTO.getPlaylists().size());
        assertTrue(playlistsResponseDTO.getPlaylists().get(0).getOwner());
        assertEquals(newName, playlistsResponseDTO.getPlaylists().get(0).getName());
        assertFalse(playlistsResponseDTO.getPlaylists().get(1).getOwner());

    }

    @Test
    void editPlaylistNullError() throws SQLException {
        String newName = "Death Metal";
        when(playlistDAOMock.get(id)).thenReturn(null);
        when(playlistDAOMock.getAll()).thenReturn(playlists);
        playlistService.setPlaylistDAO(playlistDAOMock);

        assertThrows(UnautorizedEditException.class, ()-> playlistService.editPlaylist(token, id, newName));

    }

    @Test
    void editPlaylistNotOwnerError() throws SQLException {
        String newName = "Death Metal";
        when(playlistDAOMock.get(id)).thenReturn(unOwnedPlaylist);
        when(playlistDAOMock.getAll()).thenReturn(playlists);
        playlistService.setPlaylistDAO(playlistDAOMock);

        assertThrows(UnautorizedEditException.class, ()-> playlistService.editPlaylist(token, id, newName));

    }
}
