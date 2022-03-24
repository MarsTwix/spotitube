package nl.han.dea.spotitubeherkansing.interfaces.DAOs;

public interface IUserTokenDAO {
    void setUserToken(int UserId, String token);
    boolean tokenExists(String token);
    String getUsernameByToken(String token);
}
