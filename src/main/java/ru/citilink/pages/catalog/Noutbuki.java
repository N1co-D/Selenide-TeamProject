package ru.citilink.pages.catalog;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.assertj.core.api.SoftAssertions;

import java.time.Duration;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class Noutbuki {
    private final String filterDropDownCategoryAndValue = "//div[@data-meta-name='FilterListGroupsLayout']//div[contains(@data-meta-value,'%s')]//div[contains(@data-meta-value,'%s')]";
    private final String productsHorizontalSnippets = "//div[@data-meta-name='ProductHorizontalSnippet']/../div";
    private final String titlesProductsHorizontalSnippets = "//a[@data-meta-name='Snippet__title']";
    private final String displayProductsHorizontalSnippets = "//span[contains(text(),'Экран')]/..";
    private final String processorProductsHorizontalSnippets = "//span[contains(text(),'Процессор')]/..";
    private final String buttonWithFilteringResults = "//div[contains(@class,'app-catalog') and contains(text(),'Найдено')]";
    private final String buttonDetailCatalogMode = "//div[@class=\"app-catalog-1h5tkz eo3odd60\"]";
    private static final long SECONDS_OF_WAITING = 15;

    public Noutbuki clickFilterDropDownCategoryAndValue(String filterCategory, String value) {
        sleep(2000);
        $x(String.format(filterDropDownCategoryAndValue, filterCategory, value))
//                .hover()
                .scrollIntoView("{behavior: \"smooth\", block: \"end\", inline: \"end\"}")
                .shouldBe(interactable, Duration.ofSeconds(SECONDS_OF_WAITING))
                .scrollIntoView("{behavior: \"smooth\", block: \"center\", inline: \"center\"}")
                .shouldBe(interactable, Duration.ofSeconds(SECONDS_OF_WAITING)).click();
        sleep(2000);
        return this;
    }

//    public Noutbuki clickButtonWithFilteringResults() {
//        $x(buttonWithFilteringResults).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).click();
//        return this;
//    }

    public Noutbuki clickButtonDetailCatalogMode() {
        $x(buttonDetailCatalogMode).scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}").should(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).click();
        return this;
    }
/**
 * Метод
 */
    public Noutbuki checkProductsAfterFiltration(String brand, String diagonal, String processor) {
        checkParametrProductsAfterFilter(productsHorizontalSnippets, titlesProductsHorizontalSnippets, brand)
                .checkParametrProductsAfterFilter(productsHorizontalSnippets, displayProductsHorizontalSnippets, diagonal)
                .checkParametrProductsAfterFilter(productsHorizontalSnippets, processorProductsHorizontalSnippets, processor);
        return this;
    }

    private Noutbuki checkParametrProductsAfterFilter(String baseXpath, String paramXpath, String param) {
        SoftAssertions softAssertions = new SoftAssertions();
        for (SelenideElement element : createElementsCollection(baseXpath + paramXpath)) {
            if (element.getText().toLowerCase(Locale.ROOT).contains(param.toLowerCase(Locale.ROOT))) ;
            else {
                softAssertions.fail(element.getText() + "не имеет параметр " + param);
            }
        }
        softAssertions.assertAll();
        return this;
    }

    private ElementsCollection createElementsCollection(String xPath) {
        return $$x(xPath).should(CollectionCondition.sizeGreaterThan(0));
    }
}