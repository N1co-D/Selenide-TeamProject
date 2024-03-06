package ru.dns_shop.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ex.ElementNotFound;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Страница с результатами "Результаты [поиска] для <запрос пользователя>" в Citilink
 * Страница с результатами "[Категория товаров]" в Citilink
 */
public class ResultsPage {
    private final String uniqueElement = "//div[@data-meta-name='FiltersLayout']";
    private final String detailedCatalogMode = "//label[@for='Подробный режим каталога-list']";
    private final String listOfProducts = "//div[@data-meta-name='ProductHorizontalSnippet']";
    private final String addToCompareButton = ".//button[@data-meta-name='Snippet__compare-button']";
    private final String comparingButton = "//div[@data-meta-name='HeaderBottom__search']/following-sibling::div//div[@data-meta-name='CompareButton']";
    private final String amountOfAddedProductsToCompare = "//div[contains(@class,'fresnel-greaterThanOrEqual')]//div[@data-meta-name='CompareButton']//div[@data-meta-name='NotificationCounter']";
    private final int secondsOfWaiting = 20;

    public boolean getPagesUniqueElement() {
        try {
            $x(uniqueElement).should(visible, Duration.ofSeconds(secondsOfWaiting));
            return $x(uniqueElement).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            Assertions.fail("Фильтр (как уникальный элемент страницы) не обнаружен");
        }
        return false;
    }

    public ResultsPage enableDetailedCatalogMode() {
        $x(detailedCatalogMode).should(visible, Duration.ofSeconds(secondsOfWaiting));
        executeJavaScript("arguments[0].click();", $x(detailedCatalogMode));
        return this;
    }

    private ElementsCollection getAllProductsInPage() {
        return $$x(listOfProducts).should(CollectionCondition.sizeGreaterThan(0));
    }

    public void someProductAddToComparingClick(int amountOfProductsForAdding) {
        ElementsCollection allProductsFromList = getAllProductsInPage();
        int countOfAddedProducts = 0;
        while (countOfAddedProducts < amountOfProductsForAdding) {
            allProductsFromList.get(countOfAddedProducts).should(visible, Duration.ofSeconds(secondsOfWaiting));
            executeJavaScript("arguments[0].click();", allProductsFromList.get(countOfAddedProducts).$x(addToCompareButton));
            countOfAddedProducts++;
        }
    }

    public String getAmountOfAddedProductsToCompare() {
        return $x(amountOfAddedProductsToCompare).should(visible, Duration.ofSeconds(secondsOfWaiting)).getText();
    }

    public void comparingButtonClick() {
        $x(comparingButton).should(visible, Duration.ofSeconds(secondsOfWaiting));
        executeJavaScript("arguments[0].click();", $x(comparingButton));
    }
}