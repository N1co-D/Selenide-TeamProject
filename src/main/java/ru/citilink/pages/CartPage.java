package ru.citilink.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.ex.ElementNotFound;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница "Корзина" на сайте Citilink
 */
public class CartPage {
    private final String uniqueElement = "//div[@data-meta-name='BasketSummary']";
    private final String codeNumberOfProductInCart = "//span[text()='Код товара: ']";
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

    public String getCodeNumberOfProductInCart() {
        return $x(codeNumberOfProductInCart).should(visible, Duration.ofSeconds(secondsOfWaiting))
                .getText();
    }
}