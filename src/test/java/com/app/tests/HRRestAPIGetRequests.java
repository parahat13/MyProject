package com.app.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HRRestAPIGetRequests {

	/*
	 * When I send GET request url "http://34.223.219.142:1212/ords/hr/employees"
	 * Then response status should be 200
	 */
	// @Test
	public void simpleGet() {

		when().get("http://34.223.219.142:1212/ords/hr/employees").then().statusCode(200);

	}

	// When I send GET request url "http://34.223.219.142:1212/ords/hr/countries"
	// Then response status should be 200
	// Then I should see json response
	// @Test
	public void printResponse() {

		when().get("http://34.223.219.142:1212/ords/hr/countries").andReturn().body().prettyPrint();

	}

	// When I send GET request url "http://34.223.219.142:1212/ords/hr/countries"
	// Then response status should be 200
	// Then I should see json response
	// @Test
	public void getWithHeaders() {

		with().accept(ContentType.JSON).when().get("http://34.223.219.142:1212/ords/hr/countries").then()
				.statusCode(200);

	}

	// Negative scenario
	// @Test
	public void negativeGet() {

		// with().accept(ContentType.JSON).when().get("http://34.223.219.142:1212/ords/hr/employees/1234").then()
		// .statusCode(404);

		Response response = when().get("http://34.223.219.142:1212/ords/hr/employees/1234");
		assertEquals(response.statusCode(), 404);
		assertTrue(response.asString().contains("Not Found"));
		response.prettyPrint();

	}
	
	/* when I send a GET request to REST URL:
	 * http://34.223.219.142:1212/ords/hr/employees/100
	 * and accept type is JSON
	 * Then status code is 200
	 * and response content should be json
	 * 
	 * WITH WHEN GET ASSERT THAT ACCEPT CONTENTTYPE
	 */

	// @Test
	public void verifyContentTypeWithAssertThat() {

		String url = "http://34.223.219.142:1212/ords/hr/employees/100";
		given().accept(ContentType.JSON)
	    .when().get(url)
	    .then().assertThat().statusCode(200)
	    .and().contentType(ContentType.JSON);

	}

	/* Given accept type is JSON 
	 * When I send a Get request to rest URL
	 * http://34.223.219.142:1212/ords/hr/employees/100
	 * Then status code is 200
	 * And Response content should be JSON 
	 * And first name should be "Steven" and employee_id is 100
	 * 
	 */
	@Test
	public void verifyFirstName() throws URISyntaxException {
		URI uri = new URI("http://34.223.219.142:1212/ords/hr/employees/100");

		given().accept(ContentType.JSON).when()
		.get(uri).then().assertThat()
		.statusCode(200).and()
		.contentType(ContentType.JSON).and()
		.assertThat()
		.body("first_name", Matchers.equalTo("Steven")).and()
		.assertThat().body("employee_id", Matchers.equalTo(100));
	}

}
