package ru.citilink.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.ex.UIAssertionError;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Страница "Корзина" на сайте Citilink
 */
public class CartPage extends BasePage {
    private final String sideDescriptionOfCart = "//div[@data-meta-name='BasketSummary']";
    private final String codeNumberOfProductInCart = "//span[text()='Код товара: ']";
    private final String amountOfProductInCart = "//input[@data-meta-name='Count__input']";
    private final String increaseTheAmountOfProductInCartButton = "//button[@data-meta-name='Count__button-plus']";

    private final String uniqueElement = "//div[@data-meta-name='BasketSummary']";
    private final String listOfProductsInCart = "//div[@data-meta-name='BasketSnippet']";
    private final String productTitleInCart = ".//span[text()='Код товара: ']/../following-sibling::a/span/span[text()]";
    private final String statusOfMissingProductsInCart = "//span[text()='В корзине нет товаров']";
    private final String deleteProductInCartButton = ".//div[@data-meta-name='DeleteAction']/button";
    private final String goBackToShoppingButton = "//a[@title='Вернуться к покупкам']";


    public CartPage checkIfCorrectPageOpen() {
        try {
            $x(sideDescriptionOfCart).should(visible, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не удалось подтвердить открытие ожидаемой страницы. " +
                    "Уникальный элемент страницы 'sideDescriptionOfCart' не был найден в течение заданного времени.");
        }
        return this;
    }

    public CartPage increaseTheAmountOfProductInCartButtonClick() {
        jsClick($x(increaseTheAmountOfProductInCartButton));
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

    public CartPage checkIsCorrectCodeNumberOfProductInCart(String expectedProductCode) {
        try {
            assertThat(getCodeNumberOfProductInCart()
                    .contains(expectedProductCode))
                    .isEqualTo(true);
        } catch (AssertionError e) {
            fail(String.format("Фактическое значение кода добавленного товара = %s " +
                            " не соответствует ожидаемому = %s",
                    getCodeNumberOfProductInCart().substring(getCodeNumberOfProductInCart().indexOf(":") + 1),
                    expectedProductCode));
        }
        return this;
    }

    public boolean getPagesUniqueElement() {
        try {
            $x(uniqueElement).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
            Selenide.sleep(5000);
            return $x(uniqueElement).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            fail("Боковое описание корзины (как уникальный элемент страницы) не обнаружен");
        }
        return false;
    }

    private ElementsCollection getAllProductsInCart() {
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
        $x(productTitleInCart).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        return product.$x(productTitleInCart);
    }

    public void deleteRequiredProductInCartButtonClick(String observedProduct) {
        SelenideElement requiredProduct = searchingForRequiredProductInList(observedProduct);
        requiredProduct.$x(deleteProductInCartButton).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        executeJavaScript("arguments[0].click();", requiredProduct.$x(deleteProductInCartButton));
    }

    public void goBackToShoppingButtonClick() {
        $x(goBackToShoppingButton).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        executeJavaScript("arguments[0].click();", $x(goBackToShoppingButton));
    }

    public boolean getStatusOfMissingProductsInCart() {
        try {
            $x(statusOfMissingProductsInCart).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
            return $x(statusOfMissingProductsInCart).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            fail("Элемент с уведомлением 'В корзине нет товаров' не был найден");
        }
        return false;
    }
}