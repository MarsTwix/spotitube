package nl.han.dea.spotitubeherkansing.DTOs;

import javax.json.bind.annotation.JsonbProperty;

public class LoginRequestDTO {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}
