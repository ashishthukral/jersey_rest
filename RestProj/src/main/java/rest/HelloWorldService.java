package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class HelloWorldService {

	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		// http://localhost:8080/RestProj/rest/hello/yo
		String output = "Jersey say  : " + msg;
		return Response.status(200).entity(output).build();
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello Jersey";
	}

	// @GET
	// @Path("/{param1}")
	// public Response getMsg1(@PathParam("param1") String msg) {
	// // http://localhost:8080/RestProj/rest/hello/yo
	// String output = "qqqqqqq say : " + msg;
	// return Response.status(200).entity(output).build();
	// }
}
