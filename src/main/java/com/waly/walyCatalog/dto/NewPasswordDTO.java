package com.waly.walyCatalog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewPasswordDTO {

    @NotBlank(message = "campo obrigatorio")

    private String token;

    @NotBlank(message = "campo obrigatorio")
    @Size(min = 8, message = "a senha deve conter no minimo 8 caracters")
    private String password;

    public NewPasswordDTO(){}

    public NewPasswordDTO(String token, String password) {
        this.token = token;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
