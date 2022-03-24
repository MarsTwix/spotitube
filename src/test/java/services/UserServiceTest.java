package services;

import nl.han.dea.spotitubeherkansing.DAOs.UserTokenMySQL;
import nl.han.dea.spotitubeherkansing.DTOs.login.LoginRequestDTO;
import nl.han.dea.spotitubeherkansing.DTOs.login.LoginResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.DAOs.UserMySQL;
import nl.han.dea.spotitubeherkansing.interfaces.DAOs.IUserDAO;
import nl.han.dea.spotitubeherkansing.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void validPassword(){
        IUserDAO userMySQLMock = mock(UserMySQL.class);
        User userMock = new User(1,"meron", DigestUtils.sha256Hex("MySuperSecretPassword12341"),"Meron Brouwer");
        when(userMySQLMock.get(loginRequestDTO.getUsername())).thenReturn(userMock);
        userService.setUserDAO(userMySQLMock);

        UserTokenMySQL userTokenMySQLMock = mock(UserTokenMySQL.class);
        userService.setUserTokenDAO(userTokenMySQLMock);


        LoginResponseDTO loginResponseDTO = userService.authenticateUser(loginRequestDTO);


        assertEquals(userMock.getName(), loginResponseDTO.getName());
    }
    @Test
    void invalidPassword(){
        User mockUser = new User(1,"meron", DigestUtils.sha256Hex("wrong_password"),"Meron Brouwer");

        IUserDAO userMySQLMock = mock(UserMySQL.class);
        when(userMySQLMock.get(loginRequestDTO.getUsername())).thenReturn(mockUser);
        userService.setUserDAO(userMySQLMock);

        assertThrows(UnauthorizedUserException.class, () -> userService.authenticateUser(loginRequestDTO));
    }

    @Test
    void nonExistenceUsername(){

        IUserDAO userMySQLMock = mock(UserMySQL.class);
        when(userMySQLMock.get(loginRequestDTO.getUsername())).thenThrow(new UnauthorizedUserException("Invalid username"));
        userService.setUserDAO(userMySQLMock);

        assertThrows(UnauthorizedUserException.class, () -> userService.authenticateUser(loginRequestDTO));
    }
}
