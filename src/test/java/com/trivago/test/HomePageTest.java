package com.trivago.test;

import java.util.Properties;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.trivago.utils.DataProviderUtil;
import com.trivago.utils.ExtraUtil;
import com.trivago.utils.TestDataParser;
import com.trivago.configenvironment.TestSessionInitiator;
import com.trivago.pages.HomePage;

public class HomePageTest extends TestSessionInitiator {

	@DataProvider(name = "SearchTerms")
	public Object[][] searchDestinations() {
		TestDataParser searchDataParser = _configureTestData("searchDatapath");
		String[] searchTerms = searchDataParser.getSearchTerms();
		return DataProviderUtil.getSearchDataProvider(searchTerms);
	}
	 
	@Test(testName = "testSearchFunctionality", description = "Verify User should be able to view search results for a valid search term and should get proper message for invalid search term", dataProvider = "SearchTerms")
	public void testSearchFunctionality(String searchTerm) {
		/******* Test Data ***********/
		Properties messageProp = _configureApplicationData("messagesFilepath");
		/*****************************/
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		homePage.performSearch(searchTerm);
		homePage.verifySearchResultsDisplayed();
		homePage.closeSearch();
		homePage.performSearch(ExtraUtil.getRandomString());
		homePage.verifyInvalidSearchResults(messageProp.getProperty("adjustSearchTerm"));
	}
	
	@Test(testName = "testSelectCountryFunctionality", description = "Verify User should be able to view search results for a valid search term and should get proper message for invalid search term",dataProvider="SearchTerms")
	public void testSelectCountryFunctionality(String searchTerm) {
		/******* Test Data ***********/
		Properties messageProp = _configureApplicationData("messagesFilepath");
		/*****************************/
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		homePage.performSearch(searchTerm);
		homePage.verifySearchResultsDisplayed();
		homePage.closeSearch();
		homePage.performSearch(ExtraUtil.getRandomString());
		homePage.verifyInvalidSearchResults(messageProp.getProperty("adjustSearchTerm"));
	}
	
	

}
