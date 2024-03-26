package ru.citilink.pages;

import com.codeborne.selenide.ex.UIAssertionError;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Главная страница сайта Citilink
 */
public class MainPage extends BasePage {
    private final String centralAdBanner = "//div[@data-meta-name='BannersLayout']";
    private final String productSearchField = "//input[@type='search']";
    private final String searchDropDownList = "//div[@data-meta-name='InstantSearchExtraResultList']//a";
    private final String compareButton = "//div[@data-meta-name='HeaderBottom__search']/..//div[@data-meta-name='CompareButton']";
    private final String cartButton = "//div[@data-meta-name='HeaderBottom__search']/following-sibling::div//div[@data-meta-name='BasketButton']";
    private final String compareValue = "//div[@data-meta-name='HeaderBottom__search']/..//div[@data-meta-name='NotificationCounter']";
    private final String popularCategoryTile = "//div[contains(@data-meta-name,'category-tiles')]//a//span[contains(text(),'%s')]";
    private final String productCatalog = "//a[@data-meta-name='DesktopHeaderFixed__catalog-menu']";
    private final String televisionsAndAudioVideoEquipmentCategory = "//div[@data-meta-name='CatalogMenuDesktopLayout__menu']//span[text()='Телевизоры, аудио-видео техника']";
    private final String oledTelevisionsCategory = "//span[text()='Телевизоры OLED']";
    private final String searchCategoryInDropDownMenu = "//div[@data-meta-name='InstantSearchExtraResultList']//a[@title='";
    private final String searchButton = "//div[@data-meta-name='HeaderBottom__search']//button[@type='submit']";
    private final String productSearchInputLine = "//input[@type='search']";
    private final String catalogMenuButton = "//span[contains(text(),'Каталог товаров')]";
    private final String catalogCategoryButton = "//div[@class='PopupScrollContainer']//span[contains(text(),'%s')]";

    @Step("Проверка открытия главной страницы")
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
        jsClick($x(productSearchField));
        $x(productSearchField).sendKeys(searchedProduct);
        return this;
    }

    @Step("Поиск товара {searchedProduct} через поле поиска")
    public MainPage searchProductByInputBox(String searchedProduct) {
        inputBoxWriteText(searchedProduct);
        $x(productSearchField).should(visible, WAITING_TIME)
                .pressEnter();
        return this;
    }

    public ResultsPage productSearchExtraResultListClick(String gameName) {
        $$x(searchDropDownList).shouldBe(sizeGreaterThan(0), WAITING_TIME)
                .findBy(text(gameName)).click();
        return new ResultsPage();
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

    @Step("Переход в раздел категории = {nameCategory}, через плитку категории")
    public MainPage clickPopularCategoryTile(String nameCategory) {
        $x(String.format(popularCategoryTile, nameCategory))
                .scrollIntoView("{behavior: \"smooth\", block: \"center\", inline: \"nearest\"}")
                .should(visible, WAITING_TIME)
                .click();
        makeScreenshot();
        return this;
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

    public MainPage clickOnProductFromDropDownList(String productFromDropDownList) {
        jsClick($x(searchCategoryInDropDownMenu + productFromDropDownList + "']"));
        return this;
    }

    public MainPage cartButtonClick() {
        jsClick($x(cartButton));
        return this;
    }

    @Step("Поиск товара '{nameProduct}' через поле поиска")
    public MainPage enterSearchProductInputLine(String nameProduct) {
        $x(productSearchField)
                .should(visible, WAITING_TIME)
                .val(nameProduct).pressEnter();
        makeScreenshot();
        return this;
    }

    public ResultsPage searchButtonClick() {
        executeJavaScript("arguments[0].click()",
                $x(searchButton).shouldBe(visible, WAITING_TIME));
        return new ResultsPage();
    }

    @Step("Поиск товара '{nameProduct}' через поле поиска и нажатие на кнопку поискаь(рис.лупа)")
    public MainPage enterProductInputLineAndClickSearchButton(String nameProduct) {
        $x(productSearchInputLine)
                .should(visible, WAITING_TIME)
                .sendKeys(nameProduct);
        clickSearchButton();
        makeScreenshot();
        return this;
    }

    private void clickSearchButton() {
        $x(searchButton)
                .should(visible, WAITING_TIME)
                .click();
    }

    @Step("Раскрытие каталога (выпадающий список)")
    public MainPage clickCatalogMenuButton() {
        $x(catalogMenuButton).shouldBe(visible, WAITING_TIME).click();
        makeScreenshot();
        return this;
    }

    @Step("Переход в категорию {nameCategory} через выпадающий список 'Категории'")
    public MainPage clickCatalogCategoryButton(String nameCategory) {
        $x(String.format(catalogCategoryButton, nameCategory))
                .should(visible, WAITING_TIME)
                .click();
        makeScreenshot();
        return this;
    }
}