package nl.han.dea.spotitubeherkansing.services;

import nl.han.dea.spotitubeherkansing.DTOs.login.LoginRequestDTO;
import nl.han.dea.spotitubeherkansing.DTOs.login.LoginResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.User;
import nl.han.dea.spotitubeherkansing.exceptions.UnauthorizedUserException;
import nl.han.dea.spotitubeherkansing.interfaces.DAOs.IUserDAO;
import nl.han.dea.spotitubeherkansing.interfaces.DAOs.IUserTokenDAO;
import nl.han.dea.spotitubeherkansing.interfaces.services.IUserService;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.util.UUID;

public class UserService implements IUserService {

    private IUserDAO userDAO;

    @Inject
    public void setUserDAO(IUserDAO userDAO){ this.userDAO = userDAO; }


    private IUserTokenDAO userTokenDAO;

    @Inject
    public void setUserTokenDAO(IUserTokenDAO userTokenDAO){ this.userTokenDAO = userTokenDAO; }

    public LoginResponseDTO authenticateUser(LoginRequestDTO request) {
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
            throw new UnauthorizedUserException("Invalid username or password!");
        }

    }

    public User authenticateToken(String token) {
        return userDAO.getByToken(token);
    }

    private User getUser(String username) {
        return userDAO.get(username);
    }

    private boolean isPasswordValid(String givenPassword, String receivedPassword){
        return DigestUtils.sha256Hex(givenPassword).equals(receivedPassword);
    }
}
