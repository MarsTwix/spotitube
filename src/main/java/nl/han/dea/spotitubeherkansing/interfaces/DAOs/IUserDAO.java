package nl.han.dea.spotitubeherkansing.interfaces.DAOs;

import nl.han.dea.spotitubeherkansing.domains.User;

public interface IUserDAO {
    User get(String username);
    User getByToken(String token);
}
