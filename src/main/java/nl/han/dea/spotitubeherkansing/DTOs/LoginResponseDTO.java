package nl.han.dea.spotitubeherkansing.DTOs;

import javax.json.bind.annotation.JsonbProperty;
import java.util.UUID;

public class LoginResponseDTO {
    @JsonbProperty("token")
    private String token;
    @JsonbProperty("user")
    private String name;

    public LoginResponseDTO(UUID token, String name){
        this.token = token.toString();
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
