package ru.dns_shop.citilink;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CatalogPage {
    private final String subcategoryPageTitle = "//div[@data-meta-name='SubcategoryPageTitle']/h1[text()]";
    private final String productList = "//div[@data-meta-row-count='48']/div";
    private final String comparingCurrentProductButton = ".//button[@data-meta-name='Snippet__compare-button']";
    private final String detailedCatalogModeButton = "//label[@for='Подробный режим каталога-list']";
    private static final long SECONDS_OF_WAITING = 15;

    public String getSubcategoryPageTitle() {
        return $x(subcategoryPageTitle).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).getText();
    }

    public void comparingCurrentProductButtonClick(String nameData) {
        executeJavaScript("arguments[0].click()",
                $$x(productList).shouldBe(sizeGreaterThan(0), Duration.ofSeconds(SECONDS_OF_WAITING))
                        .findBy(text(nameData)).$x(comparingCurrentProductButton));
    }

    public CatalogPage detailedCatalogModeButtonClick() {
        $x(detailedCatalogModeButton).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).click();
        return this;
    }
}