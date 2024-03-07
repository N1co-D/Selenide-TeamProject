package ru.citilink.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.fail;

/**
 * Страница с результатами "Результаты [поиска] для <запрос пользователя>" в Citilink
 * Страница с результатами "[Категория товаров]" в Citilink
 */
public class ResultsPage {
    private final String uniqueElement = "//div[@data-meta-name='FiltersLayout']";
    private final String detailedCatalogMode = "//label[@for='Подробный режим каталога-list']";
    private final String listOfProducts = "//div[@data-meta-name='ProductHorizontalSnippet']";
    private final String productTitle = ".//a[@data-meta-name='Snippet__title']";
    private final String inCartButton = ".//button[@data-meta-name='Snippet__cart-button']";
    private final String windowWithAddedProductInCartStatus = "//div[@data-meta-name='Popup']";
    private final String closeWindowWithAddedProductInCartStatus = "//button[@data-meta-name='UpsaleBasket__close-popup']";
    private final String cartButton = "//div[@data-meta-name='HeaderBottom__search']/following-sibling::div//div[@data-meta-name='BasketButton']";
    private static final int SECONDS_OF_WAITING = 20;

    public boolean getPagesUniqueElement() {
        try {
            $x(uniqueElement).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
            return $x(uniqueElement).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            fail("Фильтр (как уникальный элемент страницы) не обнаружен");
        }
        return false;
    }

    public ResultsPage enableDetailedCatalogMode() {
        $x(detailedCatalogMode).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        executeJavaScript("arguments[0].click();", $x(detailedCatalogMode));
        return this;
    }

    private ElementsCollection getAllProductsInPage() {
        return $$x(listOfProducts).should(CollectionCondition.sizeGreaterThan(0));
    }

    private SelenideElement searchForRequiredProductInList(String observedProduct) {
        ElementsCollection allProductsFromList = getAllProductsInPage();
        SelenideElement foundProduct = null;
        SelenideElement currentProductTitleElement;
        for (SelenideElement product : allProductsFromList) {
            currentProductTitleElement = getElementWithCurrentProductTitle(product);
            if (currentProductTitleElement.getText().contains(observedProduct)) {
                foundProduct = product;
                break;
            }
        }
        if (foundProduct == null) {
            fail("Продукт с названием, содержащим '" + observedProduct + "' , не был найден в списке");
        }
        return foundProduct;
    }

    private SelenideElement getElementWithCurrentProductTitle(SelenideElement product) {
        $x(productTitle).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        return product.$x(productTitle);
    }

    public void requiredProductBuyingClick(String observedProduct) {
        SelenideElement foundRequiredProduct = searchForRequiredProductInList(observedProduct);
        foundRequiredProduct.$x(inCartButton).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        executeJavaScript("arguments[0].click();", foundRequiredProduct.$x(inCartButton));
    }

    public void cartButtonClick() {
        $x(cartButton).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        executeJavaScript("arguments[0].click();", $x(cartButton));
    }

    public void closeWindowWithAddedProductInCartStatusClick() {
        $x(closeWindowWithAddedProductInCartStatus).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        executeJavaScript("arguments[0].click();", $x(closeWindowWithAddedProductInCartStatus));
    }

    public boolean checkAppearingWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).should(appear, Duration.ofSeconds(SECONDS_OF_WAITING));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean checkDisappearingWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).shouldNot(appear, Duration.ofSeconds(SECONDS_OF_WAITING));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}