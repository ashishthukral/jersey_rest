package rest;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class JerseyClient {

	/*
	 * OK 200 - The request was fulfilled.
	 * 
	 * CREATED 201 - Following a POST command, this indicates success, but the textual part of the response line indicates the URI by which the newly created document should be
	 * known.
	 * 
	 * Accepted 202 - The request has been accepted for processing, but the processing has not been completed. The request may or may not eventually be acted upon, as it may be
	 * disallowed when processing actually takes place. there is no facility for status returns from asynchronous operations such as this.
	 * 
	 * Partial Information 203 - When received in the response to a GET command, this indicates that the returned meta information is not a definitive set of the object from a
	 * server with a copy of the object, but is from a private overlaid web. This may include annotation information about the object, for example.
	 * 
	 * No Response 204
	 */

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

		Car car = new Car(1, "audi");
		Gson gson = new Gson();
		String json = gson.toJson(car);
		response = theWebResource.path("rest").path("hello").path("post").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);

		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		System.out.println("Output from Server .... \n");
		output = response.getEntity(String.class);
		System.out.println(output);
		car = gson.fromJson(output, Car.class);
		System.out.println(car.toString());
	}

	private static URI getBaseURI() {
		// CASE-SENSITIVE URI
		return UriBuilder.fromUri("http://localhost:8080/RestProj").build();
	}

}
