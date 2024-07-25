import lib.CoreTestCase;
import org.openqa.selenium.By;
import ui.MainPageObject;

public class FirstTest extends CoreTestCase {
    private MainPageObject mainPageObject;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainPageObject = new MainPageObject(driver);
    }

    public void testOne() {
        mainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/fragment_onboarding_skip_button"),
                "Cannot find 'Skip' button",
                5
        );
    }
}
