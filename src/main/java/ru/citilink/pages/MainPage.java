package ru.citilink.pages;

import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.fail;

/**
 * Главная страница сайта Citilink
 */
public class MainPage extends BasePage {
    private final String uniqueElement = "//div[@data-meta-name='BannersLayout']";
    private final String productCatalog = "//a[@data-meta-name='DesktopHeaderFixed__catalog-menu']";
    private final String televisionsAndAudioVideoEquipmentCategory = "//div[@data-meta-name='CatalogMenuDesktopLayout__menu']//span[text()='Телевизоры, аудио-видео техника']";
    private final String oledTelevisionsCategory = "//span[text()='Телевизоры OLED']";
    private final String inputBox = "//input[@type='search']";
    private static final int SECONDS_OF_WAITING = 20;

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

    public boolean getPagesUniqueElement() {
        try {
            $x(uniqueElement).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
            return $x(uniqueElement).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            fail("Центральная секция с баннерами (как уникальный элемент страницы) не обнаружен");
        }
        return false;
    }

    public MainPage productCatalogClick() {
        $x(productCatalog).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        executeJavaScript("arguments[0].click();", $x(productCatalog));
        return this;
    }

    public MainPage televisionsAndAudioVideoEquipmentCategoryClick() {
        $x(televisionsAndAudioVideoEquipmentCategory).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        actions().moveToElement($x(televisionsAndAudioVideoEquipmentCategory)).perform();
        return this;
    }

    public void oledTelevisionsCategoryClick() {
        $x(oledTelevisionsCategory).should(visible, Duration.ofSeconds(SECONDS_OF_WAITING));
        executeJavaScript("arguments[0].click();", $x(oledTelevisionsCategory));
    }
}