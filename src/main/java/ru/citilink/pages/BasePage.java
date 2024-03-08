package ru.citilink.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static java.time.Duration.ofSeconds;

public class BasePage {
    static final int DURATION = 20;

    public void jsClick(SelenideElement selenideElement) {
        selenideElement.should(visible, ofSeconds(DURATION));
        executeJavaScript("arguments[0].click();", selenideElement);
    }
}