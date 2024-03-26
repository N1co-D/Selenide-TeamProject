package ru.citilink.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.UIAssertionError;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Страница "Корзина" на сайте Citilink
 */
public class CartPage extends BasePage {
    private final String sideDescriptionOfCart = "//div[@data-meta-name='BasketSummary']";
    private final String amountOfProductInCart = "//input[@data-meta-name='Count__input']";
    private final String increaseTheAmountOfProductInCartButton = "//button[@data-meta-name='Count__button-plus']";
    private final String listOfProductsInCart = "//div[@data-meta-name='BasketSnippet']";
    private final String codeNumberOfProductInCart = "//span[text()='Код товара: ']";
    private final String productTitleInCart = ".//span[text()='Код товара: ']/../following-sibling::a/span/span[text()]";
    private final String statusOfMissingProductsInCart = "//span[text()='В корзине нет товаров']";
    private final String deleteProductInCartButton = ".//div[@data-meta-name='DeleteAction']/button";
    private final String goBackToShoppingButton = "//a[@title='Вернуться к покупкам']";
    private final String productNameInCart = "//div[@data-meta-name='BasketSnippet']//a//span[text()]";
    private final String basketSnippetProductname = "//div[@data-meta-type='Product']//a//span[text()]";

    @Step("Открытие страницы корзины")
    public CartPage checkIfCorrectPageOpen() {
        try {
            $x(sideDescriptionOfCart).should(visible, WAITING_TIME);
        } catch (UIAssertionError e) {
            makeScreenshot();
            fail("Не удалось подтвердить открытие ожидаемой страницы. " +
                    "Уникальный элемент страницы 'sideDescriptionOfCart' не был найден в течение заданного времени.");
        }
        makeScreenshot();
        return this;
    }

    @Step("Увеличение количества товара в корзине до {amountOfProductsForIncrease}")
    public CartPage increaseTheAmountOfProductInCartButtonClick(int amountOfProductsForIncrease) {
        for (int i = 1; i < amountOfProductsForIncrease; i++) {
            jsClick($x(increaseTheAmountOfProductInCartButton));
        }
        return this;
    }

    public String getAmountOfProductInCart() {
        return $x(amountOfProductInCart).should(visible, WAITING_TIME)
                .getAttribute("value");
    }

    public String getCodeNumberOfProductInCart() {
        return $x(codeNumberOfProductInCart).should(visible, WAITING_TIME)
                .getText();
    }

    @Step("Соответствие количества добавленного товара в корзине с ожидаемым значением = {expectedAmountOfProduct}")
    public CartPage checkAmountOfProductInCart(String expectedAmountOfProduct) {
        String actualAmountOfProduct = getAmountOfProductInCart();
        assertEquals(expectedAmountOfProduct, actualAmountOfProduct,
                String.format("Фактическое количество товаров в корзине = %s не соответствует ожидаемому = %s",
                        actualAmountOfProduct, expectedAmountOfProduct));
        makeScreenshot();
        return this;
    }

    @Step("Соответствие кода добавленного в корзину товара с ожидаемым значением = {expectedProductCode}")
    public CartPage checkIsCorrectCodeNumberOfProductInCart(String expectedProductCode) {
        String actualProductCode = getCodeNumberOfProductInCart();
        assertTrue(actualProductCode.contains(expectedProductCode),
                String.format("Фактическое значение кода добавленного товара = %s " +
                                " не соответствует ожидаемому = %s",
                        actualProductCode.substring(getCodeNumberOfProductInCart().indexOf(":") + 1),
                        expectedProductCode));
        makeScreenshot();
        return this;
    }

    private ElementsCollection getAllProductsInCart() {
        sleep(4000);
        return $$x(listOfProductsInCart)
                .should(sizeGreaterThan(0));
    }

    private SelenideElement searchingForRequiredProductInList(String observedProduct) {
        ElementsCollection allProductsFromList = getAllProductsInCart();
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
        $x(productTitleInCart).should(visible, WAITING_TIME);
        return product.$x(productTitleInCart);
    }

    @Step("Удаление товара '{observedProduct}' из корзины")
    public CartPage deleteRequiredProductInCartButtonClick(String observedProduct) {
        SelenideElement requiredProduct = searchingForRequiredProductInList(observedProduct);
        jsClick(requiredProduct.$x(deleteProductInCartButton));
        makeScreenshot();
        return this;
    }

    @Step("Переход на главную страницу через кнопку 'Вернуться к покупкам'")
    public CartPage goBackToShoppingButtonClick() {
        jsClick($x(goBackToShoppingButton));
        return this;
    }

    @Step("Отображение статуса об отсутствии товаров в корзине")
    public CartPage checkIsVisibleStatusOfMissingProductsInCart() {
        try {
            $x(statusOfMissingProductsInCart).should(visible, WAITING_TIME);
        } catch (UIAssertionError e) {
            makeScreenshot();
            fail("Элемент с уведомлением 'В корзине нет товаров' не был обнаружен");
        }
        makeScreenshot();
        return this;
    }

    public String getNameProductFromBasketSnippet() {
        return $x(basketSnippetProductname)
                .should(visible, WAITING_TIME)
                .getText();
    }

    public CartPage checkProductTitleCart(String observedProduct) {
        ElementsCollection selenideElements = $$x(listOfProductsInCart).shouldBe(sizeGreaterThan(0), WAITING_TIME);
        String productTitle = null;
        for (int i = 0; i < selenideElements.size() - 1; i++) {
            if (selenideElements.get(i).$x(productTitleInCart).getText().equals(observedProduct)) {
                productTitle = selenideElements.get(i).$x(productTitleInCart).getText();
                break;
            }
        }
        if (productTitle == null) {
            fail("Продукт с названием, '" + observedProduct + "', не был найден в списке");
        }
        return this;
    }

    private String getProductNameInCart() {
        return $x(productNameInCart).should(visible, WAITING_TIME)
                .getText();
    }

    public CartPage checkProductNameInCart(String expectedProductName) {
        String productName = getProductNameInCart();
        assertTrue(productName.contains(expectedProductName),
                String.format("Фактическое имя добавленного товара = %s " +
                                " не соответствует ожидаемому = %s",
                        productName,
                        expectedProductName));
        return this;
    }
}