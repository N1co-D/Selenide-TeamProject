package ru.citilink.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.ElementNotFound;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.assertj.core.api.Assertions.fail;

/**
 * Страница "Корзина" на сайте Citilink
 */
public class CartPage {
    private final String uniqueElement = "//div[@data-meta-name='BasketSummary']";
    private final String amountOfProductInCart = "//input[@data-meta-name='Count__input']";
    private final String increaseTheAmountOfProductInCartButton = "//button[@data-meta-name='Count__button-plus']";
    private static final int SECONDS_OF_WAITING = 20;

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

    public void increaseTheAmountOfProductInCartButtonClick() {
        $x(increaseTheAmountOfProductInCartButton).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        executeJavaScript("arguments[0].click();", $x(increaseTheAmountOfProductInCartButton)); //todo вынести в пейдж
    }

    public String getAmountOfProductInCart() {
        return $x(amountOfProductInCart).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING))
                .getAttribute("value");
    }
}