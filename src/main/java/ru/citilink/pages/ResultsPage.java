package ru.citilink.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Страница с результатами "Результаты [поиска] для <запрос пользователя>" в Citilink
 * Страница с результатами "[Категория товаров]" в Citilink
 */
public class ResultsPage extends BasePage {
    private final String uniqueElement = "//div[@data-meta-name='FiltersLayout']";
    private final String detailedCatalogModeButton = "//label[@for='Подробный режим каталога-list']";
    private final String productList = "//div[@data-meta-name='ProductHorizontalSnippet']";
    private final String ramMemoryParameterOfProduct = ".//span[text()='Оперативная память']/..";
    private final String diskParameterOfProduct = ".//span[text()='Диск']/..";
    private final String inCartButton = ".//button[@data-meta-name='Snippet__cart-button']";
    private final String windowWithAddedProductInCartStatus = "//div[@data-meta-name='Popup']";
    private final String closeWindowWithAddedProductInCartStatus = "//button[@data-meta-name='UpsaleBasket__close-popup']";
    private final String cartButton = "//div[@data-meta-name='HeaderBottom__search']/following-sibling::div//div[@data-meta-name='BasketButton']";
    private final String subcategoryPageTitle = "//div[@data-meta-name='SubcategoryPageTitle']/h1[text()]";
    private final String comparingCurrentProductButton = ".//button[@data-meta-name='Snippet__compare-button']";
    private final String priceOfCurrentProduct = ".//span[@data-meta-price]/span[1]";
    private final String dropDownCategoryAndValueFilter = "//div[@data-meta-name='FilterListGroupsLayout']//div[contains(@data-meta-value,'%s')]//div[contains(@data-meta-value,'%s')]";
    private final String horizontalSnippetsProducts = "//div[@data-meta-name='ProductHorizontalSnippet']/../div";
    private final String horizontalSnippetsProductsTitles = "//a[@data-meta-name='Snippet__title']";
    private final String horizontalSnippetsProductsDisplay = "//span[contains(text(),'Экран')]/..";
    private final String horizontalSnippetsProductsProcessor = "//span[contains(text(),'Процессор')]/..";
    private final String detailCatalogModeButton = "//label[contains(@for,'Подробный режим каталога')]";
    private List<String> notMatchFilterProductsList = new ArrayList<>();

    public boolean getPagesUniqueElement() {
        try {
            $x(uniqueElement).should(visible, WAITING_TIME);
            return $x(uniqueElement).isDisplayed();
        } catch (TimeoutException | NoSuchElementException | ElementNotFound e) {
            fail("Фильтр (как уникальный элемент страницы) не обнаружен");
        }
        return false;
    }

    public ResultsPage enableDetailedCatalogMode() {
        jsClick($x(detailedCatalogModeButton));
        return this;
    }

    private ElementsCollection getAllProductsInPage() {
        return $$x(productList).should(sizeGreaterThan(0));
    }

    private String getRamMemoryParameterOfProduct() {
        return $x(ramMemoryParameterOfProduct).should(visible, WAITING_TIME)
                .getText();
    }

    private String getDiskParameterOfProduct() {
        return $x(diskParameterOfProduct).should(visible, WAITING_TIME)
                .getText();
    }

    private SelenideElement searchingForRequiredProductInList(String firstParameter, String secondParameter) {
        ElementsCollection allProductsFromList = getAllProductsInPage();
        SelenideElement foundProduct = null;
        for (SelenideElement product : allProductsFromList) {
            if ((getRamMemoryParameterOfProduct().contains(firstParameter))
                    && (getDiskParameterOfProduct()).contains(secondParameter)) {
                foundProduct = product;
                break;
            }
        }
        if (foundProduct == null) {
            fail("Продукт с приведенными параметрами не был найден в списке");
        }
        return foundProduct;
    }

    public void requiredProductWithParametersBuyingClick(String firstParameter, String secondParameter) {
        SelenideElement requiredProduct = searchingForRequiredProductInList(firstParameter, secondParameter);
        jsClick(requiredProduct.$x(inCartButton));
    }

