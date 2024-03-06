package ru.citilink_shop.utilities.pages;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ProductPage {
    private final String buttonForAddingItemToBasket = "//a[contains(text(),'%s')]/ancestor::div[contains(@data-meta-name,'ProductVerticalSnippet')]//button[contains(@data-meta-name,'Snippet__cart')]";
    private final String buttonCloseUpSaleBasketLayout = "//div[@data-meta-name='UpsaleBasketLayout']/button[contains(@data-meta-name,'close')]";
    private final String buttonBasketFresnelContainer = "//div[@data-meta-name='UserButtonContainer']/following-sibling::a/div[@data-meta-name='BasketButton']";


    public ProductPage clickButtonForAddingItemToBasket(String nameProduct) {
        $x(String.format(buttonForAddingItemToBasket, nameProduct))
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .should(visible, Duration.ofSeconds(3))
                .click();
        return this;
    }

    public ProductPage clickButtonCloseUpSaleBasketLayout() {
        $x(buttonCloseUpSaleBasketLayout)
                .should(visible, Duration.ofSeconds(3))
                .click();
        return this;
    }

    public ProductPage clickButtonBasketFresnelContainer() {
        $x(buttonBasketFresnelContainer)
                .should(visible, Duration.ofSeconds(1))
                .click();
        return this;
    }
}