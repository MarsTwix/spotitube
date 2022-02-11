package resources;

import nl.han.dea.spotitubeherkansing.DTOs.LoginRequestDTO;
import nl.han.dea.spotitubeherkansing.DTOs.LoginResponseDTO;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.resources.LoginResource;
import nl.han.dea.spotitubeherkansing.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginResourceTest {

    private LoginResource loginResource;

    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    public void setup(){
        loginResource = new LoginResource();

        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("testUsername");
        loginRequestDTO.setPassword("testPassword");
    }

    @Test
    void succesfullLogin() throws SQLException, UnauthorizedUserException {

        LoginResponseDTO mockResponse = new LoginResponseDTO(UUID.randomUUID(), "testName");

        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.authenticateUser(loginRequestDTO)).thenReturn(mockResponse);
        loginResource.setUserService(userServiceMock);


        Response response = loginResource.login(loginRequestDTO);
        LoginResponseDTO loginResponseDTO = (LoginResponseDTO) response.getEntity();


        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(mockResponse, loginResponseDTO);
    }

    @Test
    void failedLogin() throws SQLException, UnauthorizedUserException {

        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.authenticateUser(loginRequestDTO)).thenThrow(new UnauthorizedUserException());
        loginResource.setUserService(userServiceMock);


        Response response = this.loginResource.login(loginRequestDTO);


        assertEquals(Response.Status.UNAUTHORIZED, response.getStatusInfo());
    }

    @Test
    void failedSQL() throws SQLException, UnauthorizedUserException {

        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.authenticateUser(loginRequestDTO)).thenThrow(new SQLException());
        loginResource.setUserService(userServiceMock);


        Response response = this.loginResource.login(loginRequestDTO);


        assertEquals(Response.Status.INTERNAL_SERVER_ERROR, response.getStatusInfo());
    }

}
