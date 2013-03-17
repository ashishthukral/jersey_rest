package rest;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class JerseyClient {

	public static void main(String[] args) {

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource theWebResource = client.resource(getBaseURI());
		ClientResponse response = theWebResource.accept("application/json").get(ClientResponse.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		System.out.println("Output from Server...");
		System.out.println(response.toString());
		String output = response.getEntity(String.class);
		System.out.println(output);

		// Fluent interfaces
		System.out.println(theWebResource.path("rest").path("hello").accept(MediaType.TEXT_PLAIN).get(ClientResponse.class).toString());
		// Get plain text
		System.out.println(theWebResource.path("rest").path("hello").accept(MediaType.TEXT_PLAIN).get(String.class));
		// Get XML
		System.out.println(theWebResource.path("rest").path("hello").accept(MediaType.TEXT_XML).get(String.class));
		// The HTML
		System.out.println(theWebResource.path("rest").path("hello").accept(MediaType.TEXT_HTML).get(String.class));

	}

	private static URI getBaseURI() {
		// CASE-SENSITIVE URI
		return UriBuilder.fromUri("http://localhost:8080/RestProj").build();
	}

}
