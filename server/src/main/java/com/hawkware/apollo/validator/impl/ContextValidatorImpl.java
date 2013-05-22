package com.hawkware.apollo.validator.impl;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hawkware.apollo.exception.ContextValidationException;
import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.service.ContextService;
import com.hawkware.apollo.validator.ContextValidator;

public class ContextValidatorImpl implements ContextValidator {

    private static final Logger logger = LoggerFactory.getLogger(ContextValidatorImpl.class);

    @Autowired
    private ContextService contextService;

    private boolean validateContext = true;

    @Override
    public String validateContext(String context, HttpServletRequest servletRequest) throws ContextValidationException {
	String validatedContext;
	try {
	    if (validateContext) {
		validatedContext = validateContext(servletRequest);
	    } else {
		validatedContext = validateContext(context);

	    }
	} catch (ContextValidationException cve) {
	    logger.error("invalid context", cve);
	    throw new WebApplicationException(Response.status(Status.UNAUTHORIZED).header("Message", cve.getMessage())
		    .build());
	}
	return validatedContext;
    }

    private String validateContext(String appContext) throws ContextValidationException {
	if (appContext == null) {
	    throw new ContextValidationException("cannot validate a null context");
	}

	if (contextService.getContext(appContext) == null) {
	    throw new ContextValidationException("invalid context [" + appContext + "]");
	}
	return appContext;
    }

    private String validateContext(HttpServletRequest requestContext) throws ContextValidationException {

	String context = null;
	if (requestContext != null) {
	    context = deduceContext(requestContext);
	    if (context == null) {
		throw new ContextValidationException("could not validate context");
	    }

	} else {
	    throw new IllegalArgumentException("servlet request cannot be null");
	}

	return context;
    }

    String deduceContext(HttpServletRequest requestContext) throws ContextValidationException {
	String appContext = null;
	Server server = getServer(requestContext);
	logger.debug("request came from sever=" + server);

	ApplicationContext ctx = contextService.getContext(server);
	if (ctx != null) {
	    appContext = ctx.getName();
	} else {
	    throw new ContextValidationException("No context found for [" + server + "]");
	}
	return appContext;
    }

    Server getServer(HttpServletRequest requestContext) {
	Server server = new Server();
	server.setHostName(requestContext.getRemoteHost());
	server.setIpAddress(requestContext.getRemoteAddr());
	return server;
    }

    public void setContextService(ContextService contextService) {
	this.contextService = contextService;
    }

    public void setValidateContext(boolean validateContext) {
	this.validateContext = validateContext;
    }

}
