package com.trivago.test;

import java.util.Properties;

import org.json.simple.JSONArray;
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

	@Test(enabled = false, testName = "testSearchFunctionality", description = "Verify User should be able to view search results for a valid search term and should get proper message for invalid search term", dataProvider = "SearchTerms")
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

	@Test(enabled = false, testName = "testPostLinks", description = "Verify All the liks in Editor's Pick, Most popular, Recent Articles, Inspirations and Destinations are working ")
	public void testAllLinksInHomePage() {
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		String editorSectionId = homePage.getEditorSectionId();
		homePage.verfiyLinksAreWorkingForASection(editorSectionId);
		String mostPopularSectionId = homePage.getMostPupularSectionId();
		homePage.verfiyLinksAreWorkingForASection(mostPopularSectionId);
		String recentArticleSectionId = homePage.getLatestArticleSectionId();
		homePage.verfiyLinksAreWorkingForASection(recentArticleSectionId);
		String inspirationsSectionId = homePage.getInspirationsSectionId();
		homePage.verfiyLinksAreWorkingForASection(inspirationsSectionId);
		String destinationsSectionId = homePage.getDestinationsSectionId();
		homePage.verfiyLinksAreWorkingForASection(destinationsSectionId);
		homePage.verifyFooterLinksAreWorking();
	}

	@DataProvider(name = "localeData")
	public Object[][] selectCountry() {
		TestDataParser localeDataParser = _configureTestData("localeDatapath");
		JSONArray locales = localeDataParser.getLocaleData();
		return DataProviderUtil.getLocaleDataProvider(locales);
	}

	@Test(enabled = false,testName = "testSelectCountryFunctionality", description = "Verify User should be able to view search results for a valid search term and should get proper message for invalid search term", dataProvider = "localeData")
	public void testSelectCountryFunctionality(String country, String localeDomain, String language) {
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		homePage.selectCountry(country);
		homePage.verifyLocaleUpadted(localeDomain, language);
	}
	
	@Test(testName = "testNavigationMenu", description = "Verify User should be able to view search results for a valid search term and should get proper message for invalid search term")
	public void testNavigationMenu() {
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		homePage.expandNavigationMenu();
		homePage.verifyNavigationMenuLinksAreWorking();
		homePage.collapseNavigationMenu();
	}
	
	

}
