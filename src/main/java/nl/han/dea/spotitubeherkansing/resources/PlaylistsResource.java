package nl.han.dea.spotitubeherkansing.resources;


import nl.han.dea.spotitubeherkansing.DTOs.playlists.PlaylistDTO;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.exceptions.UnautorizedEditException;
import nl.han.dea.spotitubeherkansing.services.PlaylistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("playlists")
public class PlaylistsResource {

    private PlaylistService playlistService;

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token){
        try {
            return Response.status(200).entity(playlistService.getAllPlaylists(token)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (UnauthorizedUserException e) {
            e.printStackTrace();
            return Response.status(403).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token){
        try {
            return Response.status(200).entity(playlistService.deletePlaylist(token ,id)).build();
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO request) {
        try {
            return Response.status(201).entity(playlistService.addPlaylist(token, request.getName())).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (UnauthorizedUserException e) {
            e.printStackTrace();
            return Response.status(403).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPlaylist(@PathParam("id") int id, @QueryParam("token") String token, PlaylistDTO request){
        try {
            return Response.status(200).entity(playlistService.editPlaylist(token, id, request.getName())).build();
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
