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
    public void wikiArticleManagementTest() {
        // Объявление констант
        final String SKIP_BUTTON_ID = "org.wikipedia:id/fragment_onboarding_skip_button";
        final String SEARCH_CONTAINER_ID = "org.wikipedia:id/search_container";
        final String SEARCH_INPUT_ID = "org.wikipedia:id/search_src_text";
        final String IOS_ARTICLE_XPATH = "//*[contains(@text, 'Mobile operating system by Apple')]";
        final String SAVE_BUTTON_ID = "org.wikipedia:id/page_save";
        final String ADD_TO_LIST_BUTTON_ID = "org.wikipedia:id/snackbar_action";
        final String LIST_NAME_INPUT_ID = "org.wikipedia:id/text_input";
        final String OK_BUTTON_ID = "android:id/button1";
        final String SEARCH_BUTTON_ID = "org.wikipedia:id/page_toolbar_button_search";
        final String ANDROID_ARTICLE_XPATH = "//*[contains(@text, 'Mobile operating system')]";
        final String NAVIGATE_UP_BUTTON_XPATH = "//android.widget.ImageButton[@content-desc='Navigate up']";
        final String READING_LISTS_TAB_ID = "org.wikipedia:id/nav_tab_reading_lists";
        final String LIST_XPATH = "//*[contains(@text, 'Mobile OS')]";
        final String ANDROID_ARTICLE_IN_LIST_XPATH = "//*[contains(@text, 'Android (operating system)')]";
        final String IOS_ARTICLE_IN_LIST_XPATH = "//*[contains(@text, 'iOS')]";
        final String IOS_ARTICLE_TITLE_XPATH = "//*[@text='iOS']";

        // Пропуск онбординга
        waitForElementAndClick(By.id(SKIP_BUTTON_ID),
                "Cannot find 'Skip' button",
                5);

        // Клик на поисковой строке
        waitForElementAndClick(By.id(SEARCH_CONTAINER_ID),
                "Cannot find 'Search Wikipedia' input",
                5);

        // Ввод текста в поисковую строку
        waitForElementAndSendKeys(By.id(SEARCH_INPUT_ID),
                "iOS", "Cannot find search input",
                5);

        // Переход к статье
        waitForElementAndClick(By.xpath(IOS_ARTICLE_XPATH),
                "Cannot find 'iOS' article",
                5);

        // Добавление статьи в сохраненные
        waitForElementAndClick(By.id(SAVE_BUTTON_ID),
                "Cannot find 'Save' button",
                5);

        // Переход к окну создания нового списка
        waitForElementAndClick(By.id(ADD_TO_LIST_BUTTON_ID),
                "Cannot find 'Add to list' button",
                5);

        // Ввод названия нового списка
        waitForElementAndSendKeys(By.id(LIST_NAME_INPUT_ID),
                "Mobile OS", "Cannot find input for list name",
                5);

        // Подтверждение создания списка
        waitForElementAndClick(By.id(OK_BUTTON_ID),
                "Cannot find 'OK' button",
                5);

        // Клик на поисковой строке снова
        waitForElementAndClick(By.id(SEARCH_BUTTON_ID),
                "Cannot find 'Search Wikipedia' input",
                5);

        // Ввод текста в поисковую строку
        waitForElementAndSendKeys(By.id(SEARCH_INPUT_ID), "Android",
                "Cannot find search input",
                5);

        // Переход к статье
        waitForElementAndClick(By.xpath(ANDROID_ARTICLE_XPATH),
                "Cannot find 'Android' article",
                5);

        // Добавление статьи в сохраненные
        waitForElementAndClick(By.id(SAVE_BUTTON_ID),
                "Cannot find 'Save' button",
                5);

        // Переход к добавлению статьи в список
        waitForElementAndClick(By.id(ADD_TO_LIST_BUTTON_ID),
                "Cannot find 'Add to list' button",
                5);

        // Выбор списка
        waitForElementAndClick(By.xpath(LIST_XPATH),
                "Cannot find 'Mobile OS' list",
                5);

        // Возврат назад (3 раза)
        for (int i = 0; i < 3; i++) {
            waitForElementAndClick(By.xpath(NAVIGATE_UP_BUTTON_XPATH),
                    "Cannot find 'Navigate up' button",
                    5);
        }

        // Переход к сохраненным статьям
        waitForElementAndClick(By.id(READING_LISTS_TAB_ID),
                "Cannot find 'Reading Lists' tab",
                5);

        // Переход к списку
        waitForElementAndClick(By.xpath(LIST_XPATH),
                "Cannot find 'Mobile OS' list",
                5);

        // Получение объекта статьи
        WebElement article = waitForElementPresent(By.xpath(ANDROID_ARTICLE_IN_LIST_XPATH),
                "Cannot find 'Android' article in list",
                5);

        // Удаление статьи из списка через свайп влево
        int left_x = article.getLocation().getX();
        int right_x = left_x + article.getSize().getWidth();
        int upper_y = article.getLocation().getY();
        int lower_y = upper_y + article.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        swipe(right_x - 100, middle_y, left_x + 100, middle_y, Duration.ofMillis(300));

        // Проверка, что вторая статья в списке - это статья "iOS"
        waitForElementPresent(By.xpath(IOS_ARTICLE_IN_LIST_XPATH),
                "Cannot find 'iOS' article in list",
                5);

        // Переход к статье "iOS"
        waitForElementAndClick(By.xpath(IOS_ARTICLE_IN_LIST_XPATH),
                "Cannot find 'iOS' article",
                5);

        // Проверка, что заголовок статьи "iOS"
        WebElement title = waitForElementPresent(By.xpath(IOS_ARTICLE_TITLE_XPATH),
                "Cannot find 'iOS' article title",
                5);
        Assert.assertEquals("Article title does not match", "iOS", title.getText());
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
