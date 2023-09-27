package com.way2.automation.steps;
import static net.serenitybdd.rest.SerenityRest.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.way2.util.testutils.Utils;
import com.way2.setup.SetupHeaders;
import com.way2.util.testutils.Constant;
import com.way2.util.testutils.ExcelTestData;
import com.way2.util.testutils.JsonUpdateUtil;
import com.way2.util.testutils.PropLoader;
import com.way2.validations.Way2Validations;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

public class Way2AutomationSteps {
	
	private static final Logger log = LogManager.getLogger(Way2AutomationSteps.class);
	private String getAllUsersJsonTemplatePath = "src/test/resources/jsonSchema/way2Automation/getAllUsersJsonSchema.json";
	private String getSingleUserJsonTemplatePath = "src/test/resources/jsonSchema/way2Automation/getSingleUserJsonSchema.json";
	private String createUserJsonPayload = "src/test/resources/jsonPayloads/Way2Automation/CreateUser.json";
	private String updateUserJsonPayload = "src/test/resources/jsonPayloads/Way2Automation/UpdateUser.json";
	Map<String, Map<String, String>> dataMap;

	public static String access_token;
	private String pathParam;
	private String payload;
	private int id;
	
//	static {
//		try {
//
//			access_token = GetServiceToken.getToken();
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	@Steps
	private Way2Validations validation = new Way2Validations();
	
	@Given("^The EndPoint is setup for wayToAutomation service$")
	public void endPointSetupForWayToAutomationService() throws Throwable{
		log.info("Setup endpoint for Way2Automation service");
		setupHeader(Constant.WAY2AUTOMATION_EXCEL_PATH, Constant.WAY2AUTOMATION_SHEET_NAME);
		SetupHeaders.initialSetupWay2RestAssured(PropLoader.props.apply("way2AutomationHostname"));
	}


	@When("^I hit the GET request for the scenario \"([^\"]*)\" for getAllUsers service$")
	public void getRequestForGetAllUsersService(String scenario) throws Throwable{
		Utils.startTestCase(scenario);
		log.info("Get request for getAllUsers service");

		pathParam = dataMap.get(scenario).get("PathParam");
		rest().given().urlEncodingEnabled(false)
				.header("Content-Type",Constant.CONTENT_TYPE).log().all().get(pathParam);
	}
	
	@When("^I hit the POST request for the scenario \"([^\"]*)\" for createUser service$")
	public void postRequestForCreateUserService(String scenario) throws Throwable{
		Utils.startTestCase(scenario);
		log.info("Create new user for postCreateUser service");
		payload = getRequestPayLoad(scenario,createUserJsonPayload,"createUserJsonPayload");
		pathParam = dataMap.get(scenario).get("PathParam");

		rest().given().urlEncodingEnabled(false).header("Content-Type",Constant.CONTENT_TYPE)
				.log().all().body(payload).post(pathParam);
	}
	
	@When("^I hit the GET request for the scenario \"([^\"]*)\" for getSingleUser service$")
	public void getRequestForGetSingleUserService(String scenario) throws Throwable{
		Utils.startTestCase(scenario);
		log.info("Get request for getSingleUser service");

		pathParam = dataMap.get(scenario).get("PathParam");
		String[] params = pathParam.split("/");
		id = Integer.parseInt(params[3]);
		rest().given().urlEncodingEnabled(false)
				.header("Content-Type",Constant.CONTENT_TYPE).log().all().get(pathParam);	    
	}
	
	@When("^I hit the PUT request for the scenario \"([^\"]*)\" for updateUser service$")
	public void putRequestForUpdateUserService(String scenario) throws Throwable{
		Utils.startTestCase(scenario);
		log.info("Update user for updateUser service");
		payload = getRequestPayLoad(scenario,updateUserJsonPayload,"updateUserJsonPayload");
		pathParam = dataMap.get(scenario).get("PathParam");

		rest().given().urlEncodingEnabled(false).header("Content-Type",Constant.CONTENT_TYPE)
				.log().all().body(payload).put(pathParam);
	}
	
	@When("^I hit the DELETE request for the scenario \"([^\"]*)\" for deleteUser service$")
	public void deleteRequestForDeleteUserService(String scenario) throws Throwable {
		Utils.startTestCase(scenario);
		log.info("Delete request for deleteUser service");
		pathParam = dataMap.get(scenario).get("PathParam");
		rest().given().urlEncodingEnabled(false)
				.header("Content-Type",Constant.CONTENT_TYPE).log().all().delete(pathParam);
	}

	@Then("^I validate Event Status Response Code \"([^\"]*)\", Response Message \"([^\"]*)\" for the Scenario Type \"([^\"]*)\" and Scenario \"([^\"]*)\" for getAllUsers service$")
	public void validateEventStatusResponseCodeResponseMessageForgetAllUsersService(String responseCode, String responseMessage, String scenarioType, String scenario) throws Throwable{
		log.info("******Validate Response Body ******::");
		log.info("scenario : {}", scenario);
		validation.validatestatuscode(Integer.parseInt(responseCode));
		log.info("Response Code : {}", Integer.parseInt(responseCode));
//		validation.validatestatusmsg(responseMessage);
		log.info("Response Message : {}", responseMessage);

		if (scenarioType.equalsIgnoreCase("valid") && scenario.contains("Get")) {

			validation.validateJsonSchema(getAllUsersJsonTemplatePath);
			log.info("Validated JSON Schema of Response body ");
		}
		
		else if (Integer.parseInt(responseCode) == Constant.STATUS_CODE_404) {
			String errorJsonPath = Constant.ERROR_JSONPATH;
			validation.validatefieldinResponsebody(errorJsonPath, responseMessage);
			log.info("error : {}", responseMessage);
		}
	}
	
