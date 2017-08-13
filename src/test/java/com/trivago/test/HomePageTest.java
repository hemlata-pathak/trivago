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

	/*
	 * This is data provider for testing the search functionality with multiple
	 * values;More valid search options need to be added in the searchData.json file if
	 * searching needs to be tested with more options.
	 */
	
	@DataProvider(name = "SearchTerms")
	public Object[][] searchDestinations() {
		TestDataParser searchDataParser = _configureTestData("searchDatapath");
		String[] searchTerms = searchDataParser.getSearchTerms();
		return DataProviderUtil.getSearchDataProvider(searchTerms);
	}

	/*
	 * Test method to verify search functionality. this method is using
	 * "SearchTerms" data provider to get valid options for searching. Invalid
	 * search terms will be create randomly using some java functions
	 */
	@Test(testName = "testSearchFunctionality", description = "Verify User should be able to view search results for a valid search term and should get proper message for invalid search term", dataProvider = "SearchTerms")
	public void testSearchFunctionality(String searchTerm) {
		/******* Test Data ***********/
		Properties messageProp = _configureApplicationData("messagesFilepath");
		/*****************************/
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		//Perform search with valid search options
		homePage.performSearch(searchTerm);
		//verified that search results are displayed
		homePage.verifySearchResultsDisplayed();
		//Close the search form
		homePage.closeSearch();
		//Perform search with random invalid search options
		homePage.performSearch(ExtraUtil.getRandomString());
		//verify that message should be displayed to user
		homePage.verifyInvalidSearchResults(messageProp.getProperty("adjustSearchTerm"));
	}

	/*
	 * Test method to verify if all blog links in home page are not broken
	 */
	@Test(testName = "testPostLinks", description = "Verify All the liks in Editor's Pick, Most popular, Recent Articles, Inspirations and Destinations are working ")
	public void testAllLinksInHomePage() {
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		//Get the editor's pick section id
		String editorSectionId = homePage.getEditorSectionId();
		//verify the all links in the editor's pick section are not broken
		homePage.verfiyLinksAreWorkingForASection(editorSectionId);
		//Get the most popular section id
		String mostPopularSectionId = homePage.getMostPupularSectionId();
		//verify the all links in the most popular section are not broken
		homePage.verfiyLinksAreWorkingForASection(mostPopularSectionId);
		//Get the most recent article id
		String recentArticleSectionId = homePage.getLatestArticleSectionId();
		//verify the all links in the recent article section are not broken
		homePage.verfiyLinksAreWorkingForASection(recentArticleSectionId);
		//Get the Inspirations section id
		String inspirationsSectionId = homePage.getInspirationsSectionId();
		//verify the all links in the Inspirations section are not broken
		homePage.verfiyLinksAreWorkingForASection(inspirationsSectionId);
		//Get the Destinations section id
		String destinationsSectionId = homePage.getDestinationsSectionId();
		//verify the all links in the Destinations section are not broken
		homePage.verfiyLinksAreWorkingForASection(destinationsSectionId);
		//Verify that all links in the Footer section are not broken
		homePage.verifyFooterLinksAreWorking();
	}
	
	/*
	 * This is data provider for testing the Change Country functionality with multiple
	 * values;More valid search options need to be added in the localeData.json file if
	 * country selection needs to be tested with more options.
	 */
	@DataProvider(name = "localeData")
	public Object[][] selectCountry() {
		TestDataParser localeDataParser = _configureTestData("localeDatapath");
		JSONArray locales = localeDataParser.getLocaleData();
		return DataProviderUtil.getLocaleDataProvider(locales);
	}

	/*
	 * Test method to verify if user is able to select a country from the country dropdown in footer section
	 * and on selecting the country domain and locale should be updated as per the selected locale
	 */
	@Test(testName = "testSelectCountryFunctionality", description = "Verify User should be able to view updated domain and updated language as per the selected locale from the dropdown", dataProvider = "localeData")
	public void testSelectCountryFunctionality(String country, String localeDomain, String language) {
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		//Select the country from the dropdown in footer section
		homePage.selectCountry(country);
		//Verify that application url and language is updated as per the locale of selected country
		homePage.verifyLocaleUpadted(localeDomain, language);
	}
	
	/*
	 * Test method to verify if user is able to Access navigation menu from the header section and all links in the navigation menu are working fine
	 */
	@Test(testName = "testNavigationMenu", description = "Verify User should be able to access navigation menu from the home page and all links in navigation menu are working")
	public void testNavigationMenu() {
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		homePage.verifyUserIsOnHomePage();
		//expand the navigation menu by clicking the menu icon in header
		homePage.expandNavigationMenu();
		//Verify that user should be able to view the navigation items and all links are working 
		homePage.verifyNavigationMenuLinksAreWorking();
		//Close the navigation menu
		homePage.collapseNavigationMenu();
	}
	
	/*
	 * Test method to verify if user is able to Access social media sharing links from the footer section
	 */
	@Test(testName = "testSocialMediaSharing", description = "Verify User should be able to launch the social media links from the home page")
	public void testSocialMediaSharing() {
		// Launch the application
		launchApplication();
		HomePage homePage = new HomePage(driver);
		//verify that user is able to access socialMedia links from the application
		homePage.verifySocialMediaSharingLinksAreWorking();
	}
	
	

}
