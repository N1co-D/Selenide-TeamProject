package ru.citilink.pages;

import com.codeborne.selenide.ex.UIAssertionError;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.fail;

/**
 * Главная страница сайта Citilink
 */
public class MainPage extends BasePage {
    private final String centralAdBanner = "//div[@data-meta-name='BannersLayout']";
    private final String inputBox = "//input[@type='search']";

    public MainPage checkIfCorrectPageOpen() {
        try {
            $x(centralAdBanner).should(visible, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не удалось подтвердить открытие ожидаемой страницы. " +
                    "Уникальный элемент страницы 'centralAdBanner' не был найден в течение заданного времени.");
        }
        return this;
    }

    private MainPage writeTextInInputBox(String searchingProduct) {
        jsClick($x(inputBox));
        $x(inputBox).sendKeys(searchingProduct);
        return this;
    }

    public MainPage searchProductByInputBox(String searchingProduct) {
        writeTextInInputBox(searchingProduct);
        $x(inputBox).should(visible, WAITING_TIME)
                .pressEnter();
        return this;
    }
}