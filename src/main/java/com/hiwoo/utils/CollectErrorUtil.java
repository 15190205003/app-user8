package com.hiwoo.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

public class CollectErrorUtil {

    public static String CollectError(BindingResult result)
    {
        StringBuilder sb = new StringBuilder ( "" );
        if(result.hasErrors()){
            List<ObjectError> errors=result.getAllErrors();
            for (ObjectError x : errors) {
                sb.append(x.getDefaultMessage());
            }
        };
        return sb.toString();
    }
}
