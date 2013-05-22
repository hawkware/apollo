package com.hawkware.apollo.validator;

import javax.servlet.http.HttpServletRequest;

import com.hawkware.apollo.exception.ContextValidationException;

public interface ContextValidator {
    String validateContext(String context, HttpServletRequest requestContext) throws ContextValidationException;
}