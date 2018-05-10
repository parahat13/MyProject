package com.app.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

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


public class SeleniumGridDemo {
	
	
	WebDriver driver;

	public static final String URL = "http://52.70.143.199:4444/wd/hub";
	

	@BeforeTest
	public void setUp() throws MalformedURLException {
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setPlatform(Platform.ANY);
		
		driver = new RemoteWebDriver(new URL(URL), caps);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testGoogle() throws InterruptedException {
		driver.get("https://www.google.com");
		driver.findElement(By.name("q")).sendKeys("Java programmming" + Keys.ENTER);
		System.out.println(driver.getTitle());
		Assert.assertTrue(driver.getTitle().startsWith("Java programmming" ));
		
		Thread.sleep(2345);

	}
	
	@AfterTest
	public void cleanUp() {
		driver.close();
	}
	
	

}
