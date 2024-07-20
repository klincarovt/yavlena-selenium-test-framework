package com.locators;

public class YavlenaLocators {
    public static final String UNDERSTOOD_BUTTON_XPATH = "//button[text()='Understood']";
    public static final String BROKER_NAME_ELEMENTS_XPATH =  "//h6";
    public static final String BROKER_DETAILS_BUTTON_XPATH = "//button[text()='Details']";
    public static final String DETAILS_ROOT_ELEMENT_XPATH =
            "//div[@class='MuiCollapse-root MuiCollapse-vertical MuiCollapse-entered mui-style-c4sutr']//div[@class='MuiCollapse-wrapper MuiCollapse-vertical mui-style-hboir5']//div[@class='MuiCollapse-wrapperInner MuiCollapse-vertical mui-style-8atqhb']";
    public static final String DETAILS_ADDRESS_XPATH =
            "//span[@class='MuiTypography-root MuiTypography-textSmallRegular mui-style-1ya7gnz']";
    public static final String DETAILS_PHONE_NUMBERS_XPATH = "//a[starts-with(@href, 'tel:')]";
    public static final String DETAILS_CONTACT_US_XPATH = "//a[text()='Contact us']";
    public static final String CHiLD_DIV_XPATH = "//div[%s]";
    public static final String PROPERTIES_XPATH = "//a[contains(text(), 'properties')]";
    public static final String KEYWORD_SEARCH_ID = "broker-keyword";
    public static final String BROKER_CLEAR_BUTTON_XPATH = "//button[text()='Clear']";
    public static final String PAGE_END_XPATH = "//h6[contains(text(),'No search results found. Please, expand search criteria!')]";
}
