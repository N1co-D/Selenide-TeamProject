package ru.dns_shop.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница "Корзина" на сайте Citilink
 */
public class CartPage {
    private final String uniqueElement = "//div[@data-meta-name='BasketSummary']";
    private final String listOfProductsInCart = "//div[@data-meta-name='BasketSnippet']";
    private final String productTitleInCart = ".//span[text()='Код товара: ']/../following-sibling::a/span/span[text()]";
    private final String statusOfMissingProductsInCart = "//span[text()='В корзине нет товаров']";
    private final String deleteProductInCartButton = ".//div[@data-meta-name='DeleteAction']/button";
    private final String goBackToShoppingButton = "//a[@title='Вернуться к покупкам']";
    private final int secondsOfWaiting = 20;

    public boolean getPagesUniqueElement() {
        try {
            $x(uniqueElement).should(visible, Duration.ofSeconds(secondsOfWaiting));
            Selenide.sleep(5000);
            return $x(uniqueElement).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            Assertions.fail("Боковое описание корзины (как уникальный элемент страницы) не обнаружен");
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
            Assertions.fail("Продукт с названием, содержащим '" + observedProduct + "' , не был найден в списке");
        }
        return foundProduct;
    }

    private SelenideElement getElementWithCurrentProductTitle(SelenideElement product) {
        $x(productTitleInCart).should(visible, Duration.ofSeconds(secondsOfWaiting));
        return product.$x(productTitleInCart);
    }

    public void deleteRequiredProductInCartButtonClick(String observedProduct) {
        SelenideElement requiredProduct = searchingForRequiredProductInList(observedProduct);
        requiredProduct.$x(deleteProductInCartButton).should(visible, Duration.ofSeconds(secondsOfWaiting));
        executeJavaScript("arguments[0].click();", requiredProduct.$x(deleteProductInCartButton));
    }

    public void goBackToShoppingButtonClick() {
        $x(goBackToShoppingButton).should(visible, Duration.ofSeconds(secondsOfWaiting));
        executeJavaScript("arguments[0].click();", $x(goBackToShoppingButton));
    }

    public boolean getStatusOfMissingProductsInCart() {
        try {
            $x(statusOfMissingProductsInCart).should(visible, Duration.ofSeconds(secondsOfWaiting));
            return $x(statusOfMissingProductsInCart).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            Assertions.fail("Элемент с уведомлением 'В корзине нет товаров' не был найден");
        }
        return false;
    }
}