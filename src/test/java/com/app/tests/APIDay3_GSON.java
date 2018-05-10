package com.app.tests;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.app.utilities.ConfigurationReader;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class APIDay3_GSON {
	
	@Test
	public void testWithJsonHashMap() {
		
		Response response = given().accept(ContentType.JSON)
							.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/employees/120");
		
		Map<String, String> map= response.as(HashMap.class);
		
		System.out.println(map.keySet());
		System.out.println(map.values());
		
		assertEquals(map.get("employee_id"), 120);
		assertEquals(map.get("job_id"), "AC_MGR");
		
		
	}
	
	
	@Test
	public void convertJsonToListOfMaps() {
		Response response = given().accept(ContentType.JSON)
				.when().get(ConfigurationReader.getProperty("hrapp.baseresturl")+"/departments");
		
		//convert the response that contains department information into listof maps
		//List<Map<String,String>>
		
		//ArrayList<Map<String,String>> listOfMaps = response.as(ArrayList.class);
		List<Map> listOfMaps = response.jsonPath().getList("items",Map.class);
		
		System.out.println(listOfMaps.get(0));
			
		//assert that first department name is "Administration"
		
		assertEquals(listOfMaps.get(0).get("department_name"),"Administration");
	}

}
