package com.way2.util.testutils;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	
	private static final Logger log = LogManager.getLogger(ExcelReader.class);

    public static Map<String, Object> readAllRow(String sheetName, String scenario) throws IOException {
        Workbook workbook = null;
        Map<String, Object> dataMap = new HashMap<>();
        String filePath =
            (System.getProperty("user.dir") + "/src/test/resources/testdata/containersexcel.xlsx");
        log.info("filePath =" + filePath);
        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath));
            workbook = WorkbookFactory.create(inputStream);
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            Sheet sheet = null;
            while (sheetIterator.hasNext()) {
                sheet = sheetIterator.next();
                log.info("sheet name {} " + sheet.getSheetName());
                if (sheet.getSheetName().equalsIgnoreCase(sheetName)) {
                    break;
                }
            }
            dataMap = getDataRow(sheet, scenario);
            log.info("dataMap {}" + dataMap.size());
            inputStream.close();
            workbook.close();
        } catch (EncryptedDocumentException | IOException e) {
            log.info(e, e);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return dataMap;
    }

    private static Map<String, Object> getDataRow(Sheet sheet, String testScenario) {
        Map<String, Object> dataMap = new HashMap<>();
        if (sheet != null) {
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row headerRow = sheet.getRow(0);
                if (i == 0) {
                    continue;
                } else if (i > 0) {
                    Row dataRow = sheet.getRow(i);
                    if (getCellValue(dataRow.getCell(1)).contains(testScenario)) {
                        for (int j = 1; j < headerRow.getLastCellNum(); j++) {
                            dataMap.put(getCellValue(headerRow.getCell(j)), getCellValue(dataRow.getCell(j)));
                        }
                    } else {
                        continue;
                    }
                    return dataMap;

                }

            }
        }
        return dataMap;
    }

    @Deprecated
    public static Map<String, Map<String, String>> readAllRows(String excelPath, String sheetName) throws IOException {
        Workbook workbook = null;
        Map<String, Map<String, String>> dataMap = new HashMap<>();
        String filePath = (System.getProperty("user.dir") + excelPath);
        log.info("filePath =" + filePath);
        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath));
            workbook = WorkbookFactory.create(inputStream);
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            Sheet sheet = null;
            while (sheetIterator.hasNext()) {
                sheet = sheetIterator.next();
                log.info("sheet name {} " + sheet.getSheetName());
                if (sheet.getSheetName().equalsIgnoreCase(sheetName)) {
                    break;
                }
            }
            dataMap = getAllDataRow(sheet, sheetName);
            log.info("dataMap {}" + dataMap.size());
            inputStream.close();
            workbook.close();
        } catch (EncryptedDocumentException | IOException e) {
            log.info(e, e);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return dataMap;
    }

    @Deprecated
    private static Map<String, Map<String, String>> getAllDataRow(Sheet sheet, String sheetName) {
        Map<String, String> dataMap;

        Map<String, Map<String, String>> allDataMap = new HashMap<>();
        if (sheet != null) {
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row headerRow = sheet.getRow(0);
                if (i == 0) {
                    continue;
                } else if (i > 0) {
                    Row dataRow = sheet.getRow(i);
                    String mapKey = "";
                    dataMap = new HashMap<>();
                    for (int j = 1; j < headerRow.getLastCellNum(); j++) {
                        if (getCellValue(headerRow.getCell(j)).contains("TestScenario")) {
                            mapKey = getCellValue(dataRow.getCell(j));
                        }
                        dataMap.put(getCellValue(headerRow.getCell(j)), getCellValue(dataRow.getCell(j)));
                    }
                    allDataMap.put(mapKey, dataMap);
                }

            }
        }
        return allDataMap;
    }

    public static Map<String, Map<Object, Object>> readAllRows(Map<String, String> map) throws IOException {
        Workbook workbook = null;
        Map<String, Map<Object, Object>> dataMap = new HashMap<>();
        String filePath = (System.getProperty("user.dir") + map.get("excelpath"));
        log.info("filePath =" + filePath);
        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath));
            workbook = WorkbookFactory.create(inputStream);
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            Sheet sheet = null;
            while (sheetIterator.hasNext()) {
                sheet = sheetIterator.next();
                log.info("sheet name {} " + sheet.getSheetName());
                if (sheet.getSheetName().equalsIgnoreCase(map.get("sheetname"))) {
                    break;
                }
            }
            dataMap = getAllDataRow(sheet, map);
            log.info("dataMap {}" + dataMap.size());
            inputStream.close();
            workbook.close();
        } catch (EncryptedDocumentException | IOException e) {
            log.info(e, e);
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return dataMap;
    }

    private static Map<String, Map<Object, Object>> getAllDataRow(Sheet sheet, Map<String, String> map) {
        Map<Object, Object> dataMap;
        Map<String, Map<Object, Object>> allDataMap = new HashMap<>();
        log.info("sheet name***" + map);
        if (sheet != null) {
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row headerRow = sheet.getRow(0);
                if (i == 0) {
                    continue;
                } else if (i > 0) {
                    Row dataRow = sheet.getRow(i);
                    String mapKey = "";
                    dataMap = new HashMap<>();
                    for (int j = 1; j < headerRow.getLastCellNum(); j++) {
                        if (getCellValue(headerRow.getCell(j)).contains("TestScenario")) {
                            mapKey = getCellValue(dataRow.getCell(j));
                        } else if (map.get("sheetname").contains("Case") && dataRow.getCell(j) != null
                            && dataRow.getCell(j).getCellType() != null
                            && dataRow.getCell(j).getCellType() == CellType.NUMERIC) {
                            dataMap.put(getCellValue(headerRow.getCell(j)),
                                (int) dataRow.getCell(j).getNumericCellValue());
                            continue;
                        } else if (getCellValue(headerRow.getCell(j)).contains("TestScenarioJsonTemplateEngine")) {
                            mapKey = getCellValue(dataRow.getCell(j));
                        }
                        dataMap.put(getCellValue(headerRow.getCell(j)), getCellValue(dataRow.getCell(j)));
                    }
                    allDataMap.put(mapKey, dataMap);
                }

            }
        }
        return allDataMap;
    }

    private static String getCellValue(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case BOOLEAN:
                    return Boolean.toString(cell.getBooleanCellValue());
                case STRING:
                    return cell.getRichStringCellValue().getString();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    } else {
                        return Double.toString(cell.getNumericCellValue());
                    }
                case BLANK:
                    return "";
                default:
                    return "";
            }
        } else
            return "";
    }

}
