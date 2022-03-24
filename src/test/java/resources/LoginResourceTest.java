package resources;

import nl.han.dea.spotitubeherkansing.DTOs.login.LoginRequestDTO;
import nl.han.dea.spotitubeherkansing.DTOs.login.LoginResponseDTO;
import nl.han.dea.spotitubeherkansing.resources.LoginResource;
import nl.han.dea.spotitubeherkansing.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

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
    void successfulLogin(){

        LoginResponseDTO mockResponse = new LoginResponseDTO(UUID.randomUUID().toString(), "testName");

        UserService userServiceMock = mock(UserService.class);
        when(userServiceMock.authenticateUser(loginRequestDTO)).thenReturn(mockResponse);
        loginResource.setUserService(userServiceMock);


        Response response = loginResource.login(loginRequestDTO);
        LoginResponseDTO loginResponseDTO = (LoginResponseDTO) response.getEntity();


        assertEquals(Response.Status.OK, response.getStatusInfo());
        assertEquals(mockResponse, loginResponseDTO);
    }

}
