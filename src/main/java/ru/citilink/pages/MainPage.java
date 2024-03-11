package ru.citilink.pages;


import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.assertj.core.api.Assertions.fail;
/**
 * Главная страница сайта Citilink
 */
public class MainPage extends BasePage{
    private final String uniqueElement = "//div[@data-meta-name='BannersLayout']";
    private final String inputBox = "//input[@type='search']";



    private final String searchField = "//input[@type='search']";
    private final String searchDropDownList = "//div[@data-meta-name='InstantSearchExtraResultList']//a";
    private final String compareButton = "//div[@data-meta-name='HeaderBottom__search']/..//div[@data-meta-name='CompareButton']";
    private final String compareValue = "//div[@data-meta-name='HeaderBottom__search']/..//div[@data-meta-name='NotificationCounter']";
    private static final long SECONDS_OF_WAITING = 15;


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




    public MainPage enterDataSearchField(String parameterSearch) {
        $x(searchField).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).sendKeys(parameterSearch);
        return this;
    }

    public ResultsPage productSearchExtraResultListClick(String gameName) {
        $$x(searchDropDownList).shouldBe(sizeGreaterThan(0), Duration.ofSeconds(SECONDS_OF_WAITING))
                .findBy(text(gameName)).click();
        return new ResultsPage();
    }

    public boolean compareValueIsDisplayed() {
        return $x(compareValue).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).isDisplayed();
    }

    public ComparePage compareButtonClick() {
        $x(compareButton).shouldBe(visible, Duration.ofSeconds(SECONDS_OF_WAITING)).click();
        return new ComparePage();
