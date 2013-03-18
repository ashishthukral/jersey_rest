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

import org.codehaus.jackson.map.ObjectMapper;

import common.CommonObjectMapper;

import domain.ResponseObject;
import domain.User;

@Path("/user")
public class UserService {

	ObjectMapper _objectMapper = CommonObjectMapper.INSTANCE.getObjectMapper();

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(String iUserJson) {
		Integer theId = 333;
		String aResponseObjectJson = "null";
		try {
			User anUser = new User();
			anUser.setId(theId);
			ResponseObject aResponseObject = new ResponseObject(anUser, "true");
			aResponseObjectJson = _objectMapper.writeValueAsString(aResponseObject);
			System.out.println(aResponseObjectJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(201).entity(aResponseObjectJson).build();
	}

	@GET
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") String iUserId) {
		System.out.println(iUserId);
		User anUser = new User(Integer.valueOf(iUserId), "stallone", "john", "rambo", "rambo@gmail.com", 6663331234L);
		String aResponseObjectJson = "null";
		try {
			ResponseObject aResponseObject = new ResponseObject(anUser, "true");
			aResponseObjectJson = _objectMapper.writeValueAsString(aResponseObject);
			System.out.println(aResponseObjectJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(aResponseObjectJson).build();
	}

}