	@Then("^I validate Event Status Response Code \"([^\"]*)\", Response Message \"([^\"]*)\" for the Scenario Type \"([^\"]*)\" and Scenario \"([^\"]*)\" for createUser service$")
	public void validateEventStatusResponseCodeResponseMessageForcreateUserService(String responseCode, String responseMessage, String scenarioType, String scenario) throws Throwable{
		log.info("******Validate Response Body ******::");
		log.info("scenario : {}", scenario);
		validation.validatestatuscode(Integer.parseInt(responseCode));
		log.info("Response Code : {}", Integer.parseInt(responseCode));
//		validation.validatestatusmsg(responseMessage);
		log.info("Response Message : {}", responseMessage);
		

		if (Integer.parseInt(responseCode) == Constant.STATUS_CODE_404) {
			String errorJsonPath = Constant.ERROR_JSONPATH;
			validation.validatefieldinResponsebody(errorJsonPath, responseMessage);
			log.info("error : {}", responseMessage);
		}

	}
	
	@Then("^I validate Event Status Response Code \"([^\"]*)\", Response Message \"([^\"]*)\" for the Scenario Type \"([^\"]*)\" and Scenario \"([^\"]*)\" for getSingleUser service$")
	public void validateEventStatusResponseCodeResponseMessageForGetSingleUserService(String responseCode, String responseMessage, String scenarioType, String scenario) throws Throwable{
		log.info("******Validate Response Body ******::");
		log.info("scenario : {}", scenario);
		validation.validatestatuscode(Integer.parseInt(responseCode));
		log.info("Response Code : {}", Integer.parseInt(responseCode));
//		validation.validatestatusmsg(responseMessage);
		log.info("Response Message : {}", responseMessage);

		if (scenarioType.equalsIgnoreCase("valid") && scenario.contains("Get")) {
			String idJsonPath = Constant.ID_JSONPATH;
			validation.validateIntfieldinResponsebody(idJsonPath, id);
			log.info("id : {}", id);
			validation.validateJsonSchema(getSingleUserJsonTemplatePath);
			log.info("Validated JSON Schema of Response body ");
		}		
	}
	
	@Then("^I validate Event Status Response Code \"([^\"]*)\", Response Message \"([^\"]*)\" for the Scenario Type \"([^\"]*)\" and Scenario \"([^\"]*)\" for updateUser service$")
	public void validateEventStatusResponseCodeResponseMessageForUpdateUserService(String responseCode, String responseMessage, String scenarioType, String scenario) throws Throwable{
		log.info("******Validate Response Body ******::");
		log.info("scenario : {}", scenario);
		validation.validatestatuscode(Integer.parseInt(responseCode));
		log.info("Response Code : {}", Integer.parseInt(responseCode));
//		validation.validatestatusmsg(responseMessage);
		log.info("Response Message : {}", responseMessage);
		
		if (Integer.parseInt(responseCode) == Constant.STATUS_CODE_404) {
			String errorJsonPath = Constant.ERROR_JSONPATH;
			validation.validatefieldinResponsebody(errorJsonPath, responseMessage);
			log.info("error : {}", responseMessage);
		}
	}
	
	@Then("^I validate Event Status Response Code \"([^\"]*)\", Response Message \"([^\"]*)\" for the Scenario Type \"([^\"]*)\" and Scenario \"([^\"]*)\" for deleteUser service$")
	public void validateEventStatusResponseCodeResponseMessageForDeleteUserService(String responseCode, String responseMessage, String scenarioType, String scenario) throws Throwable{
		log.info("******Validate Response Body ******::");
		log.info("scenario : {}", scenario);
		validation.validatestatuscode(Integer.parseInt(responseCode));
		log.info("Response Code : {}", Integer.parseInt(responseCode));
//		validation.validatestatusmsg(responseMessage);
		log.info("Response Message : {}", responseMessage);
		
		if (scenarioType.equalsIgnoreCase("valid") && scenario.contains("Delete")) {
			validation.validateResponsebody(Constant.DELETE_MSG);
			log.info("message : {}", Constant.DELETE_MSG);
		}
		
		else if (Integer.parseInt(responseCode) == Constant.STATUS_CODE_404) {
			String errorJsonPath = Constant.ERROR_JSONPATH;
			validation.validatefieldinResponsebody(errorJsonPath, responseMessage);
			log.info("error : {}", responseMessage);
		}
	}
	
	private void setupHeader(String excelPath, String sheetName) throws Exception {
		dataMap = ExcelTestData.getTestDataBySheet(PropLoader.props.apply(excelPath),
				PropLoader.props.apply(sheetName));
		Serenity.getCurrentSession().put(Constant.DATA_MAP, dataMap);
	}
	
	private String getRequestPayLoad(String scenario, String jsonPayloadPath, String serviceName) throws IOException {
		Map<String, Object> scenarioDataMap = new HashMap<>(dataMap.get(scenario));
		String jsonTemplatePayload = new String(Files.readAllBytes(Paths.get(jsonPayloadPath)));
		payload = JsonUpdateUtil.buildJsonRequest(jsonTemplatePayload, scenarioDataMap, serviceName);
		return payload;
	}

}
