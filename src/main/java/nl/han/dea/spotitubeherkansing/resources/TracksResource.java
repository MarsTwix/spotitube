package nl.han.dea.spotitubeherkansing.resources;

import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.services.TrackService;
import nl.han.dea.spotitubeherkansing.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/tracks")
public class TracksResource {

    private TrackService trackService;

    @Inject
    public void setTrackService(TrackService trackService){
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int PlayListId){
        try {
            return Response.status(200).entity(trackService.getAvailableTracks(token, PlayListId)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (UnauthorizedUserException e) {
            e.printStackTrace();
            return Response.status(403).build();
        }
    }
}
