package com.trivago.configpageobjects;

import static com.trivago.utils.DataReadWrite.getProperty;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import com.trivago.utils.SeleniumWait;

/**
 *Class to perform basic operations on page elements
 * 
 */
public class BaseUi {

    WebDriver driver;
    protected SeleniumWait wait;
    private String pageName;

    protected BaseUi(WebDriver driver, String pageName) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.pageName = pageName;
        this.wait = new SeleniumWait(driver,
                Integer.parseInt(getProperty("Config.properties", "timeout")));
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void logMessage(String message) {
        Reporter.log(message, true);
    }

    protected String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    //verfiy page title
    protected void verifyPageTitleExact(String expectedPagetitle) {
        wait.waitForPageTitleToContain(expectedPagetitle);
        assertEquals(getPageTitle(), expectedPagetitle, "Test Failed due to page title check on " + pageName);
        logMessage("Assertion Passed: PageTitle for " + pageName
                + " is exactly: '" + expectedPagetitle + "'");
    }
    
    /**
     * this method will get page title of current window and match it partially with the param provided
     *
     * @param expectedPagetitle partial page title text
     */
    protected void verifyPageTitleContains(String expectedPagetitle) {
        wait.waitForPageTitleToContain(expectedPagetitle);
        String actualPageTitle = getPageTitle().trim();
        assertTrue(actualPageTitle.contains(expectedPagetitle),
                "Verifying Actuals Page Title: '" + actualPageTitle
                + "' contains expected Page Title : '"
                + expectedPagetitle + "'.");
        logMessage("Assertion Passed: PageTitle for " + actualPageTitle
                + " contains: '" + expectedPagetitle + "'.");
    }

    //get element from a web element list based on the index
    protected WebElement getElementByIndex(List<WebElement> elementlist, int index) {
        return elementlist.get(index);
    }

    //get element from the list based on the text value
    protected WebElement getElementByExactText(List<WebElement> elementlist, String elementtext) {
        WebElement element = null;
        for (WebElement elem : elementlist) {
            if (elem.getText().equalsIgnoreCase(elementtext.trim())) {
                element = elem;
            }
        }
        // FIXME: handle if no element with the text is found in list
        if (element == null) {
        }
        return element;
    }

    //get element from the list based on the contained text
    protected WebElement getElementByContainsText(List<WebElement> elementlist, String elementtext) {
        WebElement element = null;
        for (WebElement elem : elementlist) {
            if (elem.getText().contains(elementtext.trim())) {
                element = elem;
            }
        }
        // FIXME: handle if no element with the text is found in list
        if (element == null) {
        }
        return element;
    }

    //switch frame
    protected void switchToFrame(WebElement element) {
        wait.waitForElementToBeVisible(element);
        driver.switchTo().frame(element);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
    
    protected void changeWindow(int i) {
        hardWait(1);
        Set<String> windows = driver.getWindowHandles();
        if (i > 0) {
            for (int j = 0; j < 5; j++) {
                System.out.println("Windows: " + windows.size());
                if (windows.size() >= 2) {
                    try {
                        Thread.sleep(5000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                windows = driver.getWindowHandles();
            }
        }
        String wins[] = windows.toArray(new String[windows.size()]);
        driver.switchTo().window(wins[i]);
        hardWait(1);
        System.out.println("Title: " + driver.switchTo().window(wins[i]).getTitle());
    }

    //execute java script code if required
    protected void executeJavascript(String script) {
        ((JavascriptExecutor) driver).executeScript(script);
    }
    
    protected void clickElementWithJavascript(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",element);
    }

    // function for mouse hover on any web element
    protected void hover(WebElement element) {
        Actions hoverOver = new Actions(driver);
        hoverOver.moveToElement(element).build().perform();
    }

    // hard Wait option : Should not be used unless no workaround is there
    protected void hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    // scroll to the element in the page
    protected void scrollDown(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", element);
    }

    //mouse hover and click to any web element
    protected void hoverClick(WebElement element) {
        Actions hoverClick = new Actions(driver);
        hoverClick.moveToElement(element).click().build().perform();
    }

    //click to element: handled wait and stale reference exception for element
    public void click(WebElement element) {
        try {
            wait.waitForElementToBeVisible(element);
            //scrollDown(element);
            element.click();
        } catch (StaleElementReferenceException ex1) {
            wait.waitForElementToBeVisible(element);
            scrollDown(element);
            element.click();
            logMessage("Clicked Element " + element + " after catching Stale Element Exception");
        } catch (Exception ex2) {
            logMessage("Element " + element + " could not be clicked! " + ex2.getMessage());
        }
    }
    
    // select value from drop down based on the value
    protected void selectByValue(WebElement element,String value) {
    	Select select = new Select(element);
    	select.selectByValue(value);
    	logMessage(value + " selected from dropdown");
    }
    
    // select value from drop down based on the index
    protected String selectByIndex(WebElement element,int index) {
    	Select select = new Select(element);
    	select.selectByIndex(index);
    	logMessage(index + " index selected from dropdown");
    	return select.getFirstSelectedOption().getText();
    }
   
 // select value from drop down based on the textß
    protected void selectByVisibleText(WebElement element,String text) {
    	Select select = new Select(element);
    	select.selectByVisibleText(text);
    	logMessage(text + " selected from dropdown");
    }
    
    protected String getSelectedValue(WebElement element){
    	Select select = new Select(element);
    	return select.getFirstSelectedOption().getAttribute("value");
    	
    }
    
    protected void closeWindow() {
        driver.close();
    }
  
}