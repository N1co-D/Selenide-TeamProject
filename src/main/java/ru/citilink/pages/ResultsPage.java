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
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.fail;

/**
 * Страница с результатами "Результаты [поиска] для <запрос пользователя>" в Citilink
 * Страница с результатами "[Категория товаров]" в Citilink
 */
public class ResultsPage extends BasePage {
    private final String uniqueElement = "//div[@data-meta-name='FiltersLayout']";
    private final String detailedCatalogMode = "//label[@for='Подробный режим каталога-list']";
    private final String listOfProducts = "//div[@data-meta-name='ProductHorizontalSnippet']";
    private final String ramMemoryParameterOfProduct = ".//span[text()='Оперативная память']/..";
    private final String diskParameterOfProduct = ".//span[text()='Диск']/..";
    private final String inCartButton = ".//button[@data-meta-name='Snippet__cart-button']";
    private final String windowWithAddedProductInCartStatus = "//div[@data-meta-name='Popup']";
    private final String closeWindowWithAddedProductInCartStatus = "//button[@data-meta-name='UpsaleBasket__close-popup']";
    private final String cartButton = "//div[@data-meta-name='HeaderBottom__search']/following-sibling::div//div[@data-meta-name='BasketButton']";
    private final BasePage basePage = new BasePage();

    public boolean getPagesUniqueElement() {
        try {
            $x(uniqueElement).should(visible, Duration.ofSeconds(DURATION));
            return $x(uniqueElement).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            fail("Фильтр (как уникальный элемент страницы) не обнаружен");
        }
        return false;
    }

    public ResultsPage enableDetailedCatalogMode() {
        $x(detailedCatalogMode).should(visible, Duration.ofSeconds(DURATION));
        basePage.jsClick($x(detailedCatalogMode));
        return this;
    }

    private ElementsCollection getAllProductsInPage() {
        return $$x(listOfProducts).should(CollectionCondition.sizeGreaterThan(0));
    }

    private String getRamMemoryParameterOfProduct() {
        return $x(ramMemoryParameterOfProduct).should(visible, Duration.ofSeconds(DURATION))
                .getText();
    }

    private String getDiskParameterOfProduct() {
        return $x(diskParameterOfProduct).should(visible, Duration.ofSeconds(DURATION))
                .getText();
    }

    private SelenideElement searchingForRequiredProductInList(String firstParameter, String secondParameter) {
        ElementsCollection allProductsFromList = getAllProductsInPage();
        SelenideElement foundProduct = null;
        for (SelenideElement product : allProductsFromList) {
            if ((getRamMemoryParameterOfProduct().contains(firstParameter))
                    && (getDiskParameterOfProduct()).contains(secondParameter)) {
                foundProduct = product;
                break;
            }
        }
        if (foundProduct == null) {
            fail("Продукт с приведенными параметрами не был найден в списке");
        }
        return foundProduct;
    }

    public void requiredProductWithParametersBuyingClick(String firstParameter, String secondParameter) {
        SelenideElement requiredProduct = searchingForRequiredProductInList(firstParameter, secondParameter);
        requiredProduct.$x(inCartButton).should(visible, Duration.ofSeconds(DURATION));
        basePage.jsClick(requiredProduct.$x(inCartButton));
    }

    public void cartButtonClick() {
        $x(cartButton).should(visible, Duration.ofSeconds(DURATION));
        basePage.jsClick($x(cartButton));
    }

    public void closeWindowWithAddedProductInCartStatusClick() {
        $x(closeWindowWithAddedProductInCartStatus).should(visible, Duration.ofSeconds(DURATION));
        basePage.jsClick($x(closeWindowWithAddedProductInCartStatus));
    }

    public boolean checkAppearingWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).should(appear, Duration.ofSeconds(DURATION));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean checkDisappearingWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).shouldNot(appear, Duration.ofSeconds(DURATION));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}