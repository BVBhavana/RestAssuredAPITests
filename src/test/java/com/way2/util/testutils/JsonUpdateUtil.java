package com.way2.util.testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class JsonUpdateUtil {
	
	private static final Logger log = LogManager.getLogger(JsonUpdateUtil.class);

	Random random = new Random();

	public JsonUpdateUtil() {
	}

	public static String buildJsonRequest(String payload, Map<String, Object> map, String app) throws IOException {

		DocumentContext doc = JsonPath.parse(payload);
		String newPayload = payload;

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			try {
			//	log.info("entry key value"+entry.getKey()+"entry value :"+entry.getValue());
				if (entry.getValue() != null && entry.getValue().equals(Constant.UUID)) {
					doc = JsonPath.parse(newPayload).set(entry.getKey(), UUID.randomUUID().toString());
				} else if (entry.getValue() != null && entry.getValue().equals(Constant.RANDOM_NUMBER)) {
					doc = JsonPath.parse(newPayload).set(entry.getKey(),
							getRandomNumber(Constant.RANDOM_NO_MIN, Constant.RANDOM_NO_MAX));
				} else if (entry.getKey() != null && entry.getValue().equals("null")) {
					doc = JsonPath.parse(newPayload).set(entry.getKey(), null);
				} else if (entry.getKey() != null && entry.getValue().equals("EmptyString")) {
					doc = JsonPath.parse(newPayload).set(entry.getKey(), "");
				} else if (entry.getKey() != null && entry.getValue().equals("NewArray")) {
					String[] arr = {"test"};
					doc = JsonPath.parse(newPayload).set(entry.getKey(), arr);
				} else if (entry.getKey() != null && entry.getValue().equals("")) {
					
				} else if (entry.getKey() != null && entry.getValue().equals("EmptyArray")) {
					String[] arr = new String[]{} ;
					doc = JsonPath.parse(newPayload).set(entry.getKey(), arr);
				} else if (entry.getKey() != null && entry.getValue().equals("EmptyList")) {
					List<String> list = new ArrayList<>();
					doc = JsonPath.parse(newPayload).set(entry.getKey(), list);
				} else if (entry.getKey() != null && entry.getValue().equals("SplChar")) {
					String splChar = "@*$";
					doc = JsonPath.parse(newPayload).set(entry.getKey(), splChar);
				}else if(entry.getValue().toString().contains("RemoveField")) {
					
					doc = JsonPath.parse(newPayload).delete(entry.getKey());
				}else if(entry.getValue().toString().contains("TestNum")) {
					String num = "123";
					doc = JsonPath.parse(newPayload).set(entry.getKey(), num);
				}else {
					if (checkIfStringIsInteger(entry.getValue().toString()) == true) {
						Integer iVal = Integer.parseInt(entry.getValue().toString());
						doc = JsonPath.parse(newPayload).set(entry.getKey(), iVal);
					}
					else if (checkIfStringIsDouble(entry.getValue().toString()) == true) {
						Double iVal = Double.parseDouble(entry.getValue().toString());
						doc = JsonPath.parse(newPayload).set(entry.getKey(), iVal);
					}
					else {
						doc = JsonPath.parse(newPayload).set(entry.getKey(), entry.getValue());
					}

				}
				newPayload = doc.jsonString();
			} catch (Exception e) {
				 log.error(e);
			}
		}

		return doc.jsonString();
	}

	public static Boolean checkIfStringIsInteger(String val) {
		try {
			Integer.parseInt(val);
			// int iVal = (int)val;
			return true;
		} catch (NumberFormatException ex) {

		}
		return false;
	}
	public static Boolean checkIfStringIsDouble(String val) {
		try {
			Double.parseDouble(val);
			// int iVal = (int)val;
			return true;
		} catch (NumberFormatException ex) {

		}
		return false;
	}

