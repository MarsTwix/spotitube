package nl.han.dea.spotitubeherkansing.interfaces.services;

import nl.han.dea.spotitubeherkansing.DTOs.login.LoginRequestDTO;
import nl.han.dea.spotitubeherkansing.DTOs.login.LoginResponseDTO;
import nl.han.dea.spotitubeherkansing.domains.User;

public interface IUserService {
    LoginResponseDTO authenticateUser(LoginRequestDTO request);
    User authenticateToken(String token);
}
