package ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class SearchPageObject extends MainPageObject {
    private static final String SEARCH_INIT_ELEMENT = "org.wikipedia:id/search_container";
    private static final String SEARCH_INPUT = "org.wikipedia:id/search_src_text";
    public static final String SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void initSearchInput() {
        this.waitForElementAndClick(
                By.id(SEARCH_INIT_ELEMENT),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        this.waitForElementPresent(
                By.id(SEARCH_INPUT),
                "Cannot find search input",
                5
        );
    }

    public void typeSearchLine(String searchLine) {
        this.waitForElementAndSendKeys(
                By.id(SEARCH_INPUT),
                searchLine,
                "Cannot find search input",
                5
        );
    }

    public void waitForSearchResult(String substring) {
        this.waitForElementPresent(
                By.xpath("//*[contains(@text, '" + substring + "')]"),
                "Cannot find search result with substring " + substring,
                15
        );
    }

    public void waitForCancelButtonToAppear() {
        this.waitForElementPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find 'X' to cancel search",
                5
        );
    }

    public void clickCancelSearch() {
        this.waitForElementAndClick(
                By.id(SEARCH_CANCEL_BUTTON),
                "Cannot find 'X' to cancel search",
                5
        );
    }

    public void waitForCancelButtonToDisappear() {
        this.waitForElementNotPresent(
                By.id(SEARCH_CANCEL_BUTTON),
                "'X' is still present on the page",
                5
        );
    }
}
