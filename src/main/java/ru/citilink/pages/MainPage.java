package ru.citilink.pages;

import com.codeborne.selenide.ex.UIAssertionError;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.fail;

/**
 * Главная страница сайта Citilink
 */
public class MainPage extends BasePage {
    private final String centralAdBanner = "//div[@data-meta-name='BannersLayout']";
    private final String inputBox = "//input[@type='search']";
    private final String searchCategoryInDropDownMenu = "//div[@data-meta-name='InstantSearchExtraResultList']//a[@title='";
    private final String searchDropDownList = "//div[@data-meta-name='InstantSearchExtraResultList']//a";
    private final String compareButton = "//div[@data-meta-name='HeaderBottom__search']/..//div[@data-meta-name='CompareButton']";
    private final String cartButton = "//div[@data-meta-name='HeaderBottom__search']/following-sibling::div//div[@data-meta-name='BasketButton']";
    private final String compareValue = "//div[@data-meta-name='HeaderBottom__search']/..//div[@data-meta-name='NotificationCounter']";

    public MainPage checkIfCorrectPageOpen() {
        try {
            $x(centralAdBanner).should(visible, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не удалось подтвердить открытие ожидаемой страницы. " +
                    "Уникальный элемент страницы 'centralAdBanner' не был найден в течение заданного времени.");
        }
        return this;
    }

    public MainPage writeTextInInputBox(String searchingProduct) {
        jsClick($x(inputBox));
        $x(inputBox).sendKeys(searchingProduct);
        return this;
    }

    public MainPage inputBoxWriteText(String searchingProduct) {
        jsClick($x(inputBox));
        $x(inputBox).sendKeys(searchingProduct);
        return this;
    }

    public MainPage searchProductByInputBox(String searchedProduct) {
        writeTextInInputBox(searchedProduct);
        $x(inputBox).should(visible, WAITING_TIME)
                .pressEnter();
        return this;
    }

    public MainPage clickOnProductFromDropDownList(String productFromDropDownList) {
        jsClick($x(searchCategoryInDropDownMenu + productFromDropDownList + "']"));
        return this;
    }

    public ResultsPage productSearchExtraResultListClick(String gameName) {
        $$x(searchDropDownList).shouldBe(sizeGreaterThan(0), WAITING_TIME)
                .findBy(text(gameName)).click();
        return new ResultsPage();
    }

    public boolean compareValueIsDisplayed() {
        return $x(compareValue).shouldBe(visible, WAITING_TIME).isDisplayed();
    }

    public ComparePage compareButtonClick() {
        $x(compareButton).shouldBe(visible, WAITING_TIME).click();
        return new ComparePage();
    }

    public MainPage cartButtonClick() {
        jsClick($x(cartButton));
        return this;
    }
}