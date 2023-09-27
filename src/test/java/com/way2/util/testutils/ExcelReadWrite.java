package com.way2.util.testutils;

import java.io.FileInputStream;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelReadWrite {
	
	static int rowNumber;
	static String path = (System.getProperty("user.dir") + "/src/test/resources/testdata/Member_MedCompass_Inputsheet.xlsx");
	static XSSFWorkbook myExcelBook = null;
	//private static final Logger log = LogManager.getLogger(ServiceAuthCallBackResponseSteps.class);

	private ExcelReadWrite() {
		throw new IllegalStateException("Utility class");
	}

	private static int findColNumber(String columnName, String sheetName) throws IOException {
		myExcelBook = new XSSFWorkbook(new FileInputStream(path));
		XSSFSheet sheet = myExcelBook.getSheet(sheetName);
		XSSFRow row = sheet.getRow(0);
		List<String> cellValues = new ArrayList<>();
		String cellValue = "";
		row.forEach(r -> cellValues.add(r.getStringCellValue()));
		Optional<String> value = cellValues.stream().filter(e -> e.equals(columnName)).findFirst();
		if (value.isPresent()) {
			cellValue = value.get();
		}
		return cellValues.indexOf(cellValue);
	}

	private static int findRow(String mapValue, String sheetName) throws IOException {
		myExcelBook = new XSSFWorkbook(new FileInputStream(path));
		XSSFSheet sheet = myExcelBook.getSheet(sheetName);
		List<String> rows = new ArrayList<>();
		sheet.forEach(sh -> {
			rows.clear();
			sh.forEach(r -> rows.add(r.getStringCellValue()));
			if (rows.stream().anyMatch(e -> e.equals(mapValue))) {
				rowNumber = sh.getRowNum();
			}
		});
		return rowNumber;
	}

	public static String fetchXLDataNStoreInHashMap(String sheetName, String mapValue, String columnName)
			throws IOException {
		int colNumber = ExcelReadWrite.findColNumber(columnName, sheetName);
		int rowNumber = ExcelReadWrite.findRow(mapValue, sheetName);
		if (rowNumber == 0) {
			throw new NullPointerException();
		}
		myExcelBook = new XSSFWorkbook(new FileInputStream(path));
		XSSFSheet sheet = myExcelBook.getSheet(sheetName);
		XSSFRow row = sheet.getRow(rowNumber);
		XSSFCell cell = row.getCell(colNumber);
		return cell.getStringCellValue();
		
		
	}


	
	//############################# Utility Methods#################################
	
	public static String getTestcaseType(String scenario) {
	   	
	      
        String[] arrSplit = scenario.split("_");
        for (int i=0; i < arrSplit.length; i++)
        {
          //log.info("TestScenario_sections" +arrSplit[i]);
      			         
        }
       // log.info("TestScenario" +arrSplit[2]);
     	
	return arrSplit[2];
}
	
	public static int getRndNumber() throws NoSuchAlgorithmException {
	   // Random random=new Random();
		Random random = SecureRandom.getInstanceStrong();
	    int randomNumber=0;
	    boolean loop=true;
	    while(loop) {
	        randomNumber=random.nextInt();
	        if(Integer.toString(randomNumber).length()==10 && !Integer.toString(randomNumber).startsWith("-")) {
	            loop=false;
	        }
	        }
	    return randomNumber;

}
	
	public static String getRandomGenerate(int len) {
		return UUID.randomUUID().toString().replaceAll("[-+.^:,-]", "").substring(0, len).toUpperCase();
		}

}
