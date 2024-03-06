package ru.citilink_shop.utilities.pages;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class OrderPage {
    private final String nameProductFromBasketSnippet = "//div[@data-meta-name='BasketSnippet']//span[contains(text(),'%s')]";
    private static final long SECONDS_OF_WAITING = 15;

    public String getNameProductFromBasketSnippet(String nameProduct) {
        return $x(String.format(nameProductFromBasketSnippet, nameProduct))
                .should(visible, Duration.ofSeconds(SECONDS_OF_WAITING))
                .getText();
    }
}