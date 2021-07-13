package com.wayan.testMobileBanking.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.ElementOption;

public class RegistrationTest extends BtpnsMBTestMultisession{

	public RegistrationTest(List<String> param) {
		super(param);
		
		/*desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("noReset", "true");*/

	}
	
	
	@Test
	public void registrationTest( ) {
		try{
			register();
			writeToTestOuput("Status", "Success");
			saveScreenshot(adDriver);
		} catch(Exception e) {
			writeToTestOuput("Status", "Fail");
			writeToTestOuput("ErrorMessage", e.getMessage());
			saveScreenshot(adDriver);
			throw e;
		}
	}
	
	private void register() {
		String clearButtonId = "com.android.systemui:id/clear_all";
		
		final String registrationButtonXPath ="//android.widget.ImageView[@content-desc='Registrasi']";
		final String checkBoxApproveXPath ="//android.widget.CheckBox[@content-desc='Dengan memilih button setuju berarti Anda sudah memahami segala syarat dan ketentuan yang berlaku.']";
		final String approveButtonXPath ="//android.widget.Button[@content-desc='Setuju']";
		final String inputAccountXPath ="//android.view.View[@content-desc='Nomor Rekening']//following-sibling::android.widget.EditText";
		final String nextButtonAccount ="//android.widget.Button[@content-desc='Selanjutnya']";
		final String inputKTPXPath = "//android.view.View[@content-desc='Nomor KTP']//following-sibling::android.widget.EditText";
		final String inputEmailXPath ="//android.view.View[@content-desc='Email(Optional)']//following-sibling::android.widget.EditText";
		final String OTPXPath ="//android.view.View[contains(@content-desc,'Masukkan kode Aktivasi yang telah dikirimkan ke nomor')]//following-sibling::android.view.View";
		final String inputUsernameXPath ="//android.view.View[@content-desc='Buat Username Mobile Banking']//following-sibling::android.widget.EditText";
		final String inputPasswordXPath ="//android.view.View[@content-desc='Buat Password Mobile Banking']//following-sibling::android.widget.EditText";
		
		//clearNotif
		adDriver.openNotifications();
		try {
			AndroidElement clearButton  = (AndroidElement) new WebDriverWait(adDriver, 5).until(
				ExpectedConditions.elementToBeClickable(By.id(clearButtonId)));
			
			clearButton.click();
		} catch(Exception e) {
			adDriver.pressKey(new KeyEvent(AndroidKey.BACK));
		}
		
		
		//wait until page loaded
		AndroidElement registrationButton = (AndroidElement) new WebDriverWait(adDriver, 30).until(
				ExpectedConditions.elementToBeClickable(By.xpath(registrationButtonXPath)));
		
		registrationButton.click();
		
		//wait until TC page loaded
		AndroidElement tCCheckBox  = (AndroidElement) new WebDriverWait(adDriver, 30).until(
				ExpectedConditions.elementToBeClickable(By.xpath(checkBoxApproveXPath)));
		
		if(!testParam.get("Accept").toUpperCase().equals("NO")) {
			tCCheckBox.click();
		}
		
		adDriver.findElementByXPath(approveButtonXPath).click();
		
		//wait username page loaded
		AndroidElement inputAccount  = (AndroidElement) new WebDriverWait(adDriver, 30).until(
				ExpectedConditions.elementToBeClickable(By.xpath(inputAccountXPath)));
		
		inputAccount.click();
		keyNum(testParam.get("Account"));

		if(((HasOnScreenKeyboard) adDriver).isKeyboardShown()) adDriver.hideKeyboard();
		
		//next
		adDriver.findElementByXPath(nextButtonAccount).click();

		//wait id page loaded
		AndroidElement inputKTP  = (AndroidElement) new WebDriverWait(adDriver, 30).until(
				ExpectedConditions.elementToBeClickable(By.xpath(inputKTPXPath)));
		
		inputKTP.click();
		keyNum(testParam.get("ID"));
		if(((HasOnScreenKeyboard) adDriver).isKeyboardShown()) adDriver.hideKeyboard();
		//next
		adDriver.findElementByXPath(nextButtonAccount).click();
		
		//wait email page loaded
		AndroidElement inputEmail  = (AndroidElement) new WebDriverWait(adDriver, 30).until(
				ExpectedConditions.elementToBeClickable(By.xpath(inputEmailXPath)));
		
		inputEmail.click();
		inputEmail.sendKeys(testParam.get("Email"));
		if(((HasOnScreenKeyboard) adDriver).isKeyboardShown()) adDriver.hideKeyboard();
		
		//next
		adDriver.findElementByXPath(nextButtonAccount).click();
		
		//wait for OTP screen
		AndroidElement inputOTP  = (AndroidElement) new WebDriverWait(adDriver, 30).until(
				ExpectedConditions.elementToBeClickable(By.xpath(OTPXPath)));
		
		String otp;
		if(testParam.get("OTP").isBlank() ||testParam.get("OTP").isEmpty() ) {
			otp = getOTPFromNotification();
		} else {
			otp = testParam.get("OTP");
		}
		
		TouchAction action = new TouchAction(adDriver);
		action.tap(ElementOption.element(inputOTP,10,10));
		action.perform();

		keyNum(otp);
		
		//next
		adDriver.findElementByXPath(nextButtonAccount).click();
		
		//username
		AndroidElement inputUsername  = (AndroidElement) new WebDriverWait(adDriver, 30).until(
				ExpectedConditions.elementToBeClickable(By.xpath(inputUsernameXPath)));
		
		inputUsername.click();
		inputUsername.sendKeys(testParam.get("Username"));
		if(((HasOnScreenKeyboard) adDriver).isKeyboardShown()) adDriver.hideKeyboard();
		
		//next
		adDriver.findElementByXPath(nextButtonAccount).click();
		
		//input password
		AndroidElement inputPassword  = (AndroidElement) new WebDriverWait(adDriver, 30).until(
				ExpectedConditions.elementToBeClickable(By.xpath(inputPasswordXPath)));
		
		List<AndroidElement> inputPwds = adDriver.findElementsByXPath(inputPasswordXPath);
		
		//pwd element
		AndroidElement inputMainPwd = inputPwds.get(0);
		inputMainPwd.click();
		inputMainPwd.sendKeys(testParam.get("Password"));
		if(((HasOnScreenKeyboard) adDriver).isKeyboardShown()) adDriver.hideKeyboard();
		
		//pwd confirmpwd
		String confirmPwd;
		if(testParam.get("ConfirmPwd").isBlank()) {
			confirmPwd = testParam.get("Password");
		}
		else {
			confirmPwd = testParam.get("ConfirmPwd");
		}
		
		AndroidElement inputConfirmPwd = inputPwds.get(1);
		inputConfirmPwd.click();
		inputConfirmPwd.sendKeys(confirmPwd);
		if(((HasOnScreenKeyboard) adDriver).isKeyboardShown()) adDriver.hideKeyboard();
		
		//next last step
		adDriver.findElementByXPath(nextButtonAccount).click();
		

		
		//make PIN
		makePIN("Buat Mobile PIN",testParam.get("PIN"));
		//confirmation PIN
		makePIN("Masukkan kembali Mobile PIN", testParam.get("ConfirmPIN"));
		
		//finally
		/*String usernameXPath = "//android.view.View[@content-desc='"+ testParam.get("Username")+"']";
				AndroidElement title  = (AndroidElement) new WebDriverWait(adDriver, 30).until(
						ExpectedConditions.presenceOfElementLocated(By.xpath( makePinTitleXPath)));*/
		
	}
	
