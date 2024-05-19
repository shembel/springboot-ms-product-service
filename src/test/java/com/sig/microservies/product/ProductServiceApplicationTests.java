package com.sig.microservies.product;

import io.restassured.RestAssured;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.web.bind.annotation.RestController;
import org.testcontainers.containers.MongoDBContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	// by adding this annotation we don't have to manually specify mongoDb uri - SpringBoot will inject the relevant URI details
	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	// this annotation will check the port on which the application is running and inject the port into the variable below;
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		// this dependency is to allow REST calls from our integration tests (add to pom.xml first)
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() {
		String requestBody = """
				{
				    "id": "664a0e7b730da40dd949dc83",
				    "name": "iPhone 15",
				    "description": "iPhone 15 is a smartphone from Apple",
				    "price": 1000
				  }
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("iPhone 15"))
				.body("description", Matchers.equalTo("iPhone 15 is a smartphone from Apple"))
				.body("price", Matchers.equalTo(1000));
	}
}
