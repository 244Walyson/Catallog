package com.waly.walyCatalog.dto;

import com.waly.walyCatalog.entities.User;
import com.waly.walyCatalog.services.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@UserInsertValid
public class UserInsertDTO extends UserDTO{

    @NotBlank(message = "campo obrigatório")
    @Size(min = 8, message = "a senha deve haver no minimo 8 caracteres")
    private String password;

    public UserInsertDTO(){super();}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
