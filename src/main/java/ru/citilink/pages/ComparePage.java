package ru.citilink.pages;

import com.codeborne.selenide.ex.ElementNotFound;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Страница "Сравнение товаров" сайта Citilink
 */
public class ComparePage {
    private final String showOnlyDifferenceCheckbox = "//label[contains(@class,'Compare__actions_show-differences')]";
    private final String amountOfAddedProductsToCompare = "//div[@class='Tabs js--Tabs']//div";
    private static final int SECONDS_OF_WAITING = 20;

    public boolean getPagesUniqueElement() {
        try {
//            assertThat($x(showOnlyDifferenceCheckbox).should(visible, WAITING_TIME));
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            fail("Чекбокс 'Показывать только отличия' (как уникальный элемент страницы) не обнаружен");
        }
        return false;
    }

    public String getAmountOfAddedProductsToCompare() {
        return $x(amountOfAddedProductsToCompare).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING))
                .getText();
    }
}