	private void makePIN(String id, String pin) {
		String makePinTitleXPath = "//android.view.View[@content-desc='"+ id +"']";
		String pinKeyboardXPath = "//android.view.View[@content-desc='"+ id +"']//following-sibling::android.widget.Button";
		
		AndroidElement title  = (AndroidElement) new WebDriverWait(adDriver, 30).until(
				ExpectedConditions.presenceOfElementLocated(By.xpath( makePinTitleXPath)));

		List<AndroidElement> keys = adDriver.findElementsByXPath(pinKeyboardXPath);
	
		char[] pins = pin.toCharArray();
		for(char pinEntry : pins) {
			AndroidElement keyElement;
			if(pinEntry == '0') {
				keyElement = keys.get(10);
			}
			else {
				keyElement = keys.get(Integer.parseInt(String.valueOf(pinEntry)) - 1);
			}
			
			keyElement.click();
			

		}
		
		//click confirm
		keys.get(11).click();
		
	}
	
	private String getOTPFromNotification() {
		
		String smsFromId = "android:id/conversation_text";
		String smsFromBTPNXPath = "//android.widget.TextView[@resource-id='android:id/message_text']";
		
		adDriver.openNotifications();
		//AndroidElement clearButton  = (AndroidElement) new WebDriverWait(adDriver, 30).until(
		//		ExpectedConditions.elementToBeClickable(By.xpath(clearButtonXPath)));
		
		AndroidElement smsFrom  = (AndroidElement) new WebDriverWait(adDriver, 30).until(
				ExpectedConditions.visibilityOfElementLocated(By.id(smsFromId)));

		List<AndroidElement> smsOTPs = adDriver.findElementsByXPath(smsFromBTPNXPath);
		

		
		String latestSms = smsOTPs.get(smsOTPs.size() -1).getText();
		Pattern sixDigit = Pattern.compile("[\\d]{6}");
		Matcher match = sixDigit.matcher(latestSms);
		
		String otp = match.find()?match.group():"000000";
		
		//close notif
		adDriver.pressKey(new KeyEvent(AndroidKey.BACK));
		
		return otp;
	}
	
	private void keyNum(String text) {
		char[] keys = text.toCharArray();
		
		for(char key: keys) {
			String num = "DIGIT_" + key;
			adDriver.pressKey(new KeyEvent(AndroidKey.valueOf(num)));
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
