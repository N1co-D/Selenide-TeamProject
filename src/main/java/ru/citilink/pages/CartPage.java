package ru.citilink.pages;

import com.codeborne.selenide.ex.UIAssertionError;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.fail;

/**
 * Страница "Корзина" на сайте Citilink
 */
public class CartPage extends BasePage {
    private final String sideDescriptionOfCart = "//div[@data-meta-name='BasketSummary']";
    private final String amountOfProductInCart = "//input[@data-meta-name='Count__input']";
    private final String increaseTheAmountOfProductInCartButton = "//button[@data-meta-name='Count__button-plus']";

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
}