package com;

import com.config.ConfigurationConstants;
import com.enums.DriverType;
import com.pages.YavlenaPage;
import com.utils.DriverFactory;
import com.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class SampleTest {

    static String browser = ".CHROME";
    String baseUrl;
    String driverType;
    WebDriver webDriver;
    YavlenaPage yavlenaPage;

    @BeforeMethod
    void setup(){
        baseUrl = PropertiesReader.readFromProperties
                (ConfigurationConstants.PROPERTIES_PATH, ConfigurationConstants.BASE_URL_PROPERTY);

        driverType = PropertiesReader.readFromProperties(ConfigurationConstants.PROPERTIES_PATH,
                (ConfigurationConstants.DRIVER_TYPE_PROPERTY + browser).toString());

        webDriver = DriverFactory.createWebDriverForBrowserWithValue(DriverType.parse(driverType));

        webDriver.get(baseUrl);
        yavlenaPage = new YavlenaPage(webDriver);
        //yavlenaPage.closeCookies();
    }

    @Test
    void goToPageSample(){
        //yavlenaPage.print(yavlenaPage.getAllBrokers());
        //yavlenaPage.searchByName("Aleksandar Petkov");

        List<String> allBrokers = yavlenaPage.getAllBrokers();
        allBrokers.forEach(name ->{
            yavlenaPage.searchByName(name);
        });
    }

    @AfterMethod
    void close(){
        webDriver.quit();
    }
}
