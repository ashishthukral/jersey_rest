package client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import common.CommonUtil;

import domain.ResponseObject;
import domain.User;

public class JerseyClient {

	private WebResource _webResource;
	private final static ObjectMapper OBJECT_MAPPER = CommonUtil.INSTANCE.getObjectMapper();
	private final static Logger LOG = Logger.getLogger(JerseyClient.class);
	// CASE-SENSITIVE URI
	private final static URI SERVICE_URI = UriBuilder.fromUri("http://localhost:8080/api").build();

	public JerseyClient() {
		ClientConfig config = new DefaultClientConfig();
		// config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
		config.getClasses().add(JacksonJsonProvider.class);
		Client client = Client.create(config);
		_webResource = client.resource(SERVICE_URI);
	}

	public static void main(String[] args) {
		JerseyClient theJerseyClient = new JerseyClient();
		// User anUser = new User(null, "stallone", "john", "rambo", "rambo@gmail.com", 666_333_1234L);
		// theJerseyClient.readFile();
		// LOG.info("new user Id=" + theJerseyClient.userCreate(anUser));
		// LOG.info(theJerseyClient.getUserById(2));
		// anUser = new User(2, "arnold", "Terminator", "machine", "arnold@gmail.com", 111_222_3456L);
		// theJerseyClient.userUpdate(anUser);
		// theJerseyClient.deleteUserById(3);
		// theJerseyClient.sync();
		// LOG.info(theJerseyClient.getUserPojoById(2));
		LOG.info(theJerseyClient.getOtherUserPojoList(new User(2, null, null, null, null, null)));
	}

	private User getUserPojoById(Integer iUserId) {
		return _webResource.path("user").path("pojo").path(iUserId.toString()).type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON).get(User.class);
	}

	public List<User> getOtherUserPojoList(User iUser) {
		List<User> users = _webResource.path("user").path("pojo/list").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(List.class, iUser);
		return users;
	}

	// private void sync() {
	// List<User> theAcmeUserList = readFile();
	// for (User theAcmeUser : theAcmeUserList) {
	// User theDBUser = getUserById(theAcmeUser.getId());
	// // user not in db, so create new
	// if (theDBUser == null) {
	// userCreate(theAcmeUser);
	// } // user in db, check for any new updated fields
	// else {
	// if (!areUsersEqual(theAcmeUser, theDBUser)) {
	// userUpdate(theAcmeUser);
	// }
	// }
	// LOG.info(theDBUser);
	// }
	// }

	private boolean areUsersEqual(User iUserAcme, User iUserDB) {
		boolean isEqual = true;
		if (!iUserAcme.getFirstName().equals(iUserDB.getFirstName())) {
			isEqual = false;
		} else if (!iUserAcme.getLastName().equals(iUserDB.getLastName())) {
			isEqual = false;
		} else if (!iUserAcme.getEmailAddress().equals(iUserDB.getEmailAddress())) {
			isEqual = false;
		} else if (!iUserAcme.getPhoneNumber().equals(iUserDB.getPhoneNumber())) {
			isEqual = false;
		}
		return isEqual;
	}

	private List<User> readFile() {
		// HELPFUL TIP - use to print current path where executing
		// System.out.println(new File(".").getAbsolutePath());
		// Location of file to read
		File file = new File("src/main/resources/users.txt");
		List<User> theAcmeFileUserList = new ArrayList<>();
		try {
			User anUser;
			Scanner scanner = new Scanner(file);
			// TODO
			// remove artificial id addition
			// Integer id = 1;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				// System.out.println(line);
				// skips lines starting with //, to treat them as comments
				if (line.startsWith("//"))
					continue;
				String[] userDetails = line.split(",");
				anUser = new User(null, userDetails[0] + " " + userDetails[1], userDetails[0], userDetails[1], userDetails[2], Long.valueOf(userDetails[3]));
				// anUser = new User(id++, userDetails[0] + " " + userDetails[1], userDetails[0], userDetails[1], userDetails[2], Long.valueOf(userDetails[3]));
				theAcmeFileUserList.add(anUser);
			}
			scanner.close();
			LOG.info(theAcmeFileUserList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return theAcmeFileUserList;
	}

	private Integer userCreate(User iUser) {
		Integer theUserId = null;
		try {
			ClientResponse aClientResponse = _webResource.path("user").path("create").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, OBJECT_MAPPER.writeValueAsString(iUser));
			if (aClientResponse.getStatus() != Response.Status.CREATED.getStatusCode()) {
				throw new RuntimeException("Failed : HTTP error code : " + aClientResponse.getStatus() + "=" + aClientResponse.getClientResponseStatus().getReasonPhrase());
			}

			String theResponseJson = aClientResponse.getEntity(String.class);
			LOG.info("userCreate - Response Json String");
			LOG.info(theResponseJson);
			ResponseObject aResponseObject = OBJECT_MAPPER.readValue(theResponseJson, ResponseObject.class);
			// LOG.info(aResponseObject);
			theUserId = aResponseObject.getUser().getId();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return theUserId;
	}

	private User getUserById(Integer iUserId) {
		User anUser = null;
		try {
			ClientResponse aClientResponse = _webResource.path("user").path(iUserId.toString()).type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON)
					.get(ClientResponse.class);
			if (aClientResponse.getStatus() != Response.Status.OK.getStatusCode()) {
				throw new RuntimeException("Failed : HTTP error code : " + aClientResponse.getStatus() + "=" + aClientResponse.getClientResponseStatus().getReasonPhrase());
			}

			String theResponseJson = aClientResponse.getEntity(String.class);
			LOG.info("getUserById - Response Json String");
			LOG.info(theResponseJson);
			ResponseObject aResponseObject = OBJECT_MAPPER.readValue(theResponseJson, ResponseObject.class);
			anUser = aResponseObject.getUser();
			// LOG.info(aResponseObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return anUser;
	}

	private void userUpdate(User iUser) {
		try {
			ClientResponse aClientResponse = _webResource.path("user").path(iUser.getId().toString()).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, OBJECT_MAPPER.writeValueAsString(iUser));
			if (aClientResponse.getStatus() != Response.Status.ACCEPTED.getStatusCode()) {
				throw new RuntimeException("Failed : HTTP error code : " + aClientResponse.getStatus() + "=" + aClientResponse.getClientResponseStatus().getReasonPhrase());
			}
			String theResponse = aClientResponse.getEntity(String.class);
			LOG.info("userUpdate - Response Json String");
			LOG.info(theResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void deleteUserById(Integer iUserId) {
		try {
			ClientResponse aClientResponse = _webResource.path("user").path(iUserId.toString()).type(MediaType.TEXT_PLAIN).accept(MediaType.APPLICATION_JSON)
					.delete(ClientResponse.class);
			if (aClientResponse.getStatus() != Response.Status.ACCEPTED.getStatusCode()) {
				throw new RuntimeException("Failed : HTTP error code : " + aClientResponse.getStatus() + "=" + aClientResponse.getClientResponseStatus().getReasonPhrase());
			}

			String theResponseJson = aClientResponse.getEntity(String.class);
			LOG.info("deleteUserById - Response Json String");
			LOG.info(theResponseJson);
			ResponseObject aResponseObject = OBJECT_MAPPER.readValue(theResponseJson, ResponseObject.class);
			// LOG.info(aResponseObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
