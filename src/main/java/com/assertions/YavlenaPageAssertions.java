package com.assertions;

import org.openqa.selenium.WebElement;
import org.testng.asserts.Assertion;

import java.util.List;

public class YavlenaPageAssertions {
    Assertion assertion = new Assertion();

    public void checkIfWebElementsAreDisplayed(List<WebElement> webElementList) {
        for (WebElement element : webElementList) {
            assertion.assertTrue(element.isDisplayed(), "Element is not displayed: " + element.getText());
        }
    }
}
