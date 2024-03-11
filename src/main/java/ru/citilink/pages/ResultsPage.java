package ru.citilink.pages;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ResultsPage {
    private final String subcategoryPageTitle = "//div[@data-meta-name='SubcategoryPageTitle']/h1[text()]";
    private final String productList = "//div[@data-meta-name='ProductHorizontalSnippet']";
    private final String comparingCurrentProductButton = ".//button[@data-meta-name='Snippet__compare-button']";
    private final String priceOfCurrentProduct = ".//span[@data-meta-price]/span[1]";
    private final String detailedCatalogModeButton = "//label[@for='Подробный режим каталога-list']";
    private static final long SECONDS_OF_WAITING = 15;

    public String getSubcategoryPageTitle() {
        return $x(subcategoryPageTitle).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).getText();
    }

    public String getPriceOfCurrentProduct(String nameData) {
        return $$x(productList).shouldBe(sizeGreaterThan(0), Duration.ofSeconds(SECONDS_OF_WAITING))
                .findBy(text(nameData)).$x(priceOfCurrentProduct).getText();
    }

    public void comparingCurrentProductButtonClick(String nameData) {
        executeJavaScript("arguments[0].click()",
                $$x(productList).shouldBe(sizeGreaterThan(0), Duration.ofSeconds(SECONDS_OF_WAITING))
                        .findBy(text(nameData)).$x(comparingCurrentProductButton));
    }

    public ResultsPage detailedCatalogModeButtonClick() {
        $x(detailedCatalogModeButton).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).click();
        return this;
    }
}