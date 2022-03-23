package nl.han.dea.spotitubeherkansing.services;

import nl.han.dea.spotitubeherkansing.DAOs.UserTokenDAO;
import nl.han.dea.spotitubeherkansing.DTOs.login.LoginRequestDTO;
import nl.han.dea.spotitubeherkansing.DTOs.login.LoginResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.DAOs.UserDAO;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.UUID;

public class UserService {

    private UserDAO userDAO;

    @Inject
    public void setUserMapper(UserDAO userDAO){ this.userDAO = userDAO; }


    private UserTokenDAO userTokenDAO;

    @Inject
    public void setUserTokenDAO(UserTokenDAO userTokenDAO){ this.userTokenDAO = userTokenDAO; }

    public LoginResponseDTO authenticateUser(LoginRequestDTO request) throws SQLException, UnauthorizedUserException {
        User user = getUser(request.getUsername());
        if(isPasswordValid(request.getPassword(), user.getPassword())){
            String token;
            do {
                 token = UUID.randomUUID().toString();
            }while(userTokenDAO.tokenExists(token));

            userTokenDAO.setUserToken(user.getId(), token);


            return new LoginResponseDTO(token, user.getName());
        }
        else{
            throw new UnauthorizedUserException();
        }

    }

    public User authenticateToken(String token) throws SQLException, UnauthorizedUserException {
        return userDAO.getByToken(token);
    }

    private User getUser(String username) throws SQLException, UnauthorizedUserException {
        return userDAO.get(username);
    }

    private boolean isPasswordValid(String givenPassword, String recievedPassword){
        return DigestUtils.sha256Hex(givenPassword).equals(recievedPassword);
    }
}
