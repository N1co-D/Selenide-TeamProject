package ru.citilink_shop.utilities.pages.catalog;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class Noutbuki {
    private final String filterDropDownCategoryAndValue = "//div[@data-meta-name='FilterListGroupsLayout']//div[contains(@data-meta-value,'%s')]//div[contains(@data-meta-value,'%s')]";
    private final String productHorizontalSnippets = "//div[@data-meta-name='ProductHorizontalSnippet']/../div";
    private final String buttonWithFilteringResults = "//div[contains(@class,'app-catalog') and contains(text(),'Найдено')]";
    private final String buttonDetailCatalogMode = "//div[@class=\"app-catalog-1h5tkz eo3odd60\"]";
    private static final long SECONDS_OF_WAITING = 15;

    public Noutbuki clickFilterDropDownCategoryAndValue(String filterCategory, String value) {
        $x(String.format(filterDropDownCategoryAndValue, filterCategory, value))
                .scrollIntoView("{behavior: \"smooth\", block: \"center\", inline: \"nearest\"}")
                .shouldBe(interactable, Duration.ofSeconds(SECONDS_OF_WAITING))
                .click();
        return this;
    }

    public Noutbuki clickButtonWithFilteringResults() {
        $x(buttonWithFilteringResults)
                .should(visible, Duration.ofSeconds(SECONDS_OF_WAITING))
                .click();
        return this;
    }

    public Noutbuki clickButtonDetailCatalogMode() {
        $x(buttonDetailCatalogMode)
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .should(visible, Duration.ofSeconds(SECONDS_OF_WAITING))
                .click();
        return this;
    }

    public Noutbuki checkProductsAfterFiltration() {
        $$x(productHorizontalSnippets)
                .should(CollectionCondition.sizeGreaterThan(0))
                .shouldBe(allMatch("all elements have brand", e -> e.getText().contains("Huawei")))
                .shouldBe(allMatch("all elements have ", e -> e.getText().contains("14")))
                .shouldBe(allMatch("all elements have brand", e -> e.getText().contains("Core i7")));
        return this;
    }
}