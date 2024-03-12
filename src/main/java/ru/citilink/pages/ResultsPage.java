package ru.citilink.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.UIAssertionError;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.fail;

/**
 * Страница с результатами "Результаты [поиска] для <запрос пользователя>" в Citilink
 * Страница с результатами "[Категория товаров]" в Citilink
 */
public class ResultsPage extends BasePage {
    private final String filter = "//div[@data-meta-name='FiltersLayout']";
    private final String detailedCatalogModeButton = "//label[@for='Подробный режим каталога-list']";
    private final String productList = "//div[@data-meta-name='ProductHorizontalSnippet']";
    private final String ramMemoryParameterOfProduct = ".//span[text()='Оперативная память']/..";
    private final String diskParameterOfProduct = ".//span[text()='Диск']/..";
    private final String inCartButton = ".//button[@data-meta-name='Snippet__cart-button']";
    private final String windowWithAddedProductInCartStatus = "//div[@data-meta-name='Popup']";
    private final String closeWindowWithAddedProductInCartStatus = "//button[@data-meta-name='UpsaleBasket__close-popup']";
    private final String cartButton = "//div[@data-meta-name='HeaderBottom__search']/following-sibling::div//div[@data-meta-name='BasketButton']";
    private final String subcategoryPageTitle = "//div[@data-meta-name='SubcategoryPageTitle']/h1[text()]";
    private final String comparingCurrentProductButton = ".//button[@data-meta-name='Snippet__compare-button']";
    private final String priceOfCurrentProduct = ".//span[@data-meta-price]/span[1]";

    public ResultsPage checkIfCorrectPageOpen() {
        try {
            $x(filter).should(visible, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не удалось подтвердить открытие ожидаемой страницы. " +
                    "Уникальный элемент страницы 'filter' не был найден в течение заданного времени.");
        }
        return this;
    }

    public ResultsPage enableDetailedCatalogMode() {
        jsClick($x(detailedCatalogModeButton));
        return this;
    }

    private ElementsCollection getAllProductsInPage() {
        return $$x(productList).should(sizeGreaterThan(0));
    }

    private String getRamMemoryParameterOfProduct() {
        return $x(ramMemoryParameterOfProduct).should(visible, WAITING_TIME)
                .getText();
    }

    private String getDiskParameterOfProduct() {
        return $x(diskParameterOfProduct).should(visible, WAITING_TIME)
                .getText();
    }

    private SelenideElement searchForRequiredProductInList(String firstParameter, String secondParameter) {
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

    public ResultsPage requiredProductWithParametersBuyingClick(String firstParameter, String secondParameter) {
        SelenideElement requiredProduct = searchForRequiredProductInList(firstParameter, secondParameter);
        jsClick(requiredProduct.$x(inCartButton));
        return this;
    }

    public ResultsPage cartButtonClick() {
        jsClick($x(cartButton));
        return this;
    }

    public ResultsPage closeWindowWithAddedProductInCartStatusClick() {
        jsClick($x(closeWindowWithAddedProductInCartStatus));
        return this;
    }

    public ResultsPage checkAppearWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).should(appear, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не было обнаружено всплывающее окно с сообщением о добавлении товара в корзину");
        }
        return this;
    }

    public ResultsPage checkDisappearWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).shouldNot(appear, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Всплывающее окно с сообщением о добавлении товара в корзину не было закрыто " +
                    "после нажатия соответствующего элемента 'closeWindowWithAddedProductInCartStatus'");
        }
        return this;
    }

    public String getSubcategoryPageTitle() {
        return $x(subcategoryPageTitle).shouldBe(visible, WAITING_TIME).getText();
    }

    public String getPriceOfCurrentProduct(String nameData) {
        return $$x(productList).shouldBe(sizeGreaterThan(0), WAITING_TIME)
                .findBy(text(nameData)).$x(priceOfCurrentProduct).getText();
    }

    public ResultsPage comparingCurrentProductButtonClick(String nameData) {
        jsClick($$x(productList).shouldBe(sizeGreaterThan(0), WAITING_TIME)
                .findBy(text(nameData)).$x(comparingCurrentProductButton));
        return this;
    }

    public ResultsPage detailedCatalogModeButtonClick() {
        $x(detailedCatalogModeButton).shouldBe(visible, WAITING_TIME).click();
        return this;
    }
}