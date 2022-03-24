package nl.han.dea.spotitubeherkansing.resources;

import nl.han.dea.spotitubeherkansing.domains.Track;
import nl.han.dea.spotitubeherkansing.interfaces.services.ITrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/playlists/{playlist_id}/tracks")
public class PlaylistsTracksResource {

    private ITrackService trackService;

    @Inject
    public void setTrackService(ITrackService trackService){
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylistTracks(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token){
        return Response.status(Response.Status.OK).entity(trackService.getPlaylistTracks(token, playlistId)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrack(@PathParam("playlist_id") int playlistId, @QueryParam("token") String token, Track track){
        return Response.status(Response.Status.CREATED).entity(trackService.addTrack(token, playlistId, track.getId(), track.getOfflineAvailable())).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{track_id}")
    public Response deleteTrack(@PathParam("playlist_id") int playlistId, @PathParam("track_id") int trackId, @QueryParam("token") String token){
            return Response.status(Response.Status.OK).entity(trackService.deleteTrack(token, playlistId, trackId)).build();
    }
}
