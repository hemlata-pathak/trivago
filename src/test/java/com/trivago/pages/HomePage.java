package com.trivago.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import com.trivago.configpageobjects.GetPage;

public class HomePage extends GetPage {
	
	public HomePage(WebDriver driver) {
		super(driver, "HomePage");
	}
	
	public void verifyUserIsOnHomePage() {
		isElementDisplayed("editorPickHeader_label");
		isElementDisplayed("logo_img");
		logMessage("Verified that the user is on the Home Page");
	}
	
	public String selectCountry(String countryName){
		selectByVisibleText(element("selectCountry_dropdown"), countryName);
		return getSelectedValue(element("selectCountry_dropdown"));
	}
	
	public void performSearch(String searchText){
		click(element("search_button"));
		element("search_textbox").sendKeys(searchText);
		hardWait(1);
		element("search_textbox").sendKeys(Keys.ENTER);
		logMessage(searchText+" is searched.");
	}
	
	public void closeSearch(){
		click(element("searchClose_button"));
		isElementDisplayed("editorPickHeader_label");
		hardWait(1);
	}
	
	public void verifySearchResultsDisplayed(){
		isElementDisplayed("suggestedPostHeader_label");
		logMessage(element("suggestedPostHeader_label").getText()+" displayed.");
	}
	
	public void verifyInvalidSearchResults(String expectedMsg){
		isElementDisplayed("noResultsDisplayed_label");
		verifyElementContainsText("adjustSearchTermMsg_label", expectedMsg);
		logMessage(expectedMsg +" is displayed.");
	}

}
