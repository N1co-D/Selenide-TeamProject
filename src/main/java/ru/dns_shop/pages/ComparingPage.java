package ru.dns_shop.pages;

import com.codeborne.selenide.ex.ElementNotFound;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница "Сравнение товаров" сайта Citilink
 */
public class ComparingPage {
    private final String uniqueElement = "//label[contains(@class,'Compare__actions_show-differences')]";
    private final String amountOfAddedProductsToCompare = "//div[@class='Tabs js--Tabs']//div";
    private final int secondsOfWaiting = 20;

    public boolean getPagesUniqueElement() {
        try {
            $x(uniqueElement).should(visible, Duration.ofSeconds(secondsOfWaiting));
            return $x(uniqueElement).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            Assertions.fail("Чекбокс 'Показывать только отличия' (как уникальный элемент страницы) не обнаружен");
        }
        return false;
    }

    public String getAmountOfAddedProductsToCompare() {
        return $x(amountOfAddedProductsToCompare).should(visible, Duration.ofSeconds(secondsOfWaiting))
                .getText();
    }
}