package in.summtech.application.monitor.server.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/status")
public class StatusRest {

	@GET
	@Path("/hello")
	public Response hello() {
		return buildResponse("({ \"hello\" : \"world\" })");
	}

	@POST
	@Path("/serverStatus")
	public Response serverStatus() {
		return buildResponse(new WebServer("teste", "teste", 1, "teste", new ArrayList<Service>()));
	}
	
	/**
	 * Build the response for the client
	 * @param entity
	 * @return
	 */
	private Response buildResponse(Object entity) {
		ResponseBuilder responseBuilder = Response.ok(entity, MediaType.APPLICATION_JSON);
		responseBuilder.header("Access-Control-Allow-Origin", "*");
		Response response = responseBuilder.build();
		return response;
	}
	
	/**
	 * Represents the WebServer to be monitored
	 * Innner class for explanatory purposes
	 * @author Fernando
	 */
	private class WebServer {
		
		private String name;
		private String url;
		private Integer status; // 1 - fully functional (green) / 2 - some
								// service stoped or minor problem (orange) / 3 - some serious
								// problem detected (red)
		private String msg;
		private List<Service> services;
		
		/**
		 * Default constructor
		 * @param name name of this webservice (better receive from the client because it could be running in many servers)
		 * @param url url of this webservice (better receive from the client because it could be running in many servers)
		 * @param status service status from 1 to 3
		 * @param msg 
		 * @param services like databases, elasticsearch or jcr
		 */
		public WebServer(String name, String url, Integer status, String msg, List<Service> services) {
			super();
			this.name = name;
			this.url = url;
			this.status = status;
			this.msg = msg;
			this.services = services;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public List<Service> getServices() {
			return services;
		}

		public void setServices(List<Service> services) {
			this.services = services;
		}
	}
	
	/**
	 * Some service for monitoring like Database, ElasticSearch or JCR
	 * Innner class for explanatory purposes
	 * @author Fernando
	 *
	 */
	private class Service {
		
		private String name;
		private String url;
		private Integer status; // 1 - fully functional (green) / 2 - slow or problem detected
								// (orange) / 3 - stopped (red)
		private String msg;
		
		/**
		 * Default constructor 
		 * @param name name of this service associated to the webserver
		 * @param url url of this service
		 * @param status from 1 to 3
		 * @param msg to describe failure or success
		 */
		public Service(String name, String url, Integer status, String msg) {
			super();
			this.name = name;
			this.url = url;
			this.status = status;
			this.msg = msg;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}
