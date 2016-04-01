package in.summtech.application.monitor.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/status")
public class StatusRest {

	@GET
	@Path("/hello")
	public Response hello() {
		ResponseBuilder responseBuilder = Response.ok("{ \"hello\" : \"world\" }", MediaType.APPLICATION_JSON);
		Response response = responseBuilder.build();
		return response;
	}
}
