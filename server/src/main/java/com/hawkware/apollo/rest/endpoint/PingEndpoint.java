package com.hawkware.apollo.rest.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/ping")
public class PingEndpoint {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping() {
		return Response.ok("OK").build();
	}
}
