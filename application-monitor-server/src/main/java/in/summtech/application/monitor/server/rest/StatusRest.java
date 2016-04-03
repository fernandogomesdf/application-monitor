package in.summtech.application.monitor.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.json.JsonObject;
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
	public Response serverStatus(JsonObject data) {

		// Implement your code here - begin

		int simulatedStatus1 = simulateStatus();
		Service service1 = new Service("Oracle", "java:comp/env/jdbc/OracleDataSource", simulatedStatus1,
				getSimulatedMessage(simulatedStatus1));
		
		int simulatedStatus2 = simulateStatus();
		Service service2 = new Service("MySQL", "java:comp/env/jdbc/MySQLDataSource", simulatedStatus2,
				getSimulatedMessage(simulatedStatus2));
		
		int simulatedStatus3 = simulateStatus();
		Service service3 = new Service("LDAP", "ldap://localhost:389/cn=homedir,cn=Jon%20Ruiz,ou=people,o=JNDITutorial", simulatedStatus3,
				getSimulatedMessage(simulatedStatus3));

		int simulatedWebStatus = simulateStatus();
		WebServer webserver = new WebServer(data.getInt("id"), data.getString("name"), data.getString("url"),
				simulatedWebStatus, getSimulatedMessage(simulatedWebStatus), new ArrayList<Service>());
		
		webserver.getServices().add(service1);
		webserver.getServices().add(service2);
		webserver.getServices().add(service3);
		
		// Implement your code here - end

		return buildResponse(webserver);
	}

	private int simulateStatus() {
		Random rand = new Random();
		int max = 3;
		int min = 1;
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	private String getSimulatedMessage(int status) {
		String message = null;
		switch (status) {
		case 1:
			message = "Ok";
			break;
		case 2:
			message = "Slow";
			break;
		case 3:
			message = "Not Working";
			break;
		default:
			message = "Unknow failure";
			break;
		}
		return message;
	}

	/**
	 * Build the response for the client
	 * 
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
	 * Represents the WebServer to be monitored Innner class for explanatory
	 * purposes
	 * 
	 * @author Fernando
	 */
	public class WebServer {
		private Integer id;
		private String name;
		private String url;
		private Integer status; // 1 - fully functional (green) / 2 - some
								// service stoped or minor problem (orange) / 3
								// - some serious
								// problem detected (red)
		private String msg;
		private List<Service> services;

		/**
		 * Default constructor
		 * 
		 * @param name
		 *            name of this webservice (better receive from the client
		 *            because it could be running in many servers)
		 * @param url
		 *            url of this webservice (better receive from the client
		 *            because it could be running in many servers)
		 * @param status
		 *            service status from 1 to 3
		 * @param msg
		 * @param services
		 *            like databases, elasticsearch or jcr
		 */
		public WebServer(Integer id, String name, String url, Integer status, String msg, List<Service> services) {
			super();
			this.id = id;
			this.name = name;
			this.url = url;
			this.status = status;
			this.msg = msg;
			this.services = services;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
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
	 * Some service for monitoring like Database, ElasticSearch or JCR Innner
	 * class for explanatory purposes
	 * 
	 * @author Fernando
	 *
	 */
	public class Service {

		private String name;
		private String url;
		private Integer status; // 1 - fully functional (green) / 2 - slow or
								// problem detected
								// (orange) / 3 - stopped (red)
		private String msg;

		/**
		 * Default constructor
		 * 
		 * @param name
		 *            name of this service associated to the webserver
		 * @param url
		 *            url of this service
		 * @param status
		 *            from 1 to 3
		 * @param msg
		 *            to describe failure or success
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

