package nl.han.dea.spotitubeherkansing.resources;

import nl.han.dea.spotitubeherkansing.interfaces.services.ITrackService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TracksResource {

    private ITrackService trackService;

    @Inject
    public void setTrackService(ITrackService trackService){
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") int PlayListId){
            return Response.status(Response.Status.OK).entity(trackService.getAvailableTracks(token, PlayListId)).build();
    }
}
