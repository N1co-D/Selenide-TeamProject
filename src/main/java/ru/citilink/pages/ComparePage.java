package ru.citilink.pages;

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

    public ComparePage getPagesUniqueElement() {
        try {
            assertThat($x(showOnlyDifferenceCheckbox).should(visible, WAITING_TIME));
        } catch (AssertionError e) {
            fail("Ошибка в открытии ожидаемой страницы 'Сравнение товаров'");
        }
        return this;
    }

    public ComparePage checkAmountOfAddedProductsToCompare(int expectedAmountOfProductsForAdding) {
        try {
            assertThat($x(amountOfAddedProductsToCompare).should(visible, WAITING_TIME)
                    .getText()
                    .equals(String.valueOf(expectedAmountOfProductsForAdding)));
        } catch (AssertionError e) {
            fail("Ошибка в корректном отражении количества добавленных для сравнения товаров");
        }
        return this;
    }
}