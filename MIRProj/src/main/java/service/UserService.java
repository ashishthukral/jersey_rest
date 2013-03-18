package service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import common.CommonObjectMapper;

import domain.ResponseObject;
import domain.User;

@Path("/user")
public class UserService {

	private final static ObjectMapper OBJECT_MAPPER = CommonObjectMapper.INSTANCE.getObjectMapper();
	private final static Logger LOG = Logger.getLogger(UserService.class);

	/*
	 * Call POST http://ws.example.com/api/user/create
	 * 
	 * Parameters username, firstName, lastName, emailAddress and phoneNumber
	 * 
	 * Response {"id":<internal id>,"success":true}, actual = {"user":{"id":333},"success":"true"}
	 */
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(String iUserJson) {
		LOG.info("createUser");
		Integer theId = 333;
		String aResponseObjectJson = "null";
		try {
			User anUser = OBJECT_MAPPER.readValue(iUserJson, User.class);
			anUser.setId(theId);
			// LOG.info(anUser);
			// save(anUser);
			anUser = new User();
			anUser.setId(theId);
			ResponseObject aResponseObject = new ResponseObject(anUser, "true");
			aResponseObjectJson = OBJECT_MAPPER.writeValueAsString(aResponseObject);
			LOG.info(aResponseObjectJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(201).entity(aResponseObjectJson).build();
	}

	/*
	 * Call GET http://ws.example.com/api/user/<id>
	 * 
	 * Response {“user”:{“firstName” . . .}, “success”: true}
	 */
	@GET
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") String iUserId) {
		LOG.info("getUserById");
		// LOG.info(iUserId);
		User anUser = new User(Integer.valueOf(iUserId), "stallone", "john", "rambo", "rambo@gmail.com", 666_333_1234L);
		String aResponseObjectJson = "null";
		try {
			ResponseObject aResponseObject = new ResponseObject(anUser, "true");
			aResponseObjectJson = OBJECT_MAPPER.writeValueAsString(aResponseObject);
			LOG.info(aResponseObjectJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(aResponseObjectJson).build();
	}

	/*
	 * Call POST http://ws.example.com/api/user/<id>
	 * 
	 * Parameters username, firstName, lastName, emailAddress and phoneNumber
	 * 
	 * Response {"success":true}
	 */
	@POST
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response userUpdate(@PathParam("id") String iUserId, String iUserJson) {
		LOG.info("userUpdate");
		Integer theId = Integer.valueOf(iUserId);
		String aResponseObjectJson = "null";
		try {
			User anUser = OBJECT_MAPPER.readValue(iUserJson, User.class);
			anUser.setId(theId);
			// LOG.info(anUser);
			// save(anUser);
			ResponseObject aResponseObject = new ResponseObject(null, "true");
			aResponseObjectJson = OBJECT_MAPPER.writeValueAsString(aResponseObject);
			LOG.info(aResponseObjectJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(aResponseObjectJson).build();
	}

	/*
	 * Call DELETE http://ws.example.com/api/user/<id>
	 * 
	 * Response {"deleted":true, "success":true}
	 */
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUserById(@PathParam("id") String iUserId) {
		LOG.info("deleteUserById");
		// LOG.info(iUserId);
		// delete(iUserId)
		String aResponseObjectJson = "null";
		try {
			ResponseObject aResponseObject = new ResponseObject(null, "true");
			aResponseObjectJson = OBJECT_MAPPER.writeValueAsString(aResponseObject);
			LOG.info(aResponseObjectJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(200).entity(aResponseObjectJson).build();
	}
}
