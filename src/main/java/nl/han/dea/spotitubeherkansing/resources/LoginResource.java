package nl.han.dea.spotitubeherkansing.resources;

import nl.han.dea.spotitubeherkansing.DTOs.LoginRequestDTO;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;


@Path("login")
public class LoginResource {

    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO){
        try {
            return Response.status(200).entity(userService.authenticateUser(loginRequestDTO)).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).build();
        } catch (UnauthorizedUserException e) {
            e.printStackTrace();
            return Response.status(401).build();
        }
    }
}
