package com.waly.walyCatalog.dto.Exceptions;

import java.util.ArrayList;
import java.util.List;

public class CustonErrorValidation extends CustomError{

    private List<FieldMessage> errors = new ArrayList<>();

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message){
        errors.add(new FieldMessage(fieldName, message));
    }
}
