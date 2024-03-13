package ru.citilink.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.UIAssertionError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.fail;

/**
 * Страница с результатами "Результаты [поиска] для <запрос пользователя>" в Citilink
 * Страница с результатами "[Категория товаров]" в Citilink
 */
public class ResultsPage extends BasePage {
    private final String filter = "//div[@data-meta-name='FiltersLayout']";
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
    private final String addToCompareButton = ".//button[@data-meta-name='Snippet__compare-button']";
    private final String comparingButton = "//div[@data-meta-name='HeaderBottom__search']/following-sibling::div//div[@data-meta-name='CompareButton']";
    private final String amountOfAddedProductsToCompare = "//div[contains(@class,'fresnel-greaterThanOrEqual')]//div[@data-meta-name='CompareButton']//div[@data-meta-name='NotificationCounter']";
    private final String productTitle = ".//a[@data-meta-name='Snippet__title']";
    private final String goToCartButton = "//span[text()='Перейти в корзину']/preceding::span[text()='Перейти в корзину']";
    private final String filterDropdownInputMaxPrice = "//div[@data-meta-name='FilterListGroupsLayout']//div[contains(@data-meta-value,'Цена')]//div/input[@name = 'input-max']";
    private final String detailCatalogModeButton = "//label[contains(@for,'Подробный режим каталога')]";
    private final String horizontalSnippetsProducts = "//div[@data-meta-name='ProductHorizontalSnippet']";
    private final String currentProductCompareButton = ".//button[@data-meta-name='Snippet__compare-button']";
    private final String fresnelContainerCompareButton = "//div[@data-meta-name='UserButtonContainer']/following-sibling::div//div[@data-meta-name='CompareButton']";
    //div[contains(text(),'Марка топлива')]/following-sibling::div[@class="Compare__specification-row_wrapper"]/div/div
    public ResultsPage checkIfCorrectPageOpen() {
        try {
            $x(filter).should(visible, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не удалось подтвердить открытие ожидаемой страницы. " +
                    "Уникальный элемент страницы 'filter' не был найден в течение заданного времени.");
        }
        return this;
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

    private SelenideElement searchForRequiredProductInList(String firstParameter, String secondParameter) {
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

    public ResultsPage requiredProductWithParametersBuyingClick(String firstParameter, String secondParameter) {
        SelenideElement requiredProduct = searchForRequiredProductInList(firstParameter, secondParameter);
        jsClick(requiredProduct.$x(inCartButton));
        return this;
    }

    public ResultsPage cartButtonClick() {
        jsClick($x(cartButton));
        return this;
    }

    public ResultsPage closeWindowWithAddedProductInCartStatusClick() {
        jsClick($x(closeWindowWithAddedProductInCartStatus));
        return this;
    }

    public ResultsPage checkAppearWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).should(appear, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не было обнаружено всплывающее окно с сообщением о добавлении товара в корзину");
        }
        return this;
    }

    public ResultsPage checkDisappearWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).shouldNot(appear, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Всплывающее окно с сообщением о добавлении товара в корзину не было закрыто " +
                    "после нажатия соответствующего элемента 'closeWindowWithAddedProductInCartStatus'");
        }
        return this;
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
        return $$x(xPath).should(sizeGreaterThan(0));
    }

    public ResultsPage someProductAddToComparingClick(int amountOfProductsForAdding) {
        ElementsCollection allProductsFromList = getAllProductsInPage();
        for (int countOfAddedProducts = 0; countOfAddedProducts < amountOfProductsForAdding; countOfAddedProducts++) {
            allProductsFromList.get(countOfAddedProducts).should(visible, WAITING_TIME);
            jsClick(allProductsFromList.get(countOfAddedProducts).$x(addToCompareButton));
        }
        return this;
    }

    private String getAmountOfAddedProductsToCompare() {
        return $x(amountOfAddedProductsToCompare).should(visible, WAITING_TIME)
                .getText();
    }

    public ResultsPage checkAmountOfAddedProductsToCompare(int expectedAmountOfProductsForAdding) {
        assertEquals(getAmountOfAddedProductsToCompare(),
                String.valueOf(expectedAmountOfProductsForAdding),
                String.format("Фактическое количество добавленных для сравнения товаров = %s " +
                                " не соответствует ожидаемому = %s",
                        getAmountOfAddedProductsToCompare(), expectedAmountOfProductsForAdding));
        return this;
    }

    public ResultsPage comparingButtonClick() {
        jsClick($x(comparingButton));
        return this;
    }

    private SelenideElement searchForRequiredProductInList(String observedProduct) {
        ElementsCollection allProductsFromList = getAllProductsInPage();
        SelenideElement foundProduct = null;
        SelenideElement currentProductTitleElement;
        for (SelenideElement product : allProductsFromList) {
            currentProductTitleElement = getElementWithCurrentProductTitle(product);
            if (currentProductTitleElement.getText().contains(observedProduct)) {
                foundProduct = product;
                break;
            }
        }
        if (foundProduct == null) {
            fail("Продукт с названием, содержащим '" + observedProduct + "' , не был найден в списке");
        }
        return foundProduct;
    }

    private SelenideElement getElementWithCurrentProductTitle(SelenideElement product) {
        $x(productTitle).should(visible, WAITING_TIME);
        return product.$x(productTitle);
    }

    public ResultsPage requiredProductBuyingClick(String observedProduct) {
        SelenideElement foundRequiredProduct = searchForRequiredProductInList(observedProduct);
        jsClick(foundRequiredProduct.$x(inCartButton));
        return this;
    }

    public ResultsPage goToCartButtonClickWithPopupWindow() {
        jsClick($x(goToCartButton));
        return this;
    }
    public ResultsPage enterFilterDropdownInputMaxPrice(String price) {
        $x(filterDropdownInputMaxPrice)
                .should(visible, WAITING_TIME)
                .val(price).pressEnter();
        return this;
    }

    public ResultsPage clickButtonDetailCatalogMode() {
        $x(detailCatalogModeButton)
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .should(visible, WAITING_TIME)
                .click();
        return this;
    }

    public ResultsPage clickCompareProductButtonFromCollection() {
        for (SelenideElement element : createElementsCollection(horizontalSnippetsProducts)) {
            sleep(2000);
            element.$x(currentProductCompareButton)
                    .scrollIntoView("{behavior: \"smooth\", block: \"center\", inline: \"nearest\"}")
                    .shouldBe(interactable, WAITING_TIME)
                    .click();
            sleep(2000);
        }
        return this;
    }
    public ResultsPage clickFresnelContainerCompareButton() {
        $x(fresnelContainerCompareButton)
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .should(visible, WAITING_TIME)
                .click();
        return this;
    }

    private ElementsCollection createElementsCollection(String xPath) {
        return $$x(xPath).should(CollectionCondition.sizeGreaterThan(0), WAITING_TIME);
    }
}