package ru.dns_shop;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.dns_shop.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {

    @BeforeAll
    public static void installTestConfigurations() {
        Configuration.browser = new ConfProperties().getProperty("browser-name");
        Configuration.browserSize = null;
        DesiredCapabilities cap = new DesiredCapabilities();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        Configuration.browserCapabilities = cap;
    }

    @AfterAll
    public static void quitTest() {
        closeWebDriver();
    }
}