package com.test;

import com.assertions.YavlenaPageAssertions;
import com.config.ConfigurationConstants;
import com.enums.DriverType;
import com.pages.YavlenaPage;
import com.utils.DriverFactory;
import com.utils.PropertiesReader;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TestBrokerDetails {

    static String browser = ".CHROME";
    String baseUrl;
    String driverType;
    WebDriver webDriver;
    YavlenaPage yavlenaPage;
    YavlenaPageAssertions yavlenaPageAssertions;

    @BeforeMethod
    void setup(){
        baseUrl = PropertiesReader.readFromProperties
                (ConfigurationConstants.PROPERTIES_PATH, ConfigurationConstants.BASE_URL_PROPERTY);

        driverType = PropertiesReader.readFromProperties(ConfigurationConstants.PROPERTIES_PATH,
                (ConfigurationConstants.DRIVER_TYPE_PROPERTY + browser).toString());

        webDriver = DriverFactory.createWebDriverForBrowserWithValue(DriverType.parse(driverType));

        webDriver.get(baseUrl);
        yavlenaPage = new YavlenaPage(webDriver);
        yavlenaPage.closeCookies();
        yavlenaPageAssertions = new YavlenaPageAssertions();
    }

    @Test
    void testBrokersDetailsVisibility() {
        List<String> allBrokers = yavlenaPage.getAllBrokers(baseUrl);
        this.webDriver.get(baseUrl);

        for(int i = 0; i < allBrokers.size(); i++){
            System.out.println(i+1 + ": " +allBrokers.get(i));
            yavlenaPage.searchByBrokerName(allBrokers.get(i));
            yavlenaPage.clickOnDetails();
            yavlenaPageAssertions.checkIfWebElementsAreDisplayed(yavlenaPage.getDetailElements());
            yavlenaPage.navigateBack();
        }

        /*allBrokers.forEach(name ->{
            yavlenaPage.searchByBrokerName(name);
            yavlenaPage.clickOnDetails();
            yavlenaPageAssertions.checkIfWebElementsAreDisplayed(yavlenaPage.getDetailElements());
            yavlenaPage.navigateBack();
        });*/
    }

    @AfterMethod
    void close(){
        try{
            webDriver.quit();
        }catch (StaleElementReferenceException e) {
            System.err.println("StaleElementReferenceException caught while quitting driver, retrying...");
            webDriver.quit();
        } catch (Exception e) {
            System.err.println("An error occurred while quitting driver.");
        }
    }
}
