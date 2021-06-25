package com.wayan.testMobileBanking.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runners.Parameterized;

public class LoginTest extends BtpnsMBTestMultisession{
	
	/*
	 * Must be here to initialize this class with parent properties
	 * */
	public LoginTest(List<String> param) {
		super(param);
	}

	/* put all test scenarios here or use JUnit @Test annotation to create new test case in same class 
	 * although it is not applicable for this scenario test with Data Driven Test
	 * */
	@Test
	public void loginTest() throws Exception {
		try{
			CommonTest.login(testParam.get("Username"), testParam.get("Password"), adDriver );
			writeToTestOuput("Status", "Success");
			saveScreenshot(adDriver);
		} catch(Exception e) {
			writeToTestOuput("Status", "Failed");
			writeToTestOuput("ErrorMessage", e.getMessage());
			saveScreenshot(adDriver);
			throw e;
		}
	}
	
	
	/*
	 * static method, copied here to get class name as a config file name. No need to changes this method.
	 * */
	@Parameterized.Parameters
	public static Collection<ArrayList<String>> input() throws EncryptedDocumentException, IOException  {
		
		String className = MethodHandles.lookup().lookupClass().getSimpleName() ;
		
		//open file from resource to initialize test
		wb = WorkbookFactory.create(new File("src/test/resources/" + className + ".xls"));
		
		//prepare fileout as a test result
		fileout = new FileOutputStream("c:\\temp\\"+ className +"_result.xls");		
				
		//initialize header
		sheetHeader = new ArrayList<String>();
		sheet = wb.getSheet(className);
		
		sheetHeader.add("TestSequence");
		for(Cell cell : sheet.getRow(sheet.getFirstRowNum())) {
			sheetHeader.add(cell.getStringCellValue());			
		}

		System.out.println(sheetHeader.toString());
		
		return getParamFromWorkbookList(className );
	} 
	
}
