package com.way2.util.testutils;

import java.io.IOException;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExcelTestData {
	
	private static final Logger log = LogManager.getLogger(ExcelTestData.class);
    private static Map<String, Map<String, String>> dataMap;
    private static Map<String, Map<Object, Object>> dataMapNew;
    protected ExcelTestData() {}

    @SuppressWarnings("deprecation")
    public static Map<String, Map<String, String>> getTestDataBySheet(String excelPath, String sheetName) {
        log.info("get excel data ");
        synchronized (ExcelTestData.class) {
            new ExcelTestData();
                try {
                    dataMap = ExcelReader.readAllRows(excelPath, sheetName);
                } catch (IOException e) {
                    log.info(e, e);
                }
//            }

        }
        return dataMap;
    }

    public static Map<String, Map<Object, Object>> getTestDataBySheet(Map<String, String> map) {
        log.info("get excel data ");
        synchronized (ExcelTestData.class) {
            new ExcelTestData();
                try {
                    dataMapNew = ExcelReader.readAllRows(map);
                } catch (IOException e) {
                    log.info(e, e);
                }
            }

//        }
        return dataMapNew;
    }
}
