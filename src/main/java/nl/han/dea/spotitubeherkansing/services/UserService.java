package nl.han.dea.spotitubeherkansing.services;

import nl.han.dea.spotitubeherkansing.DTOs.LoginRequestDTO;
import nl.han.dea.spotitubeherkansing.DTOs.LoginResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.mappers.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.UUID;


public class UserService {


    UserMapper userMapper;

    @Inject
    public void setUserMapper(UserMapper userMapper){ this.userMapper = userMapper; }

    public LoginResponseDTO authenticateUser(LoginRequestDTO request) throws SQLException, UnauthorizedUserException {
        User user = getUser(request.getUsername());
        if(isPasswordValid(request.getPassword(), user.getPassword())){
            //TODO: save token to verify a user.
            return new LoginResponseDTO(UUID.randomUUID(), user.getName());
        }
        else{
            throw new UnauthorizedUserException();
        }

    }

    private User getUser(String username) throws SQLException, UnauthorizedUserException {
        return userMapper.find(username);
    }

    private boolean isPasswordValid(String givenPassword, String recievedPassword){
        return DigestUtils.sha256Hex(givenPassword).equals(recievedPassword);
    }
}
