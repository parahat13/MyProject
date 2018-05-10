package com.app.tests;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.poi.ss.formula.functions.Sumproduct;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.app.beans.Country;
import com.app.beans.CountryResponse;
import com.app.beans.Region;
import com.app.beans.RegionResponse;
import com.app.utilities.ConfigurationReader;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class ApiDay4POSTRequests {
	
	/*
	POST SCENARIO:
		given content type is Json
		And Accept type is Json
		When I send POST request to 
		http://34.223.219.142:1212/ords/hr/regions
		with request body :
		{
			"region_id" : 5,
			"region_name" : "murodil's region"
		}
		Then status code should be 201
		And response body should match request body
	*/
	
	//@Test
	public  void postNewRegion() {
		
		String url = ConfigurationReader.getProperty("hrapp.baseresturl") + "/regions/";
		
		//String requestJson = "{\"region_id\" : 123,\"region_name\" : \"Chicago region\"}";
		
		Map requestMap= new HashMap<>();
		requestMap.put("region_id", 78954);
		requestMap.put("region_name", "Chicago city");
		
		
		Response response= given().accept(ContentType.JSON)
						   .and().contentType(ContentType.JSON)
						   .and().body(requestMap)
						   .when().post(url);
		
		System.out.println(response.statusLine());
		response.prettyPrint();
		
		//Then status code should be 201
		//And response body should match request body
		assertEquals(response.statusCode(),201);
		Map responseMap = response.body().as(Map.class);
				
		//assertEquals(responseMap,requestMap); did not work
		assertEquals(responseMap.get("region_id"),requestMap.get("region_id"));
		assertEquals(responseMap.get("region_name"),requestMap.get("region_name"));

	}
	
	//@Test
	public void postUsingPOJO() {
		String url = ConfigurationReader.getProperty("hrapp.baseresturl") + "/regions/";
		
		Region region = new Region();
		region.setRegion_id(new Random().nextInt(999999));
		region.setRegion_name("murodil's region");
		
		Response response = given().log().all()
							.accept(ContentType.JSON)
						   .and().contentType(ContentType.JSON)
						   .and().body(region)
						   .when().post(url);
	
		assertEquals(response.statusCode(),201);
		
		RegionResponse responseRegion = response.body().as(RegionResponse.class);
		
		//And response body should match request body
		//region id and region name must match
		assertEquals(responseRegion.getRegion_id(),region.getRegion_id());
		assertEquals(responseRegion.getRegion_name(),region.getRegion_name());
		
	}
	
	@Test
	public void postCountryUsingPojo() {
		String url = ConfigurationReader.getProperty("hrapp.baseresturl") + "/countries/";
		
		Country reqCountry = new Country();
		reqCountry.setCountry_id("AB");
		reqCountry.setCountry_name("A country");
		reqCountry.setRegion_id(4);
		
		Response response = given().log().all()
				.accept(ContentType.JSON)
			   .and().contentType(ContentType.JSON)
			   .and().body(reqCountry)
			   .when().post(url);
		
		assertEquals(response.getStatusCode(),201);
		
		CountryResponse resCountry = response.body().as(CountryResponse.class);
		
		assertEquals(resCountry.getCountry_id(),reqCountry.getCountry_id()); 
		assertEquals(resCountry.getCountry_name(),reqCountry.getCountry_name()); 
		assertEquals(resCountry.getRegion_id(),reqCountry.getRegion_id());

	}
	

}
