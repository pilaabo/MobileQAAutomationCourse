package org.example;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.time.Duration;
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
    public void assertElementPresent() {
        // Объявление констант
        final String SKIP_BUTTON_ID = "org.wikipedia:id/fragment_onboarding_skip_button";
        final String SEARCH_CONTAINER_ID = "org.wikipedia:id/search_container";
        final String SEARCH_INPUT_ID = "org.wikipedia:id/search_src_text";
        final String IOS_ARTICLE_XPATH = "//*[contains(@text, 'Mobile operating system by Apple')]";
        final String IOS_TITLE_XPATH = "//*[@text='iOS']";

        // Пропуск онбординга
        waitForElementAndClick(By.id(SKIP_BUTTON_ID),
                "Cannot find 'Skip' button",
                5
        );

        // Клик на поисковой строке
        waitForElementAndClick(By.id(SEARCH_CONTAINER_ID),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        // Ввод текста в поисковую строку
        waitForElementAndSendKeys(By.id(SEARCH_INPUT_ID),
                "iOS", "Cannot find search input",
                5
        );

        // Переход к статье
        waitForElementAndClick(By.xpath(IOS_ARTICLE_XPATH),
                "Cannot find 'iOS' article",
                5
        );

        // Проверка наличия заголовка статьи
        Assert.assertNotNull(
                "Cannot find article title",
                driver.findElement(By.xpath(IOS_TITLE_XPATH))
        );
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

    protected void swipe(int xStart, int yStart, int xEnd, int yEnd, Duration duration) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), xStart, yStart));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), xEnd, yEnd));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        this.driver.perform(List.of(swipe));
    }
}
