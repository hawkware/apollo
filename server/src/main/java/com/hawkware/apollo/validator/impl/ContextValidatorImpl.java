package com.hawkware.apollo.validator.impl;

import javax.servlet.http.HttpServletRequest;

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

	@Override
	public String validateContext(String appContext, HttpServletRequest requestContext)
			throws ContextValidationException {
		if (appContext == null) {
			appContext = deduceContext(requestContext);
		} else if (contextService.getContext(appContext) == null) {
			throw new ContextValidationException("invalid context [" + appContext + "]");
		}
		return appContext;
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
}
