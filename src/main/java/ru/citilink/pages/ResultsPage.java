package ru.citilink.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Страница с результатами "Результаты [поиска] для <запрос пользователя>" в Citilink
 * Страница с результатами "[Категория товаров]" в Citilink
 */
public class ResultsPage extends BasePage {
    private final String filter = "//div[@data-meta-name='FiltersLayout']";
    private final String detailedCatalogMode = "//label[@for='Подробный режим каталога-list']";
    private final String listOfProducts = "//div[@data-meta-name='ProductHorizontalSnippet']";
    private final String ramMemoryParameterOfProduct = ".//span[text()='Оперативная память']/..";
    private final String diskParameterOfProduct = ".//span[text()='Диск']/..";
    private final String inCartButton = ".//button[@data-meta-name='Snippet__cart-button']";
    private final String windowWithAddedProductInCartStatus = "//div[@data-meta-name='Popup']";
    private final String closeWindowWithAddedProductInCartStatus = "//button[@data-meta-name='UpsaleBasket__close-popup']";
    private final String cartButton = "//div[@data-meta-name='HeaderBottom__search']/following-sibling::div//div[@data-meta-name='BasketButton']";
    private final String addToCompareButton = ".//button[@data-meta-name='Snippet__compare-button']";
    private final String comparingButton = "//div[@data-meta-name='HeaderBottom__search']/following-sibling::div//div[@data-meta-name='CompareButton']";
    private final String amountOfAddedProductsToCompare = "//div[contains(@class,'fresnel-greaterThanOrEqual')]//div[@data-meta-name='CompareButton']//div[@data-meta-name='NotificationCounter']";

    public ResultsPage checkIfCorrectPageOpen() {
        try {
            assertThat($x(filter).should(visible, WAITING_TIME));
        } catch (AssertionError e) {
            fail("Ошибка в открытии ожидаемой страницы с результатами поиска");
        }
        return this;
    }

    public ResultsPage enableDetailedCatalogMode() {
        jsClick($x(detailedCatalogMode));
        return this;
    }

    private ElementsCollection getAllProductsInPage() {
        return $$x(listOfProducts).should(sizeGreaterThan(0));
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
            assertThat($x(windowWithAddedProductInCartStatus).should(appear, WAITING_TIME));
        } catch (AssertionError e) {
            fail("Ошибка в открытии всплывающего окна с сообщением о добавлении товара в корзину");
        }
        return this;
    }

    public ResultsPage checkDisappearWindowWithAddedProductInCartStatus() {
        try {
            assertThat($x(windowWithAddedProductInCartStatus).shouldNot(appear, WAITING_TIME));
        } catch (AssertionError e) {
            fail("Ошибка в закрытии всплывающего окна с сообщением о добавлении товара в корзину");
        }
        return this;
    }

    public ResultsPage someProductAddToComparingClick(int amountOfProductsForAdding) {
        ElementsCollection allProductsFromList = getAllProductsInPage();
        for (int countOfAddedProducts = 0; countOfAddedProducts < amountOfProductsForAdding; countOfAddedProducts++) {
            allProductsFromList.get(countOfAddedProducts).should(visible, WAITING_TIME);
            jsClick(allProductsFromList.get(countOfAddedProducts).$x(addToCompareButton));
        }
        return this;
    }

    public ResultsPage checkAmountOfAddedProductsToCompare(int expectedAmountOfProductsForAdding) {
        try {
            assertThat($x(amountOfAddedProductsToCompare).should(visible, WAITING_TIME)
                    .getText()
                    .equals(String.valueOf(expectedAmountOfProductsForAdding)));
        } catch (AssertionError e) {
            fail("Ошибка в корректном отражении количества добавленных для сравнения товаров");
        }
        return this;
    }

    public ResultsPage comparingButtonClick() {
        jsClick($x(comparingButton));
        return this;
    }
}