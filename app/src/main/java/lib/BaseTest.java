package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.time.Duration;

public class BaseTest extends TestCase {

    protected static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723";
    protected static final String APP_NAME = "org.wikipedia";
    protected static final String APP_LOCATION = "/Users/vladimir/IdeaProjects/MobileQAAutomationCourse/app/src/main/resources/apks/org.wikipedia.apk";
    protected AppiumDriver driver;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "AndroidTestDevice");
        capabilities.setCapability("appium:platformVersion", "15.0");
        capabilities.setCapability("appium:automationName", "UIAutomator2");
        capabilities.setCapability("appium:appPackage", APP_NAME);
        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("appium:app", APP_LOCATION);
        capabilities.setCapability("noReset", "true");

        driver = new AndroidDriver(new URL(APPIUM_SERVER_URL), capabilities);
        this.rotateScreenPortrait();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        driver.quit();
    }

    protected void rotateScreenPortrait() {
        driver.rotate(ScreenOrientation.PORTRAIT);
    }
    protected void rotateScreenLandscape() {
        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp(int seconds) {
        driver.runAppInBackground(Duration.ofSeconds(seconds));
    }
}
