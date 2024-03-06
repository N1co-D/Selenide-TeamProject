package ru.citilink_shop.utilities.pages;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final String productSearchInputLine = "//input[@type='search']";

    public void enterProductSearchInputLine(String nameProduct) {
        $x(productSearchInputLine)
                .should(visible, Duration.ofSeconds(2))
                .val(nameProduct).pressEnter();
    }
}