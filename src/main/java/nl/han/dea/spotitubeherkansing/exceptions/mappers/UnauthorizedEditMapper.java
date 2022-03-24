package nl.han.dea.spotitubeherkansing.exceptions.mappers;

import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedEditException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthorizedEditMapper implements ExceptionMapper<UnauthorizedEditException> {
    @Override
    public Response toResponse(UnauthorizedEditException exception) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