    public void cartButtonClick() {
        jsClick($x(cartButton));
    }

    public void closeWindowWithAddedProductInCartStatusClick() {
        jsClick($x(closeWindowWithAddedProductInCartStatus));
    }

    public boolean checkAppearingWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).should(appear, WAITING_TIME);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean checkDisappearingWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).shouldNot(appear, WAITING_TIME);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getSubcategoryPageTitle() {
        return $x(subcategoryPageTitle).shouldBe(visible, WAITING_TIME).getText();
    }

    public String getPriceOfCurrentProduct(String nameData) {
        return $$x(productList).shouldBe(sizeGreaterThan(0), WAITING_TIME)
                .findBy(text(nameData)).$x(priceOfCurrentProduct).getText();
    }

    public ResultsPage comparingCurrentProductButtonClick(String nameData) {
        jsClick($$x(productList).shouldBe(sizeGreaterThan(0), WAITING_TIME)
                .findBy(text(nameData)).$x(comparingCurrentProductButton));
        return this;
    }

    public ResultsPage detailedCatalogModeButtonClick() {
        $x(detailedCatalogModeButton).shouldBe(visible, WAITING_TIME).click();
        return this;
    }

    /**
     * the method scrolls and clicks the selected category and filter values
     *
     * @param filterCategory - the name of the category in the Product Filter
     * @param value          - the value of the selected category
     * @return this Page
     * sleep and the dual use of scroll due to the site filter features
     */
    public ResultsPage clickFilterDropDownCategoryAndValue(String filterCategory, String value) { // todo  modify scroll
        sleep(2000);
        $x(String.format(dropDownCategoryAndValueFilter, filterCategory, value))
                .scrollIntoView("{behavior: \"smooth\", block: \"end\", inline: \"end\"}")
                .shouldBe(interactable, WAITING_TIME)
                .scrollIntoView("{behavior: \"smooth\", block: \"center\", inline: \"center\"}")
                .shouldBe(interactable, WAITING_TIME)
                .click();
        sleep(2000);
        return this;
    }

    public ResultsPage clickButtonDetailCatalogMode() {
        $x(detailCatalogModeButton)
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .should(visible, WAITING_TIME)
                .click();
        return this;
    }

    /**
     * the method checks the products after filtering by the selected parameters
     *
     * @param brand
     * @param diagonal
     * @param processor
     * @return this Page
     */
    public ResultsPage checkProductsAfterFiltration(String brand, String diagonal, String processor) {
        assertAll(
                () -> assertTrue(checkParamProductsAfterFilter(horizontalSnippetsProducts
                        , horizontalSnippetsProductsTitles, brand)
                        .isEmpty(), brand + "\n" + listToString(notMatchFilterProductsList)),
                () -> assertTrue(checkParamProductsAfterFilter(horizontalSnippetsProducts
                        , horizontalSnippetsProductsDisplay, diagonal)
                        .isEmpty(), diagonal + "\n" + listToString(notMatchFilterProductsList)),
                () -> assertTrue(checkParamProductsAfterFilter(horizontalSnippetsProducts
                        , horizontalSnippetsProductsProcessor, processor)
                        .isEmpty(), processor + "\n" + listToString(notMatchFilterProductsList)));
        return this;
    }

    private List<String> checkParamProductsAfterFilter(String baseXpath, String paramXpath, String param) {
        notMatchFilterProductsList.clear();
        int count = 1;
        for (SelenideElement element : createElementsCollection(baseXpath + paramXpath)) {
            if (element.getText().toLowerCase(Locale.ROOT).contains(param.toLowerCase(Locale.ROOT))) ;
            else {
                notMatchFilterProductsList.add(count + ") there is no match with the filter by " + param + ": "
                        + element.getText());
            }
            count++;
        }
        return notMatchFilterProductsList;
    }

    private String listToString(List<String> list) {
        return String.join("\n", list);
    }

    private ElementsCollection createElementsCollection(String xPath) {
        return $$x(xPath).should(CollectionCondition.sizeGreaterThan(0));
    }
}