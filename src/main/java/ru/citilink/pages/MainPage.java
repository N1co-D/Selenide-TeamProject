package ru.citilink.pages;

import com.codeborne.selenide.ex.UIAssertionError;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import com.codeborne.selenide.ex.UIAssertionError;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;
import static org.assertj.core.api.Assertions.fail;

/**
 * Главная страница сайта Citilink
 */
public class MainPage extends BasePage {
    private final String centralAdBanner = "//div[@data-meta-name='BannersLayout']";
    private final String inputBox = "//input[@type='search']";
    private final String searchDropDownList = "//div[@data-meta-name='InstantSearchExtraResultList']//a";
    private final String compareButton = "//div[@data-meta-name='HeaderBottom__search']/..//div[@data-meta-name='CompareButton']";
    private final String compareValue = "//div[@data-meta-name='HeaderBottom__search']/..//div[@data-meta-name='NotificationCounter']";
    private final String popularCategoryTile = "//div[contains(@data-meta-name,'category-tiles')]//a//span[contains(text(),'%s')]";
    private final String productCatalog = "//a[@data-meta-name='DesktopHeaderFixed__catalog-menu']";
    private final String televisionsAndAudioVideoEquipmentCategory = "//div[@data-meta-name='CatalogMenuDesktopLayout__menu']//span[text()='Телевизоры, аудио-видео техника']";
    private final String oledTelevisionsCategory = "//span[text()='Телевизоры OLED']";

    public MainPage checkIfCorrectPageOpen() {
        try {
            $x(centralAdBanner).should(visible, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не удалось подтвердить открытие ожидаемой страницы. " +
                    "Уникальный элемент страницы 'centralAdBanner' не был найден в течение заданного времени.");
        }
        return this;
    }

    public MainPage inputBoxWriteText(String searchedProduct) {
        jsClick($x(inputBox));
        $x(inputBox).sendKeys(searchedProduct);
        return this;
    }

    public MainPage searchProductByInputBox(String searchedProduct) {
        inputBoxWriteText(searchedProduct);
        $x(inputBox).should(visible, WAITING_TIME)
                .pressEnter();
        return this;
    }

    public MainPage productCatalogClick() {
        jsClick($x(productCatalog));
        return this;
    }

    public boolean compareValueIsDisplayed() {
        return $x(compareValue).shouldBe(visible, WAITING_TIME).isDisplayed();
    }

    public ComparePage compareButtonClick() {
        $x(compareButton).shouldBe(visible, WAITING_TIME).click();
        return new ComparePage();
    }

    public void clickPopularCategoryTile(String nameCategory) {
        $x(String.format(popularCategoryTile, nameCategory))
                .scrollIntoView("{behavior: \"smooth\", block: \"center\", inline: \"nearest\"}")
                .should(visible, WAITING_TIME)
                .click();
    }

    public MainPage televisionsAndAudioVideoEquipmentCategoryClick() {
        $x(televisionsAndAudioVideoEquipmentCategory).should(visible, WAITING_TIME);
        actions().moveToElement($x(televisionsAndAudioVideoEquipmentCategory)).perform();
        return this;
    }

    public MainPage oledTelevisionsCategoryClick() {
        jsClick($x(oledTelevisionsCategory));
        return this;
    }
}