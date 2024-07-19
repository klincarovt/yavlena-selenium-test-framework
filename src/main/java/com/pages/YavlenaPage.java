package com.pages;

import com.locators.YavlenaLocators;
import com.pages.basePage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class YavlenaPage extends BasePage {
    private WebDriver webDriver;

    public YavlenaPage(WebDriver webDriver){
        super(webDriver);
        this.webDriver = webDriver;
    }
    @Override
    public YavlenaPage newInstance(WebDriver webDriver) { return new YavlenaPage(webDriver); }

    @FindBy(xpath = YavlenaLocators.UNDERSTOOD_BUTTON_XPATH)
    WebElement understoodCookiesButton;

    @FindBy(id = YavlenaLocators.KEYWORD_SEARCH_ID)
    WebElement keywordSearchId;

    @FindBy(xpath = YavlenaLocators.BROKER_DETAILS_BUTTON_XPATH)
    WebElement brokerDetailsButton;

    @FindBy(xpath = YavlenaLocators.BROKER_CLEAR_BUTTON_XPATH)
    WebElement clearButton;

    public void closeCookies() {
        try {
            WebElement understoodCookiesButton = this.getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Understood']")));
            if (understoodCookiesButton != null && understoodCookiesButton.isDisplayed() && understoodCookiesButton.isEnabled()) {
                understoodCookiesButton.click();
            }
        } catch (Exception e) {
            System.out.println("Cookie consent button not found or not clickable, proceeding without clicking.");
        }
    }

    public void clickClearButton(){
        waitForElementToBeClickableAndClick(clearButton);
    }

    public void waitForPageLoad() {
        new WebDriverWait(webDriver, Duration.ofSeconds(20)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }

    public void searchByName(String name){
        //clickClearButton();
        waitForPageLoad();
        webDriver.findElement(By.xpath(YavlenaLocators.BROKER_CLEAR_BUTTON_XPATH)).click();
        waitForPageLoad();
        webDriver.findElement(By.id(YavlenaLocators.KEYWORD_SEARCH_ID)).sendKeys(name);
    }

    public void clickOnDetails(){
        waitForElementToBeClickableAndClick(brokerDetailsButton);
    }

    public void scrollToBottomOfPage(){
        JavascriptExecutor js = (JavascriptExecutor) this.webDriver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public List<String> getAllBrokers() {
        scrollToBottomOfPage();
        List<WebElement> brokerElements = waitAndFindElementsByLocator(By.xpath(YavlenaLocators.BROKER_NAME_ELEMENTS_XPATH));
        return brokerElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void clickAllUserDetails(List<String> allBrokers){
        allBrokers.forEach(name ->{
            this.searchByName(name);
            this.clickOnDetails();
        });
    }

    public void print(List<WebElement> webElements) {
        webElements.forEach(element -> System.out.println(element.getText()));
        System.out.println(webElements.size());
    }
}
