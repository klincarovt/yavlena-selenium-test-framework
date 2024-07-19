package com.utils;

import com.enums.DriverType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

import javax.management.RuntimeErrorException;
import java.util.logging.Level;

public class DriverFactory {
    
    public static WebDriver createWebDriverForBrowserWithValue(DriverType driverType) throws RuntimeErrorException{
        LoggingPreferences loggingPreferences = new LoggingPreferences();
        loggingPreferences.enable(LogType.PERFORMANCE, Level.WARNING);
        loggingPreferences.enable(LogType.BROWSER, Level.WARNING);
        loggingPreferences.enable(LogType.CLIENT, Level.WARNING);
        loggingPreferences.enable(LogType.DRIVER, Level.WARNING);
        loggingPreferences.enable(LogType.PROFILER, Level.WARNING);

        WebDriver webDriver = null;
        ChromeOptions chromeOptions = null;
        FirefoxOptions firefoxOptions = null;

        switch (driverType){
            case GOOGLE_CHROME:
                chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("start-maximized");
                chromeOptions.addArguments("disable-infobars");
                chromeOptions.addArguments("incognito");
                chromeOptions.addArguments("disable-extensions");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                chromeOptions.setCapability("goog:loggingPrefs", loggingPreferences);
                webDriver = new ChromeDriver(chromeOptions);
                break;
            case GOOGLE_CHROME_HEADLESS:
                chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("window-size=1920x1080");
                chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("incognito");
                chromeOptions.addArguments("disable-extensions");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                chromeOptions.setCapability("goog:loggingPrefs", loggingPreferences);
                webDriver = new ChromeDriver(chromeOptions);
                break;
            case FIREFOX:
                firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                firefoxOptions.addArguments("--disable-infobars");
                firefoxOptions.addArguments("--private");
                firefoxOptions.addArguments("--disable-extensions");
                firefoxOptions.addArguments("--no-sandbox");
                firefoxOptions.addArguments("--disable-gpu");
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

                webDriver = new FirefoxDriver(firefoxOptions);
            
                break;
            case FIREFOX_HEADLESS:
                firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--private");
                firefoxOptions.addArguments("--disable-extensions");
                firefoxOptions.addArguments("--no-sandbox");
                firefoxOptions.addArguments("--disable-gpu");
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                webDriver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                throw new RuntimeErrorException(null,
                "Desired Web Driver is not configured, and Web Driver will not be initialized");
        }

        return webDriver;

    }
    
}
