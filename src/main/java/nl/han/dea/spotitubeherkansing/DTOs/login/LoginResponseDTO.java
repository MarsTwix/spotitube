package nl.han.dea.spotitubeherkansing.DTOs.login;

import javax.json.bind.annotation.JsonbProperty;

public class LoginResponseDTO {
    @JsonbProperty("token")
    private String token;
    @JsonbProperty("user")
    private String name;

    public LoginResponseDTO(String token, String name){
        this.token = token;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
