package rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
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
		theJerseyClient.jaxbTest();
	}

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
	private void jerseyTest() throws JsonGenerationException, JsonMappingException, UniformInterfaceException, ClientHandlerException, IOException {
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
		// String json = gson.toJson(car);
		// response = theWebResource.path("rest").path("hello").path("postjson").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, json);

		ObjectMapper mapper = new ObjectMapper();
		// mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
		// .withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE).withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE));
		response = theWebResource.path("rest").path("hello").path("postjson").type(MediaType.APPLICATION_JSON).post(ClientResponse.class, mapper.writeValueAsString(car));

		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus() + "=" + response.getClientResponseStatus().getReasonPhrase());
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

	private void jaxbTest() {
		ArrayList<Book> bookList = new ArrayList<Book>();

		// create books
		Book book1 = new Book();
		book1.setIsbn("978-0060554736");
		book1.setName("The Game");
		book1.setAuthor("Neil Strauss");
		book1.setPublisher("Harpercollins");
		bookList.add(book1);

		Book book2 = new Book();
		book2.setIsbn("978-3832180577");
		book2.setName("Feuchtgebiete");
		book2.setAuthor("Charlotte Roche");
		book2.setPublisher("Dumont Buchverlag");
		bookList.add(book2);

		// create bookstore, assigning book
		Bookstore bookstore = new Bookstore();
		bookstore.setName("Fraport Bookstore");
		bookstore.setLocation("Frankfurt Airport");
		bookstore.setBookList(bookList);

		try {
			// create JAXB context and instantiate marshaller
			JAXBContext context = JAXBContext.newInstance(Bookstore.class);
			Marshaller theMarshaller = context.createMarshaller();
			theMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out
			// m.marshal(bookstore, System.out);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			theMarshaller.marshal(bookstore, baos);
			String jaxbString = baos.toString();
			System.out.println("Output from Marshaller");
			System.out.println(jaxbString);

			InputStream theInputStream = new ByteArrayInputStream(jaxbString.getBytes());
			// get variables from our xml file, created before
			System.out.println("Output from Un-Marshaller");
			Unmarshaller theUnmarshaller = context.createUnmarshaller();
			Bookstore bookstore2 = (Bookstore) theUnmarshaller.unmarshal(theInputStream);
			ArrayList<Book> list = bookstore2.getBooksList();
			for (Book book : list) {
				System.out.println("Book: " + book.getName() + " from " + book.getAuthor());
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
