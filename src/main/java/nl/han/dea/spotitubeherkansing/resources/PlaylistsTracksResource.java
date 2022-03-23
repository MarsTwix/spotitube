package nl.han.dea.spotitubeherkansing.resources;

import nl.han.dea.spotitubeherkansing.domains.Track;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.exceptions.UnautorizedEditException;
import nl.han.dea.spotitubeherkansing.services.TrackService;
import nl.han.dea.spotitubeherkansing.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/playlists/{playlist_id}/tracks")
public class PlaylistsTracksResource {



    private TrackService trackService;

    @Inject
    public void setTrackService(TrackService trackService){
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylistTracks(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token){
        try {
            return Response.status(200).entity(trackService.getPlaylistTracks(token, playlistId)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (UnauthorizedUserException e) {
            e.printStackTrace();
            return Response.status(403).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrack(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token, Track track){
        try {
            return Response.status(201).entity(trackService.addTrack(token, playlistId, track.getId(), track.getOfflineAvailable())).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (UnauthorizedUserException e) {
            e.printStackTrace();
            return Response.status(403).build();
        } catch (UnautorizedEditException e) {
            e.printStackTrace();
            return Response.status(400).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{track_id}")
    public Response deleteTrack(@PathParam("playlist_id") int playlistId, @PathParam("track_id") int trackId, @QueryParam("token") String token){
        try {
            return Response.status(200).entity(trackService.deleteTrack(token, playlistId, trackId)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (UnauthorizedUserException e) {
            e.printStackTrace();
            return Response.status(403).build();
        } catch (UnautorizedEditException e) {
            e.printStackTrace();
            return Response.status(400).build();
        }
    }
}
