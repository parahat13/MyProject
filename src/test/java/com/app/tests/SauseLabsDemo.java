package com.app.tests;


import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;


public class SauseLabsDemo {
	
	
	WebDriver driver;
	
	  public static final String USERNAME = "comal";
	  public static final String ACCESS_KEY = "33b65d51-1b82-4ed3-8a21-654d70da8bed";
	  public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
	

	@BeforeTest
	public void setUp() throws MalformedURLException {
		DesiredCapabilities caps = DesiredCapabilities.firefox();
		caps.setPlatform(Platform.SIERRA);
		caps.setCapability("version", "latest");
		
		driver = new RemoteWebDriver(new URL(URL), caps);
	}
	
	@Test
	public void testGoogle() throws InterruptedException {
		driver.get("https://google.com");
		driver.findElement(By.id("lst-ib")).sendKeys("Java programmming" + Keys.ENTER);
		System.out.println(driver.getTitle());
		Assert.assertTrue(driver.getTitle().startsWith("Java programmming" ));
		
		Thread.sleep(2345);

	}
	
	@AfterTest
	public void cleanUp() {
		driver.close();
	}
	
	

}
