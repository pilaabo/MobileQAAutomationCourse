package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {
    protected AppiumDriver driver;
    private static String appiumURL = "http://127.0.0.1:4723";

    @Override
    public void setUp() throws Exception {
        super.setUp();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:deviceName", "AndroidTestDevice");
        capabilities.setCapability("appium:platformVersion", "14.0");
        capabilities.setCapability("appium:automationName", "UIAutomator2");
        capabilities.setCapability("appium:appPackage", "org.wikipedia");
        capabilities.setCapability("appium:appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/vladimir/IdeaProjects/MobileQAAutomationCourse/app/src/main/resources/apks/wikipedia.apk");

        driver = new AndroidDriver(new URL(appiumURL), capabilities);
    }

    @Override
    public void tearDown() throws Exception {
        driver.quit();
        super.tearDown();
    }
}
