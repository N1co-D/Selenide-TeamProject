package ru.citilink.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.fail;

/**
 * Страница "Корзина" на сайте Citilink
 */
public class CartPage extends BasePage {
    private final String uniqueElement = "//div[@data-meta-name='BasketSummary']";
    private final String amountOfProductInCart = "//input[@data-meta-name='Count__input']";
    private final String increaseTheAmountOfProductInCartButton = "//button[@data-meta-name='Count__button-plus']";

    public boolean getPagesUniqueElement() {
        try {
            $x(uniqueElement).should(visible, WAITING_TIME);
            Selenide.sleep(5000);
            return $x(uniqueElement).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            fail("Боковое описание корзины (как уникальный элемент страницы) не обнаружен");
        }
        return false;
    }

    public void increaseTheAmountOfProductInCartButtonClick() {
        jsClick($x(increaseTheAmountOfProductInCartButton));
    }

    public String getAmountOfProductInCart() {
        return $x(amountOfProductInCart).should(visible, WAITING_TIME)
                .getAttribute("value");
    }
}