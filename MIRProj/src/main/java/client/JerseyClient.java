package client;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

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

	WebResource _webResource;
	ObjectMapper _objectMapper = CommonObjectMapper.INSTANCE.getObjectMapper();

	public JerseyClient() {
		ClientConfig config = new DefaultClientConfig();
		config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(config);
		_webResource = client.resource(getBaseURI());
	}

	public static void main(String[] args) {
		JerseyClient theJerseyClient = new JerseyClient();
		System.out.println("new user Id=" + theJerseyClient.userCreate());
		System.out.println(theJerseyClient.getUserById(666));
	}

	private User getUserById(Integer iUserId) {
		User anUser = null;
		try {
			ClientResponse aClientResponse = _webResource.path("user").path(iUserId.toString()).type(MediaType.TEXT_PLAIN).get(ClientResponse.class);
			// 200=OK
			if (aClientResponse.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + aClientResponse.getStatus() + "=" + aClientResponse.getClientResponseStatus().getReasonPhrase());
			}

			String theResponseJson = aClientResponse.getEntity(String.class);
			System.out.println("getUserById - Response Json String");
			System.out.println(theResponseJson);
			ResponseObject aResponseObject = _objectMapper.readValue(theResponseJson, ResponseObject.class);
			anUser = aResponseObject.getUser();
			// System.out.println(aResponseObject);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return anUser;
	}

	private Integer userCreate() {
		Integer theUserId = null;
		User anUser = new User(null, "stallone", "john", "rambo", "rambo@gmail.com", 6663331234L);
		try {
			ClientResponse aClientResponse = _webResource.path("user").path("create").type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, _objectMapper.writeValueAsString(anUser));
			// 201=CREATED
			if (aClientResponse.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + aClientResponse.getStatus() + "=" + aClientResponse.getClientResponseStatus().getReasonPhrase());
			}

			String theResponseJson = aClientResponse.getEntity(String.class);
			System.out.println("userCreate - Response Json String");
			System.out.println(theResponseJson);
			ResponseObject aResponseObject = _objectMapper.readValue(theResponseJson, ResponseObject.class);
			// System.out.println(aResponseObject);
			theUserId = aResponseObject.getUser().getId();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return theUserId;
	}

	private static URI getBaseURI() {
		// CASE-SENSITIVE URI
		return UriBuilder.fromUri("http://localhost:8080/api").build();
	}

}
