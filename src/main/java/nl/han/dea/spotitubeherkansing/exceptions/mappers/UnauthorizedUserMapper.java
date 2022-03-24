package nl.han.dea.spotitubeherkansing.exceptions.mappers;

import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthorizedUserMapper implements ExceptionMapper<UnauthorizedUserException> {
    @Override
    public Response toResponse(UnauthorizedUserException exception) {
        return Response.status(Response.Status.FORBIDDEN).build();
    }
}
