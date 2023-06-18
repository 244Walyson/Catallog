package com.waly.walyCatalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import com.waly.walyCatalog.Repositories.UserRepository;
import com.waly.walyCatalog.dto.Exceptions.FieldMessage;
import com.waly.walyCatalog.entities.User;
import com.waly.walyCatalog.services.validation.UserInsertValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.waly.walyCatalog.dto.UserInsertDTO;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository userRepository;
    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        User user = userRepository.findByEmail(dto.getEmail());
        if(user != null){
            list.add(new FieldMessage("Email", "Email ja cadastrado"));
        }
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}