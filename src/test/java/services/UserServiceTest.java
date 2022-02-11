package services;

import nl.han.dea.spotitubeherkansing.DTOs.LoginRequestDTO;
import nl.han.dea.spotitubeherkansing.DTOs.LoginResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.mappers.UserMapper;
import nl.han.dea.spotitubeherkansing.services.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
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
        loginRequestDTO.setUsername("testUsername");
        loginRequestDTO.setPassword("testPassword");
    }
    @Test
    void validPassword() throws SQLException, UnauthorizedUserException {
        User mockUser = new User(0,"testUsername", DigestUtils.sha256Hex("testPassword"),"testName");

        UserMapper userMapperMock = mock(UserMapper.class);
        when(userMapperMock.find(loginRequestDTO.getUsername())).thenReturn(mockUser);
        userService.setUserMapper(userMapperMock);


        LoginResponseDTO loginResponseDTO = userService.authenticateUser(loginRequestDTO);


        assertEquals(mockUser.getName(), loginResponseDTO.getName());
    }
    @Test
    void invalidPassword() throws SQLException, UnauthorizedUserException {
        User mockUser = new User(0,"testUsername", DigestUtils.sha256Hex("differentPassword"),"testName");

        UserMapper userMapperMock = mock(UserMapper.class);
        when(userMapperMock.find(loginRequestDTO.getUsername())).thenReturn(mockUser);
        userService.setUserMapper(userMapperMock);

        assertThrows(UnauthorizedUserException.class, () ->{
            userService.authenticateUser(loginRequestDTO);
        });
    }

    @Test
    void nonExistenceUsername() throws SQLException, UnauthorizedUserException {

        UserMapper userMapperMock = mock(UserMapper.class);
        when(userMapperMock.find(loginRequestDTO.getUsername())).thenThrow(new UnauthorizedUserException());
        userService.setUserMapper(userMapperMock);

        assertThrows(UnauthorizedUserException.class, () ->{
            userService.authenticateUser(loginRequestDTO);
        });
    }

    @Test
    void failedSQL() throws SQLException, UnauthorizedUserException {

        UserMapper userMapperMock = mock(UserMapper.class);
        when(userMapperMock.find(loginRequestDTO.getUsername())).thenThrow(new SQLException());
        userService.setUserMapper(userMapperMock);

        assertThrows(SQLException.class, () ->{
            userService.authenticateUser(loginRequestDTO);
        });
    }
}
