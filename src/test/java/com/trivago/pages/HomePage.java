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
	
	public void expandNavigationMenu(){
		click(element("navigationMenu_button"));
		isElementDisplayed("navigationMenuLinks_list");
		logMessage("Navigation menu is expanded.");
	}
	
	public void collapseNavigationMenu(){
		click(element("navigationMenu_button"));
		isElementDisplayed("editorPickHeader_label");
		hardWait(1);
		logMessage("Navigation menu is closed.");
	}
	
	public void verifyLocaleUpadted(String localeDomain, String language){
		Assert.assertTrue(getCurrentURL().contains(localeDomain),"locale domain should be updated on selecting the country");
		logMessage("locale domain is updated on selecting the country ");
		isStringMatching(element("root_tag").getAttribute("lang"), language);
	}
	
	public String getEditorSectionId(){
		return element("editorPickSection_container").getAttribute("id");
	}
	
	public String getMostPupularSectionId(){
		return element("mostPopularSection_container").getAttribute("id");
	}
	
	public String getLatestArticleSectionId(){
		return element("latestArticleSection_container").getAttribute("id");
	}
	
	public String getInspirationsSectionId(){
		return element("inspirationSection_container").getAttribute("id");
	}
	
	public String getDestinationsSectionId(){
		return element("destinationsSection_container").getAttribute("id");
	}
	
	public void verfiyLinksAreWorkingForASection(String sectionId){
		verifyPostImagesLinks(sectionId);
		logMessage("Verified that all post images are displayig in :"+sectionId);
		verifyPostTitleLinks(sectionId);
		logMessage("Verified that all post titles links are working in :"+sectionId);
		verifyTagLinks(sectionId);
		logMessage("Verified that all tags links are working in :"+sectionId);
	}
	
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
	
	public void verifyPostTitleLinks(String sectionId){
		List<WebElement> postTitlesLinks = elements("postTitles_list", sectionId);
		for(WebElement title : postTitlesLinks){
			String src = title.getAttribute("href");
			Assert.assertTrue(ExtraUtil.checkBrokenLink(src),src+" link is broken");
			logMessage("Verified that "+src +" is not broken");
		}
	}
	
	public void verifyTagLinks(String sectionId){
		List<WebElement> postTagLinks = elements("tagLinks_list", sectionId);
		for(WebElement tag : postTagLinks){
			String src = tag.getAttribute("href").trim();
			Assert.assertTrue(ExtraUtil.checkBrokenLink(src),src+" link is broken");
			logMessage("Verified that "+src +" is not broken");
		}
	}
	
	public void verifyFooterLinksAreWorking(){
		List<WebElement> footerLinks = elements("footerLinks_list");
		for(WebElement footerTag : footerLinks){
			String src = footerTag.getAttribute("href").trim();
			Assert.assertTrue(ExtraUtil.checkBrokenLink(src),src+" link is broken");
			logMessage("Verified that "+src +" is not broken");
		}
	}
	
	public void verifyNavigationMenuLinksAreWorking() {
		List<WebElement> navMenuLinks = elements("navigationMenuLinks_list");
		for (WebElement menuLink : navMenuLinks) {
			String src = menuLink.getAttribute("href").trim();
			if (!src.equals("#")) {
				Assert.assertTrue(ExtraUtil.checkBrokenLink(src), src + " link is broken");
				logMessage("Verified that " + src + " is not broken");
			}
		}
	}
	
	public AboutUsPage goToAboutUsPage(){
		click(element("about_link"));
		return PageFactory.initElements(driver, AboutUsPage.class);
	}

}
