package ru.citilink_shop.utilities.pages;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final String productSearchInputLine = "//input[@type='search']";
    private final String popularCategoryTile = "//div[contains(@data-meta-name,'category-tiles')]//a//span[contains(text(),'%s')]";

    public void enterProductSearchInputLine(String nameProduct) {
        $x(productSearchInputLine)
                .should(visible, Duration.ofSeconds(2))
                .val(nameProduct).pressEnter();
    }

    public void clickPopularCategoryTile(String nameCategory) {
        $x(String.format(popularCategoryTile, nameCategory))
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .should(visible, Duration.ofSeconds(2))
                .click();

    }
}
