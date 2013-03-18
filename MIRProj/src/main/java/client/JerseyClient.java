package client;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import common.CommonObjectMapper;

import domain.User;

public class JerseyClient {

	WebResource _webResource;
	ObjectMapper _objectMapper = CommonObjectMapper.INSTANCE.getObjectMapper();

	public JerseyClient() {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		_webResource = client.resource(getBaseURI());
	}

	public static void main(String[] args) {
		JerseyClient theJerseyClient = new JerseyClient();
		theJerseyClient.userCreate();
	}

	private void userCreate() {
		User anUser = new User(null, "stallone", "john", "rambo", "rambo@gmail.com", 6663331234L);
		try {
			ClientResponse aClientResponse = _webResource.path("user").path("create").type(MediaType.APPLICATION_JSON)
					.post(ClientResponse.class, _objectMapper.writeValueAsString(anUser));
			// 201=CREATED
			if (aClientResponse.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + aClientResponse.getStatus() + "=" + aClientResponse.getClientResponseStatus().getReasonPhrase());
			}

			System.out.println("Response Json String");
			String theResponseJson = aClientResponse.getEntity(String.class);
			System.out.println(theResponseJson);

		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UniformInterfaceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static URI getBaseURI() {
		// CASE-SENSITIVE URI
		return UriBuilder.fromUri("http://localhost:8080/api").build();
	}

}
