package ru.citilink.pages;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ComparePage {
    private final String comparePageTitle = "//div[@class='ComparePage__header']//h2[text()]";
    private final String titleOfCurrentProduct = "//div[normalize-space(text())='Модель']/following-sibling::div//a";
    private final String priceOfCurrentProduct = "//span[contains(@class,'Compare__product-cell_price_current-price')]";
    private static final long SECONDS_OF_WAITING = 15;

    public String getComparePageTitle() {
        return $x(comparePageTitle).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).getText();
    }

    public String getTitleOfCurrentProduct() {
        return $x(titleOfCurrentProduct).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).getText();
    }

    public String getPriceOfCurrentProduct() {
        return $x(priceOfCurrentProduct).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).getText();
    }
}