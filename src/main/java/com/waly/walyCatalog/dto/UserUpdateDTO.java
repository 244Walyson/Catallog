package com.waly.walyCatalog.dto;

import com.waly.walyCatalog.services.validation.UserInsertValid;
import com.waly.walyCatalog.services.validation.UserUpdateValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO{

}
