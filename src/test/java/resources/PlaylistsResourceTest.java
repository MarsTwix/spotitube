package resources;

import nl.han.dea.spotitubeherkansing.DTOs.playlists.PlaylistDTO;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.exceptions.UnautorizedEditException;
import nl.han.dea.spotitubeherkansing.resources.PlaylistsResource;
import nl.han.dea.spotitubeherkansing.services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistsResourceTest {

    PlaylistsResource playlistsResource;
    PlaylistService playlistServiceMock;
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
    void getPlaylistsSuccessful() throws SQLException, UnauthorizedUserException {
        when(playlistServiceMock.getAllPlaylists(token)).thenReturn(null);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.getPlaylists(token);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void getPlaylistsInvalidTokenError() throws SQLException, UnauthorizedUserException {
        when(playlistServiceMock.getAllPlaylists(token)).thenThrow(UnauthorizedUserException.class);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.getPlaylists(token);

        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test
    void getPlaylistsSQLExceptionError() throws SQLException, UnauthorizedUserException {
        when(playlistServiceMock.getAllPlaylists(token)).thenThrow(SQLException.class);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.getPlaylists(token);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());
    }

    @Test
    void deletePlaylistSuccessful() throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        when(playlistServiceMock.deletePlaylist(token, id)).thenReturn(null);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.deletePlaylist(id, token);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void deletePlaylistInvalidTokenError() throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        when(playlistServiceMock.deletePlaylist(token, id)).thenThrow(UnauthorizedUserException.class);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.deletePlaylist(id, token);

        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test
    void deletePlaylistSQLExceptionError() throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        when(playlistServiceMock.deletePlaylist(token, id)).thenThrow(SQLException.class);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.deletePlaylist(id, token);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());
    }

    @Test
    void deletePlaylistUnownedOrNonexistencePlaylistError() throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        when(playlistServiceMock.deletePlaylist(token, id)).thenThrow(UnautorizedEditException.class);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.deletePlaylist(id, token);

        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }

    @Test
    void addPlaylistSuccessful() throws SQLException, UnauthorizedUserException {
        when(playlistServiceMock.addPlaylist(token, playlistName)).thenReturn(null);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.addPlaylist(token, playlist);

        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }

    @Test
    void addPlaylistInvalidTokenError() throws SQLException, UnauthorizedUserException {
        when(playlistServiceMock.addPlaylist(token, playlistName)).thenThrow(UnauthorizedUserException.class);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.addPlaylist(token, playlist);

        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test
    void addPlaylistSQLExceptionError() throws SQLException, UnauthorizedUserException {
        when(playlistServiceMock.addPlaylist(token, playlistName)).thenThrow(SQLException.class);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.addPlaylist(token, playlist);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());
    }

    @Test
    void editPlaylistSuccessful() throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        when(playlistServiceMock.editPlaylist(token, id, playlistName)).thenReturn(null);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.editPlaylist(id, token, playlist);

        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void editPlaylistInvalidTokenError() throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        when(playlistServiceMock.editPlaylist(token, id, playlistName)).thenThrow(UnauthorizedUserException.class);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.editPlaylist(id, token, playlist);

        assertEquals(Response.Status.FORBIDDEN, response.getStatusInfo());
    }

    @Test
    void editPlaylistSQLExceptionError() throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        when(playlistServiceMock.editPlaylist(token, id, playlistName)).thenThrow(SQLException.class);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.editPlaylist(id, token, playlist);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());
    }

    @Test
    void editPlaylistUnownedOrNonexistencePlaylistError() throws SQLException, UnauthorizedUserException, UnautorizedEditException {
        when(playlistServiceMock.editPlaylist(token, id, playlistName)).thenThrow(UnautorizedEditException.class);
        playlistsResource.setPlaylistService(playlistServiceMock);

        Response response = playlistsResource.editPlaylist(id, token, playlist);

        assertEquals(Response.Status.BAD_REQUEST, response.getStatusInfo());
    }
}
