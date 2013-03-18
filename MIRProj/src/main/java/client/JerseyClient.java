package client;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import common.CommonObjectMapper;

import domain.ResponseObject;
import domain.User;

public class JerseyClient {

	private WebResource _webResource;
	private final static ObjectMapper OBJECT_MAPPER = CommonObjectMapper.INSTANCE.getObjectMapper();
	private final static Logger LOG = Logger.getLogger(JerseyClient.class);

	public JerseyClient() {
		ClientConfig config = new DefaultClientConfig();
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(config);
		_webResource = client.resource(getBaseURI());
	}

	public static void main(String[] args) {
		JerseyClient theJerseyClient = new JerseyClient();
		LOG.info("new user Id=" + theJerseyClient.userCreate());
		LOG.info(theJerseyClient.getUserById(666));
		theJerseyClient.userUpdate(987);
		theJerseyClient.deleteUserById(123);

	}

	private Integer userCreate() {
		Integer theUserId = null;
		User anUser = new User(null, "stallone", "john", "rambo", "rambo@gmail.com", 666_333_1234L);
		try {
			ClientResponse aClientResponse = _webResource.path("user").path("create").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, OBJECT_MAPPER.writeValueAsString(anUser));
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

	private void userUpdate(Integer iUserId) {
		User anUser = new User(null, "arnold", "Terminator", "machine", "arnold@gmail.com", 111_222_3456L);
		try {
			ClientResponse aClientResponse = _webResource.path("user").path(iUserId.toString()).type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, OBJECT_MAPPER.writeValueAsString(anUser));
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

	private static URI getBaseURI() {
		// CASE-SENSITIVE URI
		return UriBuilder.fromUri("http://localhost:8080/api").build();
	}

}
