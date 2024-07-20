package com.pages;

import com.config.ConfigurationConstants;
import com.locators.YavlenaLocators;
import com.pages.basePage.BasePage;
import com.utils.PropertiesReader;
import com.utils.SplitterUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
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

    @FindBy(xpath = YavlenaLocators.BROKER_DETAILS_BUTTON_XPATH)
    WebElement brokerDetailsButton;

    @FindBy(xpath = YavlenaLocators.DETAILS_ROOT_ELEMENT_XPATH)
    WebElement detailsRootElement;

    public void closeCookies() {
        try {
            WebElement understoodCookiesButton = this.getWait().until(ExpectedConditions.
                    presenceOfElementLocated(By.xpath(YavlenaLocators.UNDERSTOOD_BUTTON_XPATH)));
            if (understoodCookiesButton != null && understoodCookiesButton.isDisplayed() && understoodCookiesButton.isEnabled()) {
                understoodCookiesButton.click();
            }
        } catch (Exception e) {
            System.out.println("Cookie consent button not found or not clickable, proceeding without clicking.");
        }
    }

    public void searchByName(String name) {
        try {
            String baseUrl = PropertiesReader.readFromProperties(
                    ConfigurationConstants.PROPERTIES_PATH, ConfigurationConstants.BASE_URL_PROPERTY);
            String searchUrl = baseUrl + "&keyword=" + SplitterUtility.splitAndConcatName(name);
            this.webDriver.get(searchUrl);
            waitForPageLoad();
        } catch (Exception e) {
            System.err.println("An error occurred while searching by name: " + name);
        }
    }

    public void searchByBrokerName(String name){
        try {
            WebElement searchFieldElement = new WebDriverWait(webDriver, Duration.ofNanos(100))
                    .until(ExpectedConditions.presenceOfElementLocated(By.id(YavlenaLocators.KEYWORD_SEARCH_ID)));
            searchFieldElement.sendKeys(name);
            searchFieldElement.sendKeys(Keys.ENTER);
        } catch (StaleElementReferenceException e) {
            System.err.println("StaleElementReferenceException caught, retrying...");
            searchByBrokerName(name);
        } catch (Exception e) {
            System.err.println("An error occurred while searching by broker name: " + name);
        }
    }

    public void navigateBack() {
        this.webDriver.navigate().back();
    }

    public void waitUntilOnlyOneH6ElementIsVisible() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(3));
        wait.until(driver -> {
            List<WebElement> elements = driver.findElements(By.tagName("h6"));
            return elements.size() == 1 && elements.get(0).isDisplayed();
        });
    }

    public void clickOnDetails(){
        waitUntilOnlyOneH6ElementIsVisible();
        waitForElementToBeClickableAndClick(brokerDetailsButton);
    }

    public WebElement getProperties(){
        WebElement webElement = waitAndFindElementByLocator(By.xpath(YavlenaLocators.PROPERTIES_XPATH));
        return webElement;
    }

    public WebElement getAddressElement(){
        WebElement webElement = waitAndFindElement(detailsRootElement, By.xpath(YavlenaLocators.DETAILS_ADDRESS_XPATH));
        return webElement;
    }

    public List<WebElement> getPhoneNumberElements(){
        String selector = YavlenaLocators.DETAILS_ROOT_ELEMENT_XPATH +
                String.format(YavlenaLocators.CHiLD_DIV_XPATH,2)
                + YavlenaLocators.DETAILS_PHONE_NUMBERS_XPATH;
        List<WebElement> phones = waitAndFindElementsByLocator(By.xpath(selector));
        return phones;
    }

    public WebElement getContactElement(){
        WebElement webElement = waitAndFindElement(detailsRootElement, By.xpath(YavlenaLocators.DETAILS_CONTACT_US_XPATH));
        return webElement;
    }

    public List<WebElement> getDetailElements() {
        List<WebElement> list = new ArrayList<>();
        list.add(getProperties());
        list.add(getAddressElement());
        getPhoneNumberElements().forEach(webElement -> list.add(webElement));
        list.add(getContactElement());
        return list;
    }

    public List<String> getAllBrokers(String baseUrl) {
        loadAllPages(baseUrl);
        scrollDownAndUp();
        List<WebElement> brokerElements = waitAndFindElementsByLocator(By.xpath(YavlenaLocators.BROKER_NAME_ELEMENTS_XPATH));
        System.out.println(brokerElements.size());
        return brokerElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void loadAllPages(String baseUrl) {
        int page = 1;
        boolean morePages = true;

        while (morePages) {
            try {
                String url = baseUrl + "&page=" + page;
                this.webDriver.get(url);
                if (isNoResultsFound()) {
                    morePages = false;
                    break;
                }

                page++;
            } catch (Exception e) {
                System.err.println("An error occurred while loading page: " + page);
                morePages = false;
            }
        }

        String lastValidPageUrl = baseUrl + "&page=" + (page - 1);
        this.webDriver.get(lastValidPageUrl);
    }

    private boolean isNoResultsFound() {
        try {
            List<WebElement> noResultsElements = webDriver.findElements(By.xpath(
                    YavlenaLocators.PAGE_END_XPATH));
            return !noResultsElements.isEmpty() && noResultsElements.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private void scrollDownAndUp() {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        js.executeScript("window.scrollTo(0, 0);");
    }
}