//	private static void deleteSaveDefaultRecords(DocumentContext doc, String noOfRecords) {
//		int iNoOfRecords = new Double(Double.parseDouble(noOfRecords)).intValue();
//		int noOfRecordsToBeDeleted = Constant.maxSaveDefaultRecords - iNoOfRecords;
//		if (iNoOfRecords < Constant.maxSaveDefaultRecords && iNoOfRecords >= 1) {
//			for (int i = noOfRecordsToBeDeleted; i >= 1; i--) {
//				String saveDefaultJsonPath = "containerSheet[" + i + "]";
//				doc.delete(saveDefaultJsonPath);
//			}
//		}
//	}

	public static String buildDHOJsonRequest(String payload, Map<Object, Object> map) throws IOException {

		DocumentContext doc = JsonPath.parse(payload);
		String key = "";
		String value = "";
		String newPayload = payload;
		for (Map.Entry<Object, Object> entry : map.entrySet()) {
			try {
				if (entry.getKey() != null && entry.getKey().equals("InputField")) {
					if (entry.getValue() != null) {
						key = entry.getValue().toString();
					}
				} else if (entry.getKey() != null && entry.getKey().equals("InputValue")) {
					if (entry.getValue() != null) {
						value = entry.getValue().toString();
					}
				}
				if (!key.isEmpty() && !value.isEmpty()) {
					doc = JsonPath.parse(newPayload).set(key, value);
					key = "";
					value = "";
					newPayload = doc.jsonString();
				}

			} catch (Exception e) {
				log.info(e, e);
			}
		}
		newPayload = doc.jsonString();
		return newPayload;
	}

	public static JSONObject updateJson(JSONObject obj, String keyString, String newValue) throws Exception {

		Iterator<String> iterator = obj.keys();
		String key = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			// if the key is a string, then update the value
			if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
				if (key.equals(keyString)) {
					obj.put(key, newValue);
					return obj;
				}

			}
			// if it's jsonobject
			if (obj.optJSONObject(key) != null) {
				updateJson(obj.getJSONObject(key), keyString, newValue);
			}
			// if it's jsonarray
			if (obj.optJSONArray(key) != null) {
				JSONArray jArray = obj.getJSONArray(key);
				for (int i = 0; i < jArray.length(); i++) {
					updateJson(jArray.getJSONObject(i), keyString, newValue);
				}
			}
		}
		return obj;
	}

	public static JSONObject updateAllJsonField(JSONObject obj, String keyString, String newValue) throws Exception {

		Iterator<String> iterator = obj.keys();
		String key = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			// if the key is a string, then update the value
			if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
				if (key.equals(keyString)) {
					obj.put(key, newValue);
				}
			}
			// if it's jsonobject
			if (obj.optJSONObject(key) != null) {
				updateAllJsonField(obj.getJSONObject(key), keyString, newValue);
			}
			// if it's jsonarray
			if (obj.optJSONArray(key) != null) {
				JSONArray jArray = obj.getJSONArray(key);
				for (int i = 0; i < jArray.length(); i++) {
					updateAllJsonField(jArray.getJSONObject(i), keyString, newValue);
				}
			}
		}
		return obj;
	}

	public static JSONObject updateJsonFieldExcel(JSONObject obj, Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			try {
				JsonUpdateUtil.updateAllJsonField(obj, entry.getKey(), entry.getValue().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	public static JSONObject updateJsonArrayObjects(JSONObject obj, String keyString, String newValue)
			throws Exception {

		Iterator<String> iterator = obj.keys();
		String key = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			// if it's jsonobject

			if (obj.optJSONObject(key) != null && key.equals(keyString) && newValue == null) {
				obj.put(key, newValue);
				return obj;
			} else if (obj.optJSONObject(key) != null) {
				updateJsonArrayObjects(obj.getJSONObject(key), keyString, newValue);
			}

			// if it's jsonarray
			if (obj.optJSONArray(key) != null && key.equals(keyString) && newValue == null) {
				obj.put(key, newValue);
				return obj;
			} else if (obj.optJSONArray(key) != null) {
				JSONArray jArray = obj.getJSONArray(key);
				for (int i = 0; i < jArray.length(); i++) {
					updateJsonArrayObjects(jArray.getJSONObject(i), keyString, newValue);
				}
			}
		}
		return obj;
	}

	public static long getRandomNumber(long randomNoMin, long randomNoMax) throws NoSuchAlgorithmException {
		// Random random = new Random();
		Random random = SecureRandom.getInstanceStrong();
		return random.longs(randomNoMin, (randomNoMax + 1)).findFirst().getAsLong();
	}

	public static String readTestDataUsingColumnName(String ColumnName, String ExpectedTestcase, String fileName)
			throws IOException {

		File TestDataFile = new File(System.getProperty("user.dir") + fileName);
		FileInputStream finput1 = new FileInputStream(TestDataFile);
		Workbook wb1 = null;
		String fileExtensionName = fileName.substring(fileName.indexOf("."));

		if (fileExtensionName.equals(".xlsx")) {
			wb1 = new XSSFWorkbook(finput1);
		} else if (fileExtensionName.equals(".xls")) {
			wb1 = new HSSFWorkbook(finput1);
		} else {
         log.error("Only file extensions such as xls and xlsx are supported.");
		}
		Sheet sheet2 = wb1.getSheet("ContainerTypes");
		String KeyValue = null;
		Row row = sheet2.getRow(0);
		DataFormatter formatter = new DataFormatter();

		for (int j = 1; j <= sheet2.getLastRowNum(); j++) {
			String TestcaseName = sheet2.getRow(j).getCell(0).getStringCellValue();
			if (TestcaseName.equalsIgnoreCase(ExpectedTestcase)) {
				for (int i = 1; i <= row.getLastCellNum(); i++) {
					String FieldName = sheet2.getRow(0).getCell(i).getStringCellValue();
					if (FieldName.equalsIgnoreCase(ColumnName)) {
						Cell cell = sheet2.getRow(j).getCell(i);
						KeyValue = formatter.formatCellValue(cell);
						break;
					}
				}

				break;
			}

		}
		return KeyValue;
	}
}
