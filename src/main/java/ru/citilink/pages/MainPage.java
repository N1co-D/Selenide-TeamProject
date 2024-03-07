package ru.citilink.pages;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final String popularCategoryTile = "//div[contains(@data-meta-name,'category-tiles')]//a//span[contains(text(),'%s')]";
    private static final long SECONDS_OF_WAITING = 15;

    public void clickPopularCategoryTile(String nameCategory) {
        $x(String.format(popularCategoryTile, nameCategory))
                .scrollIntoView("{behavior: \"smooth\", block: \"center\", inline: \"nearest\"}")
                .should(visible, Duration.ofSeconds(SECONDS_OF_WAITING))
                .click();
    }
}