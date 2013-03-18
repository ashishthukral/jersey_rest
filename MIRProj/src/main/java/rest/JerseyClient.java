package rest;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class JerseyClient {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, UniformInterfaceException, ClientHandlerException, IOException {
		JerseyClient theJerseyClient = new JerseyClient();
		theJerseyClient.jerseyTest();
	}

	private void jerseyTest() throws JsonGenerationException, JsonMappingException, UniformInterfaceException, ClientHandlerException, IOException {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource theWebResource = client.resource(getBaseURI());
		ClientResponse response = theWebResource.accept("application/json").get(ClientResponse.class);

		Car car = new Car(1, "audi");

		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE));
		response = theWebResource.path("rest").path("hello").path("postjson").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, mapper.writeValueAsString(car));

		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + "=" + response.getClientResponseStatus().getReasonPhrase());
		}

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	private static URI getBaseURI() {
		// CASE-SENSITIVE URI
		return UriBuilder.fromUri("http://localhost:8080/MIRProj").build();
	}

}
