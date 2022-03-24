package nl.han.dea.spotitubeherkansing.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.sql.SQLException;

@Provider
public class SQLMapper implements ExceptionMapper<SQLException> {
    @Override
    public Response toResponse(SQLException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
