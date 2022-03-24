package resources;

import nl.han.dea.spotitubeherkansing.DTOs.playlists.PlaylistDTO;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedEditException;
import nl.han.dea.spotitubeherkansing.interfaces.services.IPlaylistService;
import nl.han.dea.spotitubeherkansing.resources.PlaylistsResource;
import nl.han.dea.spotitubeherkansing.services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistsResourceTest {

    PlaylistsResource playlistsResource;
    IPlaylistService playlistServiceMock;
    String token;
    int id;
    String playlistName;
    PlaylistDTO playlist;

    @BeforeEach
    void setup(){
        playlistsResource = new PlaylistsResource();
        playlistServiceMock = mock(PlaylistService.class);

        token = UUID.randomUUID().toString();
        id = 1;
        playlistName = "Bangers";
        playlist = new PlaylistDTO();
        playlist.setName(playlistName);
    }

    @Test
    void getPlaylistsSuccessful() throws UnauthorizedUserException {
        when(playlistServiceMock.getAllPlaylists(token)).thenReturn(null);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.getPlaylists(token);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }


    @Test
    void deletePlaylistSuccessful() throws UnauthorizedUserException, UnauthorizedEditException {
        when(playlistServiceMock.deletePlaylist(token, id)).thenReturn(null);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.deletePlaylist(id, token);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void addPlaylistSuccessful() throws UnauthorizedUserException {
        when(playlistServiceMock.addPlaylist(token, playlistName)).thenReturn(null);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.addPlaylist(token, playlist);

        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }


    @Test
    void editPlaylistSuccessful() throws UnauthorizedUserException, UnauthorizedEditException {
        when(playlistServiceMock.editPlaylist(token, id, playlistName)).thenReturn(null);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.editPlaylist(id, token, playlist);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

}
