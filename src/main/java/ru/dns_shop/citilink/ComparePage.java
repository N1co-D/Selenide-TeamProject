package ru.dns_shop.citilink;

import java.time.Duration;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ComparePage {
    private final String comparePageTitle = "//div[@class='ComparePage__header']//h2[text()]";
    private final String titleOfCurrentProduct = "//div[normalize-space(text())='Модель']/following-sibling::div//a";
    private final String deleteCurrentProductButton = "//div[@class='Compare__product-row']//div[@class='Compare__image-wrapper']/button";
    private final String productsEmpty = "//div[normalize-space(text())='Нет товаров для сравнения' and @style='display: block;']";
    private final String compareValue = "//div[contains(@class, 'HeaderMenu__count') and text()]";
    private static final long SECONDS_OF_WAITING = 15;

    public String getComparePageTitle() {
        return $x(comparePageTitle).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).getText();
    }

    public String getTitleOfCurrentProduct() {
        return $x(titleOfCurrentProduct).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).getText();
    }

    public void deleteCurrentProductButtonClick() {
        $x(deleteCurrentProductButton).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).click();
    }

    public boolean productsEmptyIsDisplayed() {
        return $x(productsEmpty).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).isDisplayed();
    }

    public boolean compareValueIsDisplayed() {
        return $x(compareValue).should(not(visible), Duration.ofSeconds(SECONDS_OF_WAITING)).isDisplayed();
    }
}