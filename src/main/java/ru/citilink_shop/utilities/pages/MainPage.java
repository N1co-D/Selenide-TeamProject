package ru.citilink_shop.utilities.pages;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final String productSearchInputLine = "//input[@type='search']";
    private static final long SECONDS_OF_WAITING = 15;

    public void enterProductSearchInputLine(String nameProduct) {
        $x(productSearchInputLine)
                .should(visible, Duration.ofSeconds(SECONDS_OF_WAITING))
                .val(nameProduct).pressEnter();
    }
}