package com.wayan.testMobileBanking.test;



import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

@RunWith(Parameterized.class)
public class BtpnsMBTest{

	//test class parameter
	protected HashMap<String, String> testParam;
	
	protected static Workbook wb;
	protected static Sheet sheet;
	protected static List<String> sheetHeader;
	protected static int nextRow;
	protected static OutputStream fileout;
	
	
	public BtpnsMBTest(List<String> param ) {
		testParam = new HashMap<String, String>();
		System.out.println(param.toString());
		
		for(int i=0; i< param.size() ; i++) {
			testParam.put(sheetHeader.get(i), param.get(i));
		}
		
	}
	
	/*
	 * provided here only for default behaviour, class extended from this class should provide same method to initialize test
	 * 
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
		
		
		for(Cell cell : sheet.getRow(sheet.getFirstRowNum())) {
			sheetHeader.add(cell.getStringCellValue());			
		}
		
		return getParamFromWorkbookList(2, className );
	} 
	*/
	
	@AfterClass
	public static void close() throws IOException {
		wb.write(fileout);
		wb.close();
		fileout.close();

	}
	
	
	/*
	 * Save screenshot to fileout workbook in field Printscreen when this field exist in config file
	 * **/	
	public void saveScreenshot(AndroidDriver adDriver) {
		
		int col = getColumnIndex("Printscreen");
		if(col == -1) return;
		
		byte[] scrFile = ((TakesScreenshot)adDriver).getScreenshotAs(OutputType.BYTES);
		
		CreationHelper helper = wb.getCreationHelper();
		Drawing drawing = sheet.createDrawingPatriarch();
		ClientAnchor anchor = helper.createClientAnchor();
		anchor.setCol1(col);
		anchor.setRow1(getCurrentSequenceTest());
		int pictureIdx = wb.addPicture(scrFile, Workbook.PICTURE_TYPE_PNG);
		
		Picture pic = drawing.createPicture(anchor, pictureIdx);
		pic.resize();
		pic.resize(0.3);
		
	}
	
	/*
	 * 
	 * **/
	public void writeToTestOuput(String columnName, String word) {
		
		int col = getColumnIndex(columnName);
		if(col == -1) return;
		
		Row row = sheet.getRow(getCurrentSequenceTest());
		Cell cell = row.createCell(col);
		cell.setCellValue(word);
		
	}
	
	private int getCurrentSequenceTest() {
		return Integer.parseInt(testParam.get("TestSequence"));
	}
	
	private int getColumnIndex(String headerName) {
		try {
			return sheetHeader.indexOf(headerName) - 1;
		} catch(Exception e) {
			return -1;
		}
	}
	
	/**
	 * get parameter from file xls
	 * numColumn : number of column that are used to be parameter
	 * filePathName : full name and path of file, for easy used src/test/resources to hold all parameter file;
	 * sheetNmae : the sheet of file used to hold parameter
	 * */
	
	public static ArrayList<ArrayList<String>> getParamFromWorkbookList(String sheetName) throws EncryptedDocumentException, IOException {

		ArrayList<ArrayList<String>> param = new ArrayList<ArrayList<String>>();
				
		Sheet sheet = wb.getSheet(sheetName);
		
		int counterRow = 0;
		
		
		/*for(Row row: sheet) {
			counterRow++;
			if(counterRow == 1) continue;
			ArrayList<String> inner2 = new ArrayList<String>();
		
			inner2.add(Integer.toString(counterRow -1));
			for(Cell cell: row) {

				String val = cell.getStringCellValue(); 
				inner2.add(val!=null?val:"");
			}
			
			param.add(inner2);
		}*/
		
		Row firstRow = sheet.getRow(0);
		int sequence = 1;
		
		for(Row row: sheet) {
			if(row.getRowNum() == 0) continue; //skip first row
			
			ArrayList<String> inner2 = new ArrayList<String>();
			inner2.add(Integer.toString(sequence++));
			
			for(int col=0; col < firstRow.getLastCellNum(); col++) {
				Cell cell = row.getCell(col);
				if(cell == null) {
					inner2.add("");
				}
				else {
				
					inner2.add(cell.getStringCellValue());
				}
			}
			param.add(inner2);
		}

		return param;
	}
	

}
