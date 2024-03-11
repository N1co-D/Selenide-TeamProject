package ru.citilink.pages;

import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.fail;

/**
 * Главная страница сайта Citilink
 */
public class MainPage extends BasePage {
    private final String uniqueElement = "//div[@data-meta-name='BannersLayout']";
    private final String inputBox = "//input[@type='search']";
    private final String productSearchInputLine = "//input[@type='search']";//todo

    public boolean getPagesUniqueElement() { //todo поменять имя
        try {
            $x(uniqueElement).should(visible, WAITING_TIME);
            return $x(uniqueElement).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) { //todo поменять эксепшион
            fail("Центральная секция с баннерами (как уникальный элемент страницы) не обнаружен");
        }
        return false;
    }

    private MainPage inputBoxWriteText(String searchingProduct) {
        jsClick($x(inputBox));
        $x(inputBox).sendKeys(searchingProduct);
        return this;
    }

    public void searchProductByInputBox(String searchingProduct) {
        inputBoxWriteText(searchingProduct);
        $x(inputBox).should(visible, WAITING_TIME).pressEnter();
    }
    public MainPage enterProductSearchInputLine(String nameProduct) {
        $x(productSearchInputLine)
                .should(visible, WAITING_TIME)
                .val(nameProduct).pressEnter();
        return this;
    }
}