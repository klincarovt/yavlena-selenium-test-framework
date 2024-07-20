### Notes

### Challenges

Generally while developing this framework i faced two major problems.
One of which was getting all brokers to iterate over them and the other was searching through them.

#### Issue 1 - Getting all brokers

When first visiting the page i noticed how to get all brokers they had unique "//h6" element across whole page.
Then while scrolling through the page and while using the locator i noticed that more brokers appear. So i had to find a
way to load all brokers before i retrieve them. **(Sometimes even when manual scrolling i wasn't able to load all brokers 
i needed to be persistent and do continuous scrolls of up and down to trigger brokers loading)**

- Approach 1 (Failed) - Initially i tried scrolling down the page but i noticed that this might load one set of brokers not all of them
- Approach 2 (Failed) - Using chatgpt i improved on this method with scrolling smaller amounts (height) to end of page with several waits,
however the page would sometimes load and sometimes not load, so i wasn't getting all brokers again.

**Solution** to this was my final approach where during exploratory testing and the developer tools of my browser i took a
look at the network tab and realized that the page was doing GET REQUESTS when i was scrolling and it called the same URL
with different page number **"&page=2"** and that's how additional brokers were added. So my solution incorporates a method that
goes to every page until i get the error message **"No search results found. Please, expand search criteria"** then i go to 
**page=currentPageNumber - 1** this leads me to last page, and by scrolling up and down on it i manage to load all brokers. 

#### Issue 2 - Searching through each individual broker

The issue appeared when i initially tried to search more than one broker, when searching the using the search tab the page
automatically added query string to the url, and when trying to clear the search field using selenium methods or javascript
executors the element was not clearing because the query string from the url was repopulating it again when trying to enter
another name. 

- Approach 1 (Failed) was unable to clear the field with usual selenium capabilities
- Approach 2 (Solvable but not used) i tried clearing the search by clicking the clear button provided by the application.
While this was clearing the search field it made my elements stale so i got the StaleElement exception. Can probably be solved
with retrying to find elements.
- Approach 3 (Not used) similar to how i cycled through the pages i managed to search brokers by appending the keyword query
string to the url with their name concatenated like **Name+Surname** however i did not like this solution, and by reading 
the assignment i understood the approach might not be what was asked for. **However this seems like the faster solution
than the one what i went for.**

**Solution** in the end consisted of a few things. First i had to implement a wait to the search element when loading the
page, then enter the name. If this didn't initially work i encapsulated it in a try catch block to retry if a StaleElement
exception was caught (This was needed due to navigating back after searching the first broker).
Second after entering the name i needed to add another wait until only one broker was visible, because of how fast selenium
did this the other brokers might not always disappear before trying to click details for broker. **(This is why the Approach 3
is faster, loading a page by url is a lot faster than searching through page).** Third after i assert broker 
i navigate back to clear the search from the query string (Used this because sometimes elements were getting StaleElement exception). 


#### General Overview
````
src/main/java/com/assertions
    /YavlenaPageAssertions.java- Contains methods with general assertions for every web page.

src/main/java/com/enums
    /DriverType.java - Enumerates the Drivers (Chrome, Firefox etc.)
    
src/main/java/com/locators
    YavlenaLocators.java - Locators for specific web page
    
src/main/java/com/pages
    /basePage/BasePage.java - This is an abstract class called BasePage that serves as a foundation for other page classes.
    /YavlenaPage.java - Page containing methods specific to one page. 

src/main/java/com/utils
    /DriverFactory.java - This is where the drivers are being initialized, based on the DriverTypeEnum ex. Chrome creates an instance of chrome and returns it.
    /PropertiesReader.java - This is a class / util that reads from the properties file and assigns urls, settings and driver on running the framework.
    /SplitterUtility.java - (Not used 2nd Implementation) Utility to split and concatinate string for searching with url Name+Surname 

src/test/java/com/test
    /TestBrokerDetails.java - Tests for getting all brokers and asserting their details.
````

To run tests:
``mvn clean test``

Versions

```
mvn -v
Apache Maven 3.9.1 (2e178502fcdbffc201671fb2537d0cb4b4cc58f8)
Maven home: C:\Program Files (x86)\Maven\apache-maven-3.9.1
Java version: 20.0.1, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-20
Default locale: en_US, platform encoding: UTF-8
OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
```

```
java -version
java version "20.0.1" 2023-04-18
Java(TM) SE Runtime Environment (build 20.0.1+9-29)
Java HotSpot(TM) 64-Bit Server VM (build 20.0.1+9-29, mixed mode, sharing)
```

Properties
```
base.url = https://www.yavlena.com/en/broker?city=Sofia
browser.type=CHROME

# Other Browser options
browser.type.CHROME=CHROME
browser.type.CHROME_HEALDESS=CHROME_HEADLESS
browser.type.FIREFOX=FIREFOX
browser.type.FIREFOX_HEADLESS=FIREFOX_HEADLESS
```