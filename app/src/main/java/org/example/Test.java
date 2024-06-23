package org.example;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class Test {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "AndroidTestDevice");
        capabilities.setCapability("appium:platformVersion", "14.0");
        capabilities.setCapability("appium:automationName", "UIAutomator2");
        capabilities.setCapability("appium:appPackage", "org.wikipedia");
        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/vladimir/IdeaProjects/MobileQAAutomationCourse/app/src/main/resources/apks/wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @org.junit.Test
    public void test() {
        // Skip onboarding
        waitForElementAndClick(By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find 'Skip' button",
                5);
        // Check that search field is displayed and click on it
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        // Check that search input is displayed and type 'Flutter'
        waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"),
                "Flutter", "Cannot find search input",
                5
        );

        // Check that search results are displayed
        waitForElementPresent(By.xpath("//*[contains(@text, 'Open-source UI software development kit for cross-platform applications')]"),
                "Cannot find 'Software development kit' topic",
                15
        );
        waitForElementPresent(By.xpath("//*[contains(@text, 'Species of bird')]"),
                "Cannot find 'Species of bird' topic",
                15
        );
        waitForElementPresent(By.xpath("//*[contains(@text, 'American fintech company')]"),
                "Cannot find 'American fintech company' topic",
                15
        );
        // If you uncomment the following 4 lines, the test will fail
//            waitForElementPresent(By.xpath("//*[contains(@text, 'Huiyuan')]"),
//                    "Cannot find 'Huiyuan' topic",
//                    15
//            );

        // Go to home page
        waitForElementAndClick(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"),
                "Cannot find 'Navigate up' button",
                5
        );

        // Check that search results are not displayed
        waitForElementNotPresent(By.xpath("//*[contains(@text, 'Open-source UI software development kit for cross-platform applications')]"),
                "Cannot find 'Software development kit' topic",
                15
        );
        waitForElementNotPresent(By.xpath("//*[contains(@text, 'Species of bird')]"),
                "Cannot find 'Species of bird' topic",
                15
        );
        waitForElementNotPresent(By.xpath("//*[contains(@text, 'American fintech company')]"),
                "Cannot find 'American fintech company' topic",
                15
        );
    }

    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement webElement = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        webElement.click();
        return webElement;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
        WebElement webElement = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        webElement.sendKeys(value);
        return webElement;
    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement webElement = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        webElement.clear();
        return webElement;
    }
}