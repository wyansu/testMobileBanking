package com.wayan.testMobileBanking.test;




import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.edge.EdgeDriver;

public class IETest {


	private EdgeDriver edgeDriver;
	
	@Before
	public void init() {
		edgeDriver = new EdgeDriver();
		
	}
	
	@Test
	public void testOpenEdge() {
		edgeDriver.get("google.com");
	}
	

}
