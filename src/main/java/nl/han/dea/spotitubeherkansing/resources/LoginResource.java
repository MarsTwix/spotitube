package nl.han.dea.spotitubeherkansing.resources;

import nl.han.dea.spotitubeherkansing.DTOs.login.LoginRequestDTO;
import nl.han.dea.spotitubeherkansing.interfaces.services.IUserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("login")
public class LoginResource {

    private IUserService userService;

    @Inject
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO){
        return Response.status(Response.Status.OK).entity(userService.authenticateUser(loginRequestDTO)).build();
    }
}
