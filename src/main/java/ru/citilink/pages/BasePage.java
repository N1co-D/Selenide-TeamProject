package ru.citilink.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class BasePage {
    public static final int SECONDS_OF_WAITING = 20;

    public static void jsClick(SelenideElement selenideElement) {
        executeJavaScript("arguments[0].click();", selenideElement);
    }
}