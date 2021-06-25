package com.wayan.testMobileBanking.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class BtpnsMBTestSinglesession extends BtpnsMBTest{
	
	protected static AndroidDriver adDriver;
	
	public BtpnsMBTestSinglesession(List<String> param) {
		super(param);
	}

	
	@BeforeClass
	public static void initDriver() throws MalformedURLException {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		//desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "RR8NC06QNXV");//"samsung SM-A125F");
		desiredCapabilities.setCapability("appActivity", "MainActivity");
		desiredCapabilities.setCapability("appPackage",  "id.com.bankbtpns.mobile_banking");
		desiredCapabilities.setCapability("autoGrantPermissions", "true");
		
		URL url = new URL("http://localhost:4723/wd/hub");
		
		adDriver = new AndroidDriver<AndroidElement>(url, desiredCapabilities);
		
	}


}
