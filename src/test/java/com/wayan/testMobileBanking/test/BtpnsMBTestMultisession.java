package com.wayan.testMobileBanking.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class BtpnsMBTestMultisession extends BtpnsMBTest{
	
	protected AndroidDriver adDriver;
	protected DesiredCapabilities desiredCapabilities;

	public BtpnsMBTestMultisession(List<String> param) {
		
		super(param);
		
		if (desiredCapabilities == null) {
			desiredCapabilities = new DesiredCapabilities();
		}

	}
	
	@Before
	public void initDriver() throws MalformedURLException {
		//DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		desiredCapabilities.setCapability("dontStopAppOnReset", "true");
		//desiredCapabilities.setCapability("noReset", "true");
		desiredCapabilities.setCapability("appActivity", "MainActivity");
		desiredCapabilities.setCapability("appPackage",  "id.com.bankbtpns.mobile_banking");
		desiredCapabilities.setCapability("autoGrantPermissions", "true");
		//desiredCapabilities.setCapability("udid", "RR8NC06QNX");
		
		URL url = new URL("http://localhost:4723/wd/hub");
		
		adDriver = new AndroidDriver<AndroidElement>(url, desiredCapabilities);
		
	}
	
	@After
	public void cleanup() {
		try {
			adDriver.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
