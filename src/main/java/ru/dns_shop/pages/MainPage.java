package ru.dns_shop.pages;

import com.codeborne.selenide.ex.ElementNotFound;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Главная страница сайта Citilink
 */
public class MainPage {
    private final String uniqueElement = "//div[@data-meta-name='BannersLayout']";
    private final String productCatalog = "//a[@data-meta-name='DesktopHeaderFixed__catalog-menu']";
    private final String televisionsAndAudioVideoEquipmentCategory = "//div[@data-meta-name='CatalogMenuDesktopLayout__menu']//span[text()='Телевизоры, аудио-видео техника']";
    private final String oledTelevisionsCategory = "//span[text()='Телевизоры OLED']";
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

    public MainPage productCatalogClick() {
        $x(productCatalog).should(visible, Duration.ofSeconds(secondsOfWaiting));
        executeJavaScript("arguments[0].click();", $x(productCatalog));
        return this;
    }

    public MainPage televisionsAndAudioVideoEquipmentCategoryClick() {
        $x(televisionsAndAudioVideoEquipmentCategory).should(visible, Duration.ofSeconds(secondsOfWaiting));
        actions().moveToElement($x(televisionsAndAudioVideoEquipmentCategory)).perform();
        return this;
    }

    public void oledTelevisionsCategoryClick() {
        $x(oledTelevisionsCategory).should(visible, Duration.ofSeconds(secondsOfWaiting));
        executeJavaScript("arguments[0].click();", $x(oledTelevisionsCategory));
    }
}