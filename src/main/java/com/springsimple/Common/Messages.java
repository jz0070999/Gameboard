package com.springsimple.Common;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@RequestScope
@Service
public class Messages {

    private List<String> errors;

    public Messages() {
        errors = new LinkedList<String>();
    }

    public void AddError(String error) {
        this.errors.add(error);
    }

    public List<String> GetErrors() {
        return this.errors;
    }


}
