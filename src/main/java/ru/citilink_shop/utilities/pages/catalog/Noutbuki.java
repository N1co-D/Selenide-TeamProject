package ru.citilink_shop.utilities.pages.catalog;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class Noutbuki {
    private final String filterDropDownCategoryAndValue = "//div[@data-meta-name='FilterListGroupsLayout']//div[contains(@data-meta-value,'%s')]//div[contains(@data-meta-value,'%s')]";
    private final String productHorizontalSnippets = "//div[@data-meta-name='ProductHorizontalSnippet']/../div";
    private final String buttonWithFilteringResults = "//div[contains(@class,'app-catalog') and contains(text(),'Найдено')]";
    private final String buttonDetailCatalogMode = "//div[@data-meta-name='ViewSwitcher']//label[contains(@for,'Подробный режим]";

    public Noutbuki clickFilterDropDownCategoryAndValue(String filterCategory, String value) {
        $x(String.format(filterDropDownCategoryAndValue, filterCategory, value))
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .shouldBe(interactable, Duration.ofSeconds(5))
                .click();
        return this;
    }

    public Noutbuki clickButtonWithFilteringResults() {
        $x(buttonWithFilteringResults)
                .should(visible, Duration.ofSeconds(1))
                .click();
        return this;
    }

    public Noutbuki clickButtonDetailCatalogMode() {
        $x(buttonDetailCatalogMode)
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .should(visible, Duration.ofSeconds(1))
                .click();
        return this;
    }

    public Noutbuki checkProductsAfterFiltration() {
        ElementsCollection productSnippets = $$x(productHorizontalSnippets)
                .should(CollectionCondition.sizeGreaterThan(0));
        System.out.println(productSnippets.size());
//        productSnippets.shouldBe(CollectionCondition.allMatch("all elements have brand", e -> e.get.contains("22 / 06 / 2021")));
        return this;
    }
}