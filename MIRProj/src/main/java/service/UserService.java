package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import common.CommonUtil;

import dao.UserDaoImpl;
import domain.ResponseObject;
import domain.User;

@Path("/user")
public class UserService {

	private final static ObjectMapper OBJECT_MAPPER = CommonUtil.INSTANCE.getObjectMapper();
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
		String aResponseObjectJson = "null";
		try {
			User anUser = OBJECT_MAPPER.readValue(iUserJson, User.class);
			Integer theId = UserDaoImpl.USER_DAO_INSTANCE.createUser(anUser);
			anUser = new User();
			anUser.setId(theId);
			ResponseObject aResponseObject = new ResponseObject(anUser, "true");
			aResponseObjectJson = OBJECT_MAPPER.writeValueAsString(aResponseObject);
			LOG.info(aResponseObjectJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.CREATED).entity(aResponseObjectJson).build();
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
		User anUser = UserDaoImpl.USER_DAO_INSTANCE.getUserById(Integer.valueOf(iUserId));
		String aResponseObjectJson = "null";
		try {
			ResponseObject aResponseObject = new ResponseObject(anUser, "true");
			aResponseObjectJson = OBJECT_MAPPER.writeValueAsString(aResponseObject);
			LOG.info(aResponseObjectJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.OK).entity(aResponseObjectJson).build();
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
			UserDaoImpl.USER_DAO_INSTANCE.userUpdate(anUser);
			ResponseObject aResponseObject = new ResponseObject(null, "true");
			aResponseObjectJson = OBJECT_MAPPER.writeValueAsString(aResponseObject);
			LOG.info(aResponseObjectJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.ACCEPTED).entity(aResponseObjectJson).build();
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
		UserDaoImpl.USER_DAO_INSTANCE.deleteUserById(Integer.valueOf(iUserId));
		String aResponseObjectJson = "null";
		try {
			ResponseObject aResponseObject = new ResponseObject(null, "true");
			aResponseObjectJson = OBJECT_MAPPER.writeValueAsString(aResponseObject);
			LOG.info(aResponseObjectJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Response.Status.ACCEPTED).entity(aResponseObjectJson).build();
	}

	@GET
	@Path("pojo/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserPojoById(@PathParam("id") Integer iUserId) {
		LOG.info("getUserPojoById");
		User anUser = UserDaoImpl.USER_DAO_INSTANCE.getUserById(iUserId);
		return anUser;
	}

	@POST
	@Path("pojo/list")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getOtherUserPojoList(User iUser) {
		LOG.info("getOtherUserPojoList");
		Collection<User> theUsers = CommonUtil.INSTANCE.getDbUserMap().values();
		List<User> finalUsers = new ArrayList<>();
		for (User aUser : theUsers) {
			// as User equals() based on id field
			if (!aUser.equals(iUser)) {
				finalUsers.add(aUser);
			}
		}
		return finalUsers;
	}
}
