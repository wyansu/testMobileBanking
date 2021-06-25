package com.wayan.testMobileBanking.test;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.HasOnScreenKeyboard;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.TouchAction;

public class CommonTest {
	

	/*
	 * Prform login to app
	 * All screen until Beranda
	 * **/
	public static void login(String username, String password, AndroidDriver driver) throws Exception {
		
		final String otpXpath = "//android.view.View[@content-desc='Masukkan kode Aktivasi yang telah dikirimkan ke nomor ']//following-sibling::android.view.View";
		
		//wait for Login button
		AndroidElement el = (AndroidElement) new WebDriverWait(driver, 30).until(
				ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Log in")));
		el.click();
		
		//wait for login form ready
		AndroidElement form = (AndroidElement) new WebDriverWait(driver, 30).until(
				ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.EditText")));
		
		List<AndroidElement> els = driver.findElementsByClassName("android.widget.EditText");
		
		els.get(0).click();
		els.get(0).sendKeys(username);
		
		els.get(1).click();
		els.get(1).sendKeys(password);
		if(((HasOnScreenKeyboard) driver).isKeyboardShown()) driver.hideKeyboard();
		
		AndroidElement login = (AndroidElement) new WebDriverWait(driver, 30).until(
				ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("MASUK")));
		login.click();
		
		//wait for OTP screen ready
		AndroidElement otp = (AndroidElement) new WebDriverWait(driver, 30).until(
				ExpectedConditions.elementToBeClickable(By.xpath(otpXpath)));
		
		TouchAction action = new TouchAction(driver);
		action.tap(ElementOption.element(otp,10,10));
		action.perform();
		
		//please dont repeat me
		driver.pressKey(new KeyEvent(AndroidKey.DIGIT_0));
		driver.pressKey(new KeyEvent(AndroidKey.DIGIT_1));
		driver.pressKey(new KeyEvent(AndroidKey.DIGIT_2));
		driver.pressKey(new KeyEvent(AndroidKey.DIGIT_3));
		driver.pressKey(new KeyEvent(AndroidKey.DIGIT_4));
		driver.pressKey(new KeyEvent(AndroidKey.DIGIT_5));

		
		AndroidElement selanjutnya = (AndroidElement) new WebDriverWait(driver, 30).until(
				ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Selanjutnya")));

		selanjutnya.click();
		
		//always end test with verifying end screen state, this enable us to get clean screenshot when needed at the end of the test
		AndroidElement myAccount = (AndroidElement) new WebDriverWait(driver, 30).until(
				ExpectedConditions.elementToBeClickable(MobileBy.AccessibilityId("Rekening Saya")));
	}

	

}
