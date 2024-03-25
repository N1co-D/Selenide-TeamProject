package ru.citilink.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.UIAssertionError;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

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
    private final String closeWindowWithAddedProductInCartStatus = "//button[@data-meta-name='UpsaleBasket__close-popup']"; // todo overload
    private final String closeUpSaleBasketLayoutButton = "//div[@data-meta-name='UpsaleBasketLayout']/button[contains(@data-meta-name,'close')]"; // todo overload
    private final String cartButton = "//div[@data-meta-name='HeaderBottom__search']/following-sibling::div//div[@data-meta-name='BasketButton']";
    private final String logo = "//div[contains(@class,'fresnel-greaterThanOrEqual-tabletL')]//div[@data-meta-name='Logo']";
    private final String productTitle = ".//a[@data-meta-name='Snippet__title']";
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
    private final String goToCartButton = "//span[text()='Перейти в корзину']/preceding::span[text()='Перейти в корзину']";
    private final String addItemToBasketButton = "//a[contains(normalize-space(text()),'%s')]/ancestor::div[contains(@data-meta-name,'ProductVerticalSnippet')]//button[contains(@data-meta-name,'Snippet__cart')]";
    private final String basketFresnelContainerButton = "//div[@data-meta-name='UserButtonContainer']/following-sibling::a/div[@data-meta-name='BasketButton']";
    private final String productListLayout = "//div[@data-meta-name='ProductListLayout']";
    private final String laptopCategoryButton = "//span[text()= 'Ноутбуки']";
    private final String searchTitle = "//h1[contains(text(), 'Результаты для «')]";
    private final String categoryTitle = "//div[@data-meta-name='ProductListLayout']//h1";
    private final String sortingItem = "//button[@data-meta-name='SortingItem']";
    private final String searchResultsList = "//div[@data-meta-name='SnippetProductHorizontalLayout']";
    private final String searchResultsPrice = ".//span[@data-meta-price]";
    private final String feedbackFilter = "//div[@data-meta-value='Оценка товара по отзывам']//div[@data-meta-name='FilterLabel']";
    private final String filterDropdownInputMaxPrice = "//div[@data-meta-name='FilterListGroupsLayout']//div[contains(@data-meta-value,'Цена')]//div/input[@name = 'input-max']";
    private final String currentProductCompareButton = ".//button[@data-meta-name='Snippet__compare-button']";
    private final String fresnelContainerCompareButton = "//div[@data-meta-name='UserButtonContainer']/following-sibling::div//div[@data-meta-name='CompareButton']";
    private final String dropDownlistShowMoreButton = "//div/a[contains(text(),'%s')]/../..//span[contains(text(),'Показать еще')]";
    private final String underSubcategoryButton = "//a[contains(text(),'%s')]/../..//a[contains(text(),'%s')]";
    private final String addFirstItemToBasketButton = "//div[@data-meta-name='ProductVerticalSnippet'][1]//button[@data-meta-name='Snippet__cart-button']";
    private final String upsaleBasketBlockGoShopCartButton = "//div[contains(@data-meta-name,'UpsaleBasketLayout')]/div[2]//span[contains(text(),'Перейти в корзину')]";

    @Step("Открытие страницы с результатами запроса / выбранной категории")
    public ResultsPage checkIfCorrectPageOpen() {
        try {
            $x(filter).should(visible, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не удалось подтвердить открытие ожидаемой страницы. " +
                    "Уникальный элемент страницы 'filter' не был найден в течение заданного времени.");
        }
        makeScreenshot();
        return this;
    }

    @Step("Включение подробного режима каталога")
    public ResultsPage enableDetailedCatalogMode() { // todo overload: clickDetailCatalogModeButton()
        jsClick($x(detailedCatalogModeButton));
        makeScreenshot();
        return this;
    }

    private ElementsCollection getAllProductsInPage() {
        return $$x(productList).should(sizeGreaterThan(0));
    }

    private String getRamMemoryParameterOfProduct() { // todo rename: getProductRamMemoryParam
        return $x(ramMemoryParameterOfProduct).should(visible, WAITING_TIME)
                .getText();
    }

    private String getDiskParameterOfProduct() { // todo rename: getProductDiskParam
        return $x(diskParameterOfProduct).should(visible, WAITING_TIME)
                .getText();
    }

    @Step("Переход на главную страницу через логотип сайта")
    public ResultsPage returnToMainPage() {
        jsClick($x(logo));
        return this;
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

    @Step("Добавление подходящего под параметры ('{firstParameter}' и '{secondParameter}') товара в корзину")
    public ResultsPage requiredProductWithParametersBuyingClick(String firstParameter, String secondParameter) { //todo rename: click...
        SelenideElement requiredProduct = searchForRequiredProductInList(firstParameter, secondParameter);
        jsClick(requiredProduct.$x(inCartButton));
        return this;
    }

    @Step("Переход на страницу корзины через верхнее меню")
    public ResultsPage cartButtonClick() { // todo rename: clickCartButton
        jsClick($x(cartButton));
        return this;
    }

    @Step("Закрытие всплывающего окна со статусом успешного добавления товара в корзину")
    public ResultsPage closeWindowWithAddedProductInCartStatusClick() { // todo overload: clickCloseUpSaleBasketLayoutButton()
        jsClick($x(closeWindowWithAddedProductInCartStatus));
        return this;
    }

    @Step("Появление всплывающего окна со статусом успешного добавления товара в корзину")
    public ResultsPage checkAppearWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).should(appear, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не было обнаружено всплывающее окно с сообщением о добавлении товара в корзину");
        }
        makeScreenshot();
        return this;
    }

    @Step("Пропадание всплывающего окна со статусом успешного добавления товара в корзину")
    public ResultsPage checkDisappearWindowWithAddedProductInCartStatus() {
        try {
            $x(windowWithAddedProductInCartStatus).shouldNot(appear, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Всплывающее окно с сообщением о добавлении товара в корзину не было закрыто " +
                    "после нажатия соответствующего элемента 'closeWindowWithAddedProductInCartStatus'");
        }
        makeScreenshot();
        return this;
    }

    public String getSubcategoryPageTitle() {
        return $x(subcategoryPageTitle).shouldBe(visible, WAITING_TIME).getText();
    }

    public String getPriceOfCurrentProduct(String nameData) {//todo rename: getCurrentPriceProduct
        return $$x(productList).shouldBe(sizeGreaterThan(0), WAITING_TIME)
                .findBy(text(nameData)).$x(priceOfCurrentProduct).getText();
    }

    public ResultsPage comparingCurrentProductButtonClick(String nameData) {//todo rename: clickCompareCurrentProductButton
        jsClick($$x(productList).shouldBe(sizeGreaterThan(0), WAITING_TIME)
                .findBy(text(nameData)).$x(comparingCurrentProductButton));
        return this;
    }

    public ResultsPage detailedCatalogModeButtonClick() {//todo rename: clickDetailCatalogModeButton
        $x(detailedCatalogModeButton).shouldBe(visible, WAITING_TIME).click();
        assertEquals("list", $x(productListLayout)
                .shouldHave(attribute("data-meta-view-type", "list"), WAITING_TIME)
                .getAttribute("data-meta-view-type"), "Подробный каталог не активен");
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

    @Step("Добавление {amountOfProductsForAdding} первых товара в список сравнения")
    public ResultsPage someProductAddToComparingClick(int amountOfProductsForAdding) { //todo rename: clickProductAddToCompareButton
        ElementsCollection allProductsFromList = getAllProductsInPage();
        sleep(3000);
        for (int countOfAddedProducts = 0; countOfAddedProducts < amountOfProductsForAdding; countOfAddedProducts++) {
            allProductsFromList.get(countOfAddedProducts).should(visible, WAITING_TIME);
            jsClick(allProductsFromList.get(countOfAddedProducts).$x(addToCompareButton));
        }
        return this;
    }

    private String getAmountOfAddedProductsToCompare() { // todo rename: getAmountProductsAddToCompare
        return $x(amountOfAddedProductsToCompare).should(visible, WAITING_TIME)
                .getText();
    }

    @Step("Соответствие количества добавленного товара в список сравнения с ожидаемым значением = {expectedAmountOfProductsForAdding}")
    public ResultsPage checkAmountOfAddedProductsToCompare(int expectedAmountOfProductsForAdding) { // todo rename: checkAmountProductsAddToCompare
        String actualAmountOfProduct = getAmountOfAddedProductsToCompare();
        assertEquals(actualAmountOfProduct,
                String.valueOf(expectedAmountOfProductsForAdding),
                String.format("Фактическое количество добавленных для сравнения товаров = %s " +
                                "не соответствует ожидаемому = %s",
                        actualAmountOfProduct, expectedAmountOfProductsForAdding));
        makeScreenshot();
        return this;
    }

    @Step("Переход на страницу сравнения товаров через верхнее меню")
    public ResultsPage comparingButtonClick() { // todo rename: clickCompareButton
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

    @Step("Добавление подходящего по названию ('{observedProduct}') товара в корзину")
    public ResultsPage requiredProductBuyingClick(String observedProduct) { // todo rename: clickRequiredProductBuyButton
        SelenideElement foundRequiredProduct = searchForRequiredProductInList(observedProduct);
        jsClick(foundRequiredProduct.$x(inCartButton));
        return this;
    }

    @Step("Переход на страницу корзины через всплывающее окно со статусом успешного добавления товара в корзину")
    public ResultsPage goToCartButtonClickWithPopupWindow() { // todo rename: clickGoToCartWithPopupWindowButton
        jsClick($x(goToCartButton));
        return this;
    }

    public ResultsPage clickAddItemToBasketButton(String nameProduct) {
        $x(String.format(addItemToBasketButton, nameProduct))
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .should(visible, WAITING_TIME)
                .click();
        return this;
    }

    public ResultsPage clickCloseUpSaleBasketLayoutButton() {
        $x(closeUpSaleBasketLayoutButton)
                .should(visible, WAITING_TIME)
                .click();
        return this;
    }

    public ResultsPage clickBasketFresnelContainerButton() {
        $x(basketFresnelContainerButton)
                .should(visible, WAITING_TIME)
                .click();
        return this;
    }

    public String getSearchTitle() {
        return $x(searchTitle).shouldBe(visible, WAITING_TIME).getText();
    }

    public ResultsPage laptopCategoryButtonClick() {
        $x(laptopCategoryButton).shouldBe(visible, WAITING_TIME).click();
        return this;
    }

    public String getCategoryTitle() {
        return $x(categoryTitle).shouldBe(visible, WAITING_TIME).getText();
    }

    public ResultsPage sortingItemClick(String sortingParameter) {
        $$x(sortingItem).shouldBe(sizeGreaterThan(0), WAITING_TIME).findBy(text(sortingParameter)).click();
        return this;
    }

    public ResultsPage sortingItemStatus(String sortingParameter) {
        assertTrue(Boolean.parseBoolean($$x(sortingItem).shouldBe(sizeGreaterThan(0), WAITING_TIME)
                        .findBy(text(sortingParameter))
                        .should(attribute("data-meta-is-selected", "true"), WAITING_TIME)
                        .getAttribute("data-meta-is-selected")),
                String.format("Сортировка \" %s \" не активна", sortingParameter));
        return this;
    }

    public boolean sortingPriceResult() {
        ElementsCollection selenideElements = $$x(searchResultsList).shouldBe(sizeGreaterThan(0), WAITING_TIME);
        for (int i = 0; i < selenideElements.size() - 1; i++) {
            if (Integer.parseInt(selenideElements.get(i).$x(searchResultsPrice).getAttribute("data-meta-price"))
                    > Integer.parseInt(selenideElements.get(i + 1).$x(searchResultsPrice).getAttribute("data-meta-price")))
                return false;
        }
        return true;
    }

    public ResultsPage feedbackFilterClick(String rating) {
        $$x(feedbackFilter).shouldBe(sizeGreaterThan(0), WAITING_TIME)
                .findBy(attribute("data-meta-value", rating))
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .click();
        return this;
    }

    public ResultsPage feedbackFilterStatus(String rating) {
        assertTrue(Boolean.parseBoolean($$x(feedbackFilter).shouldBe(sizeGreaterThan(0), WAITING_TIME)
                        .findBy(attribute("data-meta-value", rating))
                        .should(attribute("data-meta-is-selected", "true"), WAITING_TIME)
                        .getAttribute("data-meta-is-selected")),
                String.format("Оценка товара по отзывам \" %s \" не активна", rating));
        return this;
    }

    public ResultsPage buyFirstProductFromList() {
        $$x(searchResultsList).shouldBe(sizeGreaterThan(0), WAITING_TIME).first().$x(inCartButton).click();
        return this;
    }

    public ResultsPage enterFilterDropdownInputMaxPrice(String price) {
        $x(filterDropdownInputMaxPrice)
                .should(visible, WAITING_TIME)
                .val(price).pressEnter();
        return this;
    }

    public ResultsPage clickDetailCatalogModeButton() {
        $x(detailCatalogModeButton)
                .scrollIntoView("{behavior: \"auto\", block: \"center\", inline: \"nearest\"}")
                .should(interactable, WAITING_TIME)
                .click();
        return this;
    }

    public ResultsPage clickCollectionCompareProductButton() {
        for (SelenideElement element : createElementsCollection(horizontalSnippetsProducts)) {
            element.$x(currentProductCompareButton)
                    .scrollIntoView("{behavior: \"auto\", block: \"center\", inline: \"center\"}")
                    .shouldBe(visible, WAITING_TIME)
                    .click();
        }
        return this;
    }

    public ResultsPage clickFresnelContainerCompareButton() {
        $x(fresnelContainerCompareButton)
                .scrollIntoView("{behavior: \"auto\", block: \"center\", inline: \"nearest\"}")
                .should(visible, WAITING_TIME)
                .click();
        return this;
    }

    private ElementsCollection createElementsCollection(String xPath) {
        return $$x(xPath).should(CollectionCondition.sizeGreaterThan(0), WAITING_TIME);
    }

    public ResultsPage clickDropDownlistShowMoreButton(String subcategoryname) {
        $x(String.format(dropDownlistShowMoreButton, subcategoryname))
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .should(visible, WAITING_TIME)
                .click();
        return this;
    }

    public ResultsPage clickUnderSubcategoryButton(String subcategoryName, String underSubcategoryName) {
        $x(String.format(underSubcategoryButton, subcategoryName, underSubcategoryName))
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .should(visible, WAITING_TIME)
                .click();
        return this;
    }

    public ResultsPage clickAddFirstItemToBasketButton() {
        $x(addFirstItemToBasketButton)
                .scrollIntoView("{behavior: \"instant\", block: \"center\", inline: \"nearest\"}")
                .should(visible, WAITING_TIME)
                .click();
        return this;
    }

    public void clickUpsaleBasketBlockGoShopCartButton() {
        $x(upsaleBasketBlockGoShopCartButton)
                .shouldBe(visible, WAITING_TIME)
                .click();
    }
}