package services;

import nl.han.dea.spotitubeherkansing.DAOs.UserTokenDAO;
import nl.han.dea.spotitubeherkansing.DTOs.login.LoginRequestDTO;
import nl.han.dea.spotitubeherkansing.DTOs.login.LoginResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.DAOs.UserDAO;
import nl.han.dea.spotitubeherkansing.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    UserService userService;

    private LoginRequestDTO loginRequestDTO;

    @BeforeEach
    public void setup(){
        userService = new UserService();

        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUsername("meron");
        loginRequestDTO.setPassword("MySuperSecretPassword12341");
    }
    @Test
    void validPassword() throws SQLException, UnauthorizedUserException {
        UserDAO userDAOMock = mock(UserDAO.class);
        User userMock = new User(1,"meron", DigestUtils.sha256Hex("MySuperSecretPassword12341"),"Meron Brouwer");
        when(userDAOMock.get(loginRequestDTO.getUsername())).thenReturn(userMock);
        userService.setUserMapper(userDAOMock);

        UserTokenDAO userTokenDAOMock = mock(UserTokenDAO.class);
        String tokenMock = null;
        when(userTokenDAOMock.tokenExists(tokenMock)).thenReturn(true);
        userService.setUserTokenDAO(userTokenDAOMock);


        LoginResponseDTO loginResponseDTO = userService.authenticateUser(loginRequestDTO);


        assertEquals(userMock.getName(), loginResponseDTO.getName());
    }
    @Test
    void invalidPassword() throws SQLException, UnauthorizedUserException {
        User mockUser = new User(1,"meron", DigestUtils.sha256Hex("wrong_password"),"Meron Brouwer");

        UserDAO userDAOMock = mock(UserDAO.class);
        when(userDAOMock.get(loginRequestDTO.getUsername())).thenReturn(mockUser);
        userService.setUserMapper(userDAOMock);

        assertThrows(UnauthorizedUserException.class, () ->{
            userService.authenticateUser(loginRequestDTO);
        });
    }

    @Test
    void nonExistenceUsername() throws SQLException, UnauthorizedUserException {

        UserDAO userDAOMock = mock(UserDAO.class);
        when(userDAOMock.get(loginRequestDTO.getUsername())).thenThrow(new UnauthorizedUserException());
        userService.setUserMapper(userDAOMock);

        assertThrows(UnauthorizedUserException.class, () ->{
            userService.authenticateUser(loginRequestDTO);
        });
    }

    @Test
    void failedSQL() throws SQLException, UnauthorizedUserException {

        UserDAO userDAOMock = mock(UserDAO.class);
        when(userDAOMock.get(loginRequestDTO.getUsername())).thenThrow(new SQLException());
        userService.setUserMapper(userDAOMock);

        assertThrows(SQLException.class, () ->{
            userService.authenticateUser(loginRequestDTO);
        });
    }
}
