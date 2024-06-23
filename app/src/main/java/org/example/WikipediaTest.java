package org.example;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class WikipediaTest {
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

    @Test
    public void test() {
        // Пропуск онбординга
        waitForElementAndClick(By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find 'Skip' button",
                5);
        // Поиск поиска
        waitForElementAndClick(By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        // Ввод текста в поиск
        waitForElementAndSendKeys(By.id("org.wikipedia:id/search_src_text"),
                "Flutter", "Cannot find search input",
                5
        );
        // Проверка результатов поиска
        verifySearchResultsContain("Flutter");
    }

    private void verifySearchResultsContain(String query) {
        List<WebElement> results = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        Assert.assertFalse("No search results found!", results.isEmpty());

        for (WebElement result : results) {
            String resultText = result.getText().toLowerCase();
            Assert.assertTrue(
                    "Search result does not contain query: " + query,
                    resultText.contains(query.toLowerCase())
            );
        }
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
