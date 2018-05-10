package com.app.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
 
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.annotations.Test;
import com.app.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class APiDay3_JsonPath {
	
	/*	Given Accept type is json 
	 * when I send a GET request to REST URL:
	 * http://34.223.219.142:1212/ords/hr/employees
	 * Then status code should be 200
	 * and response content should be JSON
	 * and  4 regions should be returned
	 * and Americas is one of the regions names
	 */
	
//	@Test
	public void testItemsCountFromResponseBody() {
		 given().accept(ContentType.JSON)
		.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/regions")
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType(ContentType.JSON)
		.and().assertThat().body("items.region_id", hasSize(4))
		.and().assertThat().body("items.region_name", hasItem("Americas"))
		.and().assertThat().body("items.region_name", hasItems("Americas", "Europe", "Asia", "Middle East and Africa"));
		
	}
	
	
	/* Given Accept type is JSON
	 * And Params are limit 100
	 * when I send request to
	 * http://34.223.219.142:1212/ords/hr/employees
	 * Then status  code is 200
	 * and response content should be JSON
	 * and 100 employees data should be  
	 * 
	 */

	//@Test 
	public void testWithQueryParameterAndList() {
		given().accept(ContentType.JSON)
		.and().params("limit", 100)
		.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees")
		.then().statusCode(200)
		.and().contentType(ContentType.JSON)
		.and().assertThat().body("items.employee_id", hasSize(100));
	
	}
	
	/* Given Accept type is JSON
	 * And Params are limit 100
	 * and path params is 110
	 * when I send request to
	 * http://34.223.219.142:1212/ords/hr/employees
	 * Then status  code is 200
	 * and response content should be JSON
	 * and following data should be returned
	 * "employee_id": 110,
	 *  "first_name"
	 */
	//@Test
	public void testWithPathParameter() {
		
		given().accept(ContentType.JSON)
		.and().params("limit", 100)
		.and().pathParams("employee_id", 110)
		.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees/{employee_id}")
		.then().statusCode(200)
		.and().contentType(ContentType.JSON)
		.and().assertThat().body("employee_id", equalTo(110),
								  "first_name",	equalTo("John"),
								  "last_name",	equalTo("Chen"),
								  "email",   equalTo("JCHEN"));
		
		}
	
	/* Given Accept type is JSON
	 * And Params are limit 100
	 * and path params is 110
	 * when I send request to
	 * http://34.223.219.142:1212/ords/hr/employees
	 * Then status  code is 200
	 * and response content should be JSON
	 * and following data should be returned
	 * all employees ids should be returned
	 */
	
	//@Test
	public void testWithJsonPath() {
		
		Map<String,Integer> rParamMap= new  HashMap<>();
		rParamMap.put("limit", 100);
	
		Response response = given().accept(ContentType.JSON)  // header
							.and().params(rParamMap)           // query param  
							.and().pathParams("employee_id", 177)  // path  param
							.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees/{employee_id}");
							
		JsonPath json = response.jsonPath();  // get  json body and assign to jsonPath object
		
		System.out.println(json.getInt("employee_id"));
		System.out.println(json.getString("last_name"));
		System.out.println(json.getString("job_id"));
		System.out.println(json.getInt("salary"));
		System.out.println(json.getString("links[0].href" ));	
		
		// Assign all hrefs into  a list of Strings
		
		List<String> hrefs=json.getList("links.href");
		System.out.println(hrefs);
	
	}
	
	/* Given Accept type is JSON
	 * And Params are limit 100
	 * and path params is 110
	 * when I send request to
	 * http://34.223.219.142:1212/ords/hr/employees
	 * Then status  code is 200
	 * and response content should be JSON
	 * all employees data should be returned
	 */
	
	@Test
	public void testJsonPathWithLists() {
		Map<String,Integer> rParamMap = new HashMap<>();
		rParamMap.put("limit", 100);
		
		Response response = given().accept(ContentType.JSON)//header
							.and().params(rParamMap) //query param/request param
							.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees");
		
		assertEquals(response.statusCode(),200); //check status code
		
		JsonPath json = response.jsonPath();
		//JsonPath json = new JsonPath(new File(FilePath.json));
		//JsonPath json = new JsonPath(response.asString());
		
		//get all employee ids into an arraylist
		List<Integer> empIds = json.getList("items.employee_id");
		System.out.println(empIds);
		//assert that there are 100 emp ids
		
		assertEquals(empIds.size(),100);
		
		//get all emails and assign into arraylist
		List<String> emails = json.getList("items.email");
		System.out.println(emails);
		
		assertEquals(emails.size(),100);
		
		//get all employee ids that are greater than 150
		List<String> empIdList = json.getList("items.findAll{it.employee_id > 150}.employee_id");
		System.out.println(empIdList);
		
		//get all employee lastnames, whose salary is more than 7000
		List<String> lastnames = json.getList("items.findAll{it.salary>7000}.last_name");
		System.out.println(lastnames);
		
		JsonPath jsonFromFile = new JsonPath(new File("/Users/cybertekschool/Desktop/employees.json"));
		List<String> emails2 = jsonFromFile.getList("items.email");
    	System.out.println(emails2);
		
		
	}
}
