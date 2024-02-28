package ru.dns_shop;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BaseTest {

    @BeforeAll
    public static void setupClass() {
        Configuration.browser = "chrome";
        open();
        getWebDriver().manage().window().maximize();
    }

    @AfterAll
    public static void quitTest() {
        closeWebDriver();
    }
}