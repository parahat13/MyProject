package com.app.tests;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.app.utilities.ConfigurationReader;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;



public class JsonValidationOfRegions {
	
	/*
	 * Warm up task: Given Content type is Json And Limit is 10 When I send request
	 * to Rest API url: http://34.223.219.142:1212/ords/hr/regions Then status code
	 * is 200 
	 * Then I should see following data: 1 Europe 2 Americas 3 Asia 4 Middle
	 * East and Africa
	 */
	
	//@Test
	public void testRegions() {
		String url = ConfigurationReader.getProperty("hrapp.baseresturl") + "/regions";

		Response response = given().accept(ContentType.JSON).and().params("limit", 10).when().get(url);

		assertEquals(response.statusCode(), 200);

		JsonPath json = response.jsonPath();

		assertEquals(json.getInt("items[0].region_id"), 1);
		assertEquals(json.getString("items[0].region_name"), "Europe");

		assertEquals(json.getInt("items[1].region_id"), 2);
		assertEquals(json.getString("items[1].region_name"), "Americas");

		assertEquals(json.getInt("items[2].region_id"), 3);
		assertEquals(json.getString("items[2].region_name"), "Asia");

		assertEquals(json.getInt("items[3].region_id"), 4);
		assertEquals(json.getString("items[3].region_name"), "Middle East and Africa");

	}
	
	@Test
	public void testRegionsV2() {
		
		String url = ConfigurationReader.getProperty("hrapp.baseresturl") + "/regions";
		Response response = given().accept(ContentType.JSON).and().params("limit", 10).when().get(url);
		assertEquals(response.statusCode(), 200);
		JsonPath json = response.jsonPath();

		// deserialize json to List<Map>
		List<Map> regions = json.getList("items", Map.class);

		Map<Integer, String> expectedRegions = new HashMap<>();
		expectedRegions.put(1, "Europe");
		expectedRegions.put(2, "Americas");
		expectedRegions.put(3, "Asia");
		expectedRegions.put(4, "Middle East and Africa");

		for (Integer regionId : expectedRegions.keySet()) {
			System.out.println("Looking for region :" + regionId);

			for (Map map : regions) {
				if (map.get("region_id") == regionId) {
					assertEquals(map.get("region_name"), expectedRegions.get(regionId));
				}
			}

		}

	}


	

}
