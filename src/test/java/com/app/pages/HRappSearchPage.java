package com.app.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.app.utilities.Driver;

public class HRappSearchPage {
	
	
private WebDriver driver;
	
	public HRappSearchPage() {
		this.driver=Driver.getDriver();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="qryId1:val00::content")
	public WebElement empIdSearchField;
	
	@FindBy(id="qryId1::search")
	public WebElement search;
	
	@FindBy(id="it1::content")
	public WebElement employeeId;
	
	@FindBy(id="it2::content")
	public WebElement firstName;
	
	@FindBy(id="it3::content")
	public WebElement lastName;
	
	@FindBy(id="it4::content")
	public WebElement email;	
	
	@FindBy(id="sal::content")
	public WebElement salary;
	
	@FindBy(id="id1::content")
	public WebElement hireDate;
	
	@FindBy(id="jobIdId::content")
	public WebElement jobId;
	
	@FindBy(id="it6::content")
	public WebElement departmentId;
	
	@FindBy(id="it7::content")
	public WebElement departmentName;
	
	@FindBy(id="it8::content")
	public WebElement annualSalary;

}
