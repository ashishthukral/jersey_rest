package service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import common.CommonObjectMapper;

import domain.User;

@Path("/user")
public class UserService {

	ObjectMapper _objectMapper = CommonObjectMapper.INSTANCE.getObjectMapper();

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response createUser(String iUserJson) {
		Integer theId = 3;
		String theResult = "{\"id\":" + theId + ",\"success\":true}";
		try {
			User anUser = _objectMapper.readValue(iUserJson, User.class);
			System.out.println(iUserJson);
			System.out.println(anUser);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(201).entity(theResult).build();

	}

	@GET
	@Path("{message}")
	public Response getMsg(@PathParam("message") String msg) {
		// http://localhost:8080/RestProj/rest/hello/yo
		String output = "Message: " + msg;
		return Response.status(200).entity(output).build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello Jersey text";
	}

	@GET
	@Path("{message}/length")
	public String getMsgLength(@PathParam("message") String msg) {
		// http://localhost:8080/RestProj/rest/hello/yo/length
		String output = "Message length: " + msg.length();
		return output;
	}

	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayXMLHello() {
		return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey xml" + "</hello>";
	}

	// This method is called if HTML is request
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		return "<html>" + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" + "Hello Jersey html" + "</body></h1>" + "</html> ";
	}

	@POST
	@Path("/postjson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(String iCarJson) {
		System.out.println(iCarJson.toString());
		String result = "";
		return Response.status(201).entity(result).build();
	}

}
