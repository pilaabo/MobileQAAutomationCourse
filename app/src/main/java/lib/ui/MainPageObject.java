package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver) {
        this.driver = driver;
    }

    public int getElementsSize(By locator) {
        return waitForElements(locator, "Can't find element with locator " + locator, 15).size();

    }

    public List<WebElement> getElementsBy(By locator) {
        return waitForElements(locator, "Elements are not found", 10);
    }

    public List<String> getElementsText(List<WebElement> elements) {
        return elements.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    private List<WebElement> waitForElements(By locator, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public void assertElementHasText(By locator, String expectedText, String errorMessage) {
        WebElement element = waitForElementPresent(locator, "Can't find element with locator " + locator);
        assertEquals(errorMessage, expectedText, element.getText());
    }

    public WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(By by, String text, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(text);
        return element;
    }

    public WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;
    }

    public WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(By by, String errorMessage) {
        return waitForElementPresent(by, errorMessage, 5);
    }

    public boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public void swipeUp(Duration duration) {
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        swipe(new Point(x, startY), new Point(x, endY), duration);
    }

    public void swipeUpQuick() {
        swipeUp(Duration.ofMillis(200));
    }

    public void swipeUpToFindElement(By by, String errorMessage, int maxSwipes) {
        int alreadySwiped = 0;
        while (driver.findElements(by).isEmpty()) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public void swipeElementToLeft(String locator_with_type, String error_message) {

        WebElement element = waitForElementPresent(By.id(locator_with_type), error_message, 10);

        Point location = element.getLocation();
        Dimension size = element.getSize();

        int left_x = location.getX();
        int right_x = left_x + size.getWidth();
        int upper_y = location.getY();
        int lower_y = upper_y + size.getHeight();
        int middle_y = upper_y + (size.getHeight() / 2);

        int start_x = right_x - 20;
        int end_x = left_x + 20;
        int start_y = middle_y;
        int end_y = middle_y;

        this.swipe(
                new Point(start_x, start_y),
                new Point(end_x, end_y),
                Duration.ofMillis(550)
        );
    }

    protected void swipe(Point start, Point end, Duration duration) {

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start.x, start.y));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), end.x, end.y));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        this.driver.perform(Arrays.asList(swipe));
    }

    public int getAmountOfElements(By by) {
        return driver.findElements(by).size();
    }

    public String waitAndGetAttribute(By by, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public String waitAndGetText(By by, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        return element.getText();
    }

//    public void longPress(By by) {
//        WebElement element = waitForElementPresent(by, "Can't find element with locator " + by.toString(), 10);
//        TouchAction action = new TouchAction(driver);
//        action.longPress(element);
//        action.perform();
//    }

    public void assertElementNotPresent(By by, String errorMessage) {
        if (getAmountOfElements(by) > 0) {
            String defaultMessage = String.format("An element '%s' supposed to be not present", by.toString());
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public void assertElementPresent(By by, String errorMessage) {
        if (getAmountOfElements(by) == 0) {
            String defaultMessage = String.format("An element '%s' supposed to be present.", by.toString());
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }
}
