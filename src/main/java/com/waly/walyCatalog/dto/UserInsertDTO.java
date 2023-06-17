package com.waly.walyCatalog.dto;

import com.waly.walyCatalog.entities.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserInsertDTO extends UserDTO{

    @NotBlank(message = "campo obrigatório")
    @Size(min = 6, message = "a senha deve haver no minimo 6 caracteres")
    private String password;

    public UserInsertDTO(){super();}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
