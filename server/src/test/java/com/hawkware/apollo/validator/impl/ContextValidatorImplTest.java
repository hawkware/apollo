package com.hawkware.apollo.validator.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.hawkware.apollo.exception.ContextValidationException;
import com.hawkware.apollo.model.ApplicationContext;
import com.hawkware.apollo.model.Server;
import com.hawkware.apollo.service.ContextService;

public class ContextValidatorImplTest {

    private ContextValidatorImpl contextValidatorImpl;

    private HttpServletRequest httpServletRequest;

    private ContextService contextService;

    @Before
    public void setUp() {
	contextValidatorImpl = new ContextValidatorImpl();
	httpServletRequest = Mockito.mock(HttpServletRequest.class);
	contextService = Mockito.mock(ContextService.class);
	contextValidatorImpl.setContextService(contextService);
    }

    @Test
    public void testValidateContext() throws ContextValidationException {
	String expectedCtx = "dev";

	Mockito.when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
	Mockito.when(contextService.getContext((String) Mockito.anyObject())).thenReturn(
		new ApplicationContext(expectedCtx));
	String actualContext = contextValidatorImpl.validateContext(expectedCtx, httpServletRequest);
	assertEquals(expectedCtx, actualContext);

    }

    @Test
    public void testDeduceContextValidServer() throws ContextValidationException {
	String expectedCtx = "dev";
	ApplicationContext ctx = new ApplicationContext(expectedCtx);

	Mockito.when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
	Mockito.when(httpServletRequest.getRemoteHost()).thenReturn("localhost");
	Mockito.when(contextService.getContext((Server) Mockito.anyObject())).thenReturn(ctx);

	String actualContext = contextValidatorImpl.deduceContext(httpServletRequest);

	assertEquals(expectedCtx, actualContext);

    }

    @Test(expected = ContextValidationException.class)
    public void testDeduceContextNullContext() throws ContextValidationException {
	Mockito.when(contextService.getContext((Server) Mockito.anyObject())).thenReturn(null);
	contextValidatorImpl.deduceContext(httpServletRequest);
    }

    @Test
    public void testGetServer() {
	Mockito.when(httpServletRequest.getRemoteAddr()).thenReturn("127.0.0.1");
	Mockito.when(httpServletRequest.getRemoteHost()).thenReturn("localhost");

	Server expectedServer = contextValidatorImpl.getServer(httpServletRequest);

	assertNotNull(expectedServer.getHostName());
	assertNotNull(expectedServer.getIpAddress());

	assertEquals("localhost", expectedServer.getHostName());
	assertEquals("127.0.0.1", expectedServer.getIpAddress());

    }

}
