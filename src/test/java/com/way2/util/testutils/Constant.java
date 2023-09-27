package com.way2.util.testutils;

public class Constant {
	
	public static final String CONTENT_TYPE = "application/json";
	public static final String GRANT_TYPE = "client_credentials";
	public static final String OUTH2_URL = "https://ssaasi.delta.com/as/token.oauth2";
	public static final String RESPONSE_CODE = "responseCode";
	public static final String STATUS_CODE = "StatusCode";
	public static final String EXCEL_FILE_PATH = "excelFilePath";
	public static final String DATA_MAP = "dataMap";
	public static final String TestScenario = "TestScenario";
	public static final String Statuscode = "Statuscode";
	public static final String UUID = "UUID";
	public static final String RANDOM_NUMBER = "Random Number";
	public static final int maxSaveDefaultRecords = 3;
	public static final long RANDOM_NO_MIN = 4000000000L;
	public static final long RANDOM_NO_MAX = 4999999999L;
	public static final String STATUS_DESC = "statusDescription";
	public static final String OS_WIN = "Windows";
	public static final String OS_LIN = "Linux";
	public static final String CLIENT_ID = "FOMAWBBWPP_BelowWingPayloadPlanningandCloseout_CC_SI";
	public static final String CLIENT_SECRET = "39JsJzHuqBdwVgefKcZv57QGQbPHPCTIbfQR9wfEPeYm0U1PvY2NgIC4CQlgZiQX";
	public static final String SECRET_NAME = "payload-planning/client-credentials";
	public static final String DELETE_MSG = "User deleted!";

	// STATUS CODES
	public static final int STATUS_CODE_200 = 200;
	public static final int STATUS_CODE_400 = 400;
	public static final int STATUS_CODE_403 = 403;
	public static final int STATUS_CODE_415 = 415;
	public static final int STATUS_CODE_404 = 404;

	//Dynamo DB Constants
	public static final String PARTITION_KEY = "PK";
	public static final String SORT_KEY = "SK";
	public static final String TABLE_NAME = "PAYLOAD_PLANNING_GLOBAL";

	public static final String REGION = getRegion();

	private static String getRegion() {
		String regionName = System.getProperty("region");
		if (regionName == null) {
			regionName = Constant.DEFAULTREGION.toLowerCase();
		}
		return regionName;
	}

	//Default values
	public static final String SAVEPLAN = "saveplan";

	// Default Env
	public static final String DEFAULTENV = "development";
	public static final String SIENV = "systems-integration";

	// region
	public static final String DEFAULTREGION = "us-east-1";
	public static final String SECONDREGION = "us-west-2";
	
	// Test data from Way2 Automation Excel 

	public static final String WAY2AUTOMATION_EXCEL_PATH = "way2AutomationExcel";
	public static final String WAY2AUTOMATION_SHEET_NAME = "way2AutomationSheet";
	
	//**********************************Json Paths ******************************************
	
	public static final String ERROR_JSONPATH = "error";
	public static final String ID_JSONPATH = "id";
	
}
