package ru.citilink.pages;

import com.codeborne.selenide.ex.UIAssertionError;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Страница "Сравнение товаров" сайта Citilink
 */
public class ComparePage extends BasePage {
    private final String showOnlyDifferenceCheckbox = "//label[contains(@class,'Compare__actions_show-differences')]";
    private final String amountOfAddedProductsToCompare = "//div[@class='Tabs js--Tabs']//div";

    public ComparePage checkIfCorrectPageOpen() {
        try {
            $x(showOnlyDifferenceCheckbox).should(visible, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не удалось подтвердить открытие ожидаемой страницы. Уникальный элемент " +
                    "страницы 'showOnlyDifferenceCheckbox' не был найден в течение заданного времени.");
        }
        return this;
    }

    private String getAmountOfAddedProductsToCompare() {
        return $x(amountOfAddedProductsToCompare).should(visible, WAITING_TIME)
                .getText();
    }

    public ComparePage checkAmountOfAddedProductsToCompare(int expectedAmountOfProductsForAdding) {
        try {
            assertThat(getAmountOfAddedProductsToCompare()
                    .equals(String.valueOf(expectedAmountOfProductsForAdding)))
                    .isEqualTo(true);
        } catch (AssertionError e) {
            fail(String.format("Фактическое количество добавленных для сравнения товаров = %s " +
                            " не соответствует ожидаемому = %s",
                    getAmountOfAddedProductsToCompare(), expectedAmountOfProductsForAdding));
        }
        return this;
    }
}