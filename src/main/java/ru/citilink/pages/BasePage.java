package ru.citilink.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class BasePage {
    static final int DURATION = 20;

    public void jsClick(SelenideElement selenideElement) {
        executeJavaScript("arguments[0].click();", selenideElement);
    }
}