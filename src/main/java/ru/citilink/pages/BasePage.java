package ru.citilink.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class BasePage {
    static final Duration WAITING_TIME = Duration.ofSeconds(20);

    public void jsClick(SelenideElement selenideElement) {
        selenideElement.should(visible, WAITING_TIME);
        executeJavaScript("arguments[0].click();", selenideElement);
    }
}