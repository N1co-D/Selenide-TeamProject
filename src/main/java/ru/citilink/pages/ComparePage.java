package ru.citilink.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ComparePage extends BasePage {
    private final String comparePageTitle = "//div[@class='ComparePage__header']//h2[text()]";
    private final String productTitle = "//div[normalize-space(text())='Модель']/following-sibling::div//a";
    private final String productPrice = "//span[contains(@class,'Compare__product-cell_price_current-price')]";

    public String getComparePageTitle() {
        return $x(comparePageTitle).shouldBe(visible, WAITING_TIME).getText();
    }

    public String getProductTitle() {
        return $x(productTitle).shouldBe(visible, WAITING_TIME).getText();
    }

    public String getProductPrice() {
        return $x(productPrice).shouldBe(visible, WAITING_TIME).getText();
    }
}