package nl.han.dea.spotitubeherkansing.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("")
public class LoginResource {

    @GET
    public Response login(){
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
