package com.trivago.pages;


import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.trivago.configpageobjects.GetPage;
import com.trivago.utils.ExtraUtil;

public class HomePage extends GetPage {
	
	public HomePage(WebDriver driver) {
		super(driver, "HomePage");
	}
	
	//function to verify that user is on home page and able to view branding elements in home page
	public void verifyUserIsOnHomePage() {
		isElementDisplayed("editorPickHeader_label");
		isElementDisplayed("logo_img");
		isElementDisplayed("copyright_label");
		logMessage("Verified that the user is on the Home Page");
	}
	
	//function to select country
	public String selectCountry(String countryName){
		selectByVisibleText(element("selectCountry_dropdown"), countryName);
		return getSelectedValue(element("selectCountry_dropdown"));
	}
	
	//function to perform search
	public void performSearch(String searchText){
		click(element("search_button"));
		element("search_textbox").sendKeys(searchText);
		hardWait(1);
		element("search_textbox").sendKeys(Keys.ENTER);
		logMessage(searchText+" is searched.");
	}
	
	//function to close search form
	public void closeSearch(){
		click(element("searchClose_button"));
		isElementDisplayed("editorPickHeader_label");
		hardWait(1);
	}
	
	//function to verify search results
	public void verifySearchResultsDisplayed(){
		isElementDisplayed("suggestedPostHeader_label");
		logMessage(element("suggestedPostHeader_label").getText()+" displayed.");
	}
	
	//function to verify invalid search message
	public void verifyInvalidSearchResults(String expectedMsg){
		isElementDisplayed("noResultsDisplayed_label");
		verifyElementContainsText("adjustSearchTermMsg_label", expectedMsg);
		logMessage(expectedMsg +" is displayed.");
	}
	
	//function to expand navigation menu
	public void expandNavigationMenu(){
		click(element("navigationMenu_button"));
		isElementDisplayed("navigationMenuLinks_list");
		logMessage("Navigation menu is expanded.");
	}
	
	//function to close navigation menu
	public void collapseNavigationMenu(){
		click(element("navigationMenu_button"));
		isElementDisplayed("editorPickHeader_label");
		hardWait(1);
		logMessage("Navigation menu is closed.");
	}
	
	//function to verify the application url is updated and language is updated as per the selected locale
	public void verifyLocaleUpadted(String localeDomain, String language){
		Assert.assertTrue(getCurrentURL().contains(localeDomain),"locale domain should be updated on selecting the country");
		logMessage("locale domain is updated on selecting the country ");
		isStringMatching(element("root_tag").getAttribute("lang"), language);
	}
	
	//function to get editor section id
	public String getEditorSectionId(){
		return element("editorPickSection_container").getAttribute("id");
	}
	
	//function to 
	public String getMostPupularSectionId(){
		return element("mostPopularSection_container").getAttribute("id");
	}
	
	//function to 
	public String getLatestArticleSectionId(){
		return element("latestArticleSection_container").getAttribute("id");
	}
	
	//function to 
	public String getInspirationsSectionId(){
		return element("inspirationSection_container").getAttribute("id");
	}
	
	//function to 
	public String getDestinationsSectionId(){
		return element("destinationsSection_container").getAttribute("id");
	}
	
	//function to verify the broken links in a section
	public void verfiyLinksAreWorkingForASection(String sectionId){
		verifyPostImagesLinks(sectionId);
		logMessage("Verified that all post images are displayig in :"+sectionId);
		verifyPostTitleLinks(sectionId);
		logMessage("Verified that all post titles links are working in :"+sectionId);
		verifyTagLinks(sectionId);
		logMessage("Verified that all tags links are working in :"+sectionId);
	}
	
	//function to verify the broken images in a section
	public void verifyPostImagesLinks(String sectionId){
		String elementName = sectionId.contains("editors_pick") ? "editorsPickpostImages_list":"postImages_list";
		List<WebElement> postImagesLinks = elements(elementName, sectionId);
		for(WebElement img : postImagesLinks){
			String src = img.getAttribute("style");
			src = src.split("\"")[1].trim();
			Assert.assertTrue(ExtraUtil.checkBrokenLink(src),src+" link is broken");
			logMessage("Verified that "+src +" is not broken");
		}
	}
	
	//function to verify broken title links in a section
	public void verifyPostTitleLinks(String sectionId){
		verifyAnchorTagLinks(elements("postTitles_list", sectionId));
	}
	
	//function to verify broken tag links in a section
	public void verifyTagLinks(String sectionId){
		verifyAnchorTagLinks(elements("tagLinks_list", sectionId));
	}
	
	//function to verify footer links
	public void verifyFooterLinksAreWorking(){
		verifyAnchorTagLinks(elements("footerLinks_list"));
	}
	
	//function to verify navigation menu links
	public void verifyNavigationMenuLinksAreWorking() {
		verifyAnchorTagLinks(elements("navigationMenuLinks_list"));
	}
	
	//function to verify social media sharing links
	public void verifySocialMediaSharingLinksAreWorking() {
		verifyAnchorTagLinks(elements("socialMediaLinks_list"));
	}
	
	public AboutUsPage goToAboutUsPage(){
		click(element("about_link"));
		return PageFactory.initElements(driver, AboutUsPage.class);
	}

}
