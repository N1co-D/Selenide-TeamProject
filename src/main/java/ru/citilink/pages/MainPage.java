package ru.citilink.pages;

import com.codeborne.selenide.ex.ElementNotFound;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.executeJavaScript;

/**
 * Главная страница сайта Citilink
 */
public class MainPage {
    private final String uniqueElement = "//div[@data-meta-name='BannersLayout']";
    private final String inputBox = "//input[@type='search']";
    private final int secondsOfWaiting = 20;

    public boolean getPagesUniqueElement() {
        try {
            $x(uniqueElement).should(visible, Duration.ofSeconds(secondsOfWaiting));
            return $x(uniqueElement).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            Assertions.fail("Центральная секция с баннерами (как уникальный элемент страницы) не обнаружен");
        }
        return false;
    }

    public MainPage inputBoxWriteText(String searchingProduct) {
        $x(inputBox).should(visible, Duration.ofSeconds(secondsOfWaiting));
        executeJavaScript("arguments[0].click();", $x(inputBox));
        $x(inputBox).sendKeys(searchingProduct);
        return this;
    }

    public void SearchGameByInputBox(String searchingProduct) {
        inputBoxWriteText(searchingProduct);
        $x(inputBox).should(visible, Duration.ofSeconds(secondsOfWaiting)).pressEnter();
    }
}