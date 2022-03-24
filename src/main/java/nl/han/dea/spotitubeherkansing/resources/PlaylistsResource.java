package nl.han.dea.spotitubeherkansing.resources;


import nl.han.dea.spotitubeherkansing.DTOs.playlists.PlaylistDTO;
import nl.han.dea.spotitubeherkansing.interfaces.services.IPlaylistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("playlists")
public class PlaylistsResource {

    private IPlaylistService playlistService;

    @Inject
    public void setPlaylistService(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token){
            return Response.status(Response.Status.OK).entity(playlistService.getAllPlaylists(token)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token){
        return Response.status(Response.Status.OK).entity(playlistService.deletePlaylist(token ,id)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO request) {
        return Response.status(Response.Status.CREATED).entity(playlistService.addPlaylist(token, request.getName())).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@PathParam("id") int id, @QueryParam("token") String token, PlaylistDTO request){
        return Response.status(Response.Status.OK).entity(playlistService.editPlaylist(token, id, request.getName())).build();
    }

}
