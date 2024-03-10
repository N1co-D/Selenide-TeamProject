package ru.citilink.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Главная страница сайта Citilink
 */
public class MainPage extends BasePage {
    private final String centralAdBanner = "//div[@data-meta-name='BannersLayout']";
    private final String inputBox = "//input[@type='search']";

    public MainPage checkIfCorrectPageOpen() {
        try {
            assertThat($x(centralAdBanner).should(visible, WAITING_TIME));
        } catch (AssertionError e) {
            fail("Ошибка в открытии ожидаемой главной страницы сайта Citilink");
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