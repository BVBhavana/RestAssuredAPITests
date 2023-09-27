package com.way2.validations;

import static net.serenitybdd.rest.SerenityRest.then;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Way2Validations {
	
	private static final Logger log = LogManager.getLogger(Way2Validations.class);

	public void validatestatuscode(int code) {
		then().assertThat().statusCode(code);
	}

	public void validatestatusmsg(String expectedMessage) {

		then().assertThat().statusLine(containsString(expectedMessage));
	}

	public void validatefieldinResponsebody(String expectedField, String expectedValue) {
		then().assertThat().body(expectedField, equalTo(expectedValue));

	}

	public void validatefieldinCallBackResponsebody(String expectedField, String expectedValue) {
		then().assertThat().body(expectedField, hasItem(expectedValue));

	}
	
	public void validateListFieldinResponsebody(String expectedField, String expectedValue) {
		then().assertThat().body(expectedField, hasItem(expectedValue));
	}
	
	public void validateResponsebody(String expectedValue) {
		then().assertThat().body(equalTo(expectedValue));

	}

	public void validatefieldisNotNull(String expectedField) {
		then().assertThat().body(expectedField, not(isEmptyOrNullString()));

	}

	
	public void validateIntfieldinResponsebody(String expectedField, int expectedValue) {
		then().assertThat().body(expectedField, equalTo(expectedValue));

	}

	public void validateContainsfieldinResponsebody(String expectedField, String expectedValue) {
		then().assertThat().body(expectedField, containsString(expectedValue));

	}


	public void validateJsonSchema(String jsonSchemaFilePathName) {
		then().body(JsonSchemaValidator.matchesJsonSchema(new File(jsonSchemaFilePathName)));		

	}
	
	public String getStringValueFromResponse(String jsonPath) {
		return then().extract().response().path(jsonPath);

	}
	
	public int getSizeofListinJsonResponse(String jsonPath) {
		return then().extract().response().jsonPath().getList(jsonPath).size();
	}
	
	public void validateALLFieldsMatch(String jsonPath, String value) {
		
		List<String> list;
		Response response = then().extract().response();
		JsonPath jsonPathEvaluator = response.jsonPath();
		list = jsonPathEvaluator.get(jsonPath);		
		log.info(list);		
		Assert.assertTrue(list.stream().allMatch(s -> s.equals(value)));
		log.info("All remarkType field has {} value \r\n",value);	
	}
	
	public void validateAnyValuesMatch(String jsonPath, List<String> list1) {
		
		List<String> list2;
		Response response = then().extract().response();
		JsonPath jsonPathEvaluator = response.jsonPath();
		list2 = jsonPathEvaluator.get(jsonPath);
				
		log.info(list2);		
		Assert.assertTrue(list2.stream().anyMatch(list1::contains));
		log.info("All aircraftType field has values among {} \r\n",list1);	
	}
}
