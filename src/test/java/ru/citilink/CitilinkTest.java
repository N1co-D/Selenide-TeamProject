package ru.citilink;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.citilink.pages.CartPage;
import ru.citilink.pages.ComparePage;
import ru.citilink.pages.MainPage;
import ru.citilink.pages.ResultsPage;
import ru.citilink.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class CitilinkTest extends BaseTest {
    private final MainPage mainPage = new MainPage();
    private final ResultsPage resultsPage = new ResultsPage();
    private final ComparePage comparePage = new ComparePage();
    private final CartPage cartPage = new CartPage();
    private final ConfProperties confProperties = new ConfProperties();

    @ParameterizedTest
    @MethodSource("ru.citilink.CitilinkTestData#dataForComparisonTest")
    public void checkAddProductToCompare(String testLaptop, String productCategory) {
        open(confProperties.getProperty("test-site"));
        mainPage.inputBoxWriteText("lenovo").productSearchExtraResultListClick(productCategory);
        assertEquals(productCategory, resultsPage.getSubcategoryPageTitle(),
                String.format("Указан заголовок некорректной страницы. Ожидаем = %s, факт = %s",
                        productCategory, resultsPage.getSubcategoryPageTitle()));

        resultsPage.detailedCatalogModeButtonClick().comparingCurrentProductButtonClick(testLaptop);
        assertTrue(mainPage.compareValueIsDisplayed(), "Товар не добавлен в сравнение");
        String priceOfCurrentProduct = resultsPage.getPriceOfCurrentProduct(testLaptop);
        mainPage.compareButtonClick();

        assertAll(
                () -> assertEquals("Сравнение товаров", comparePage.getComparePageTitle(),
                        String.format("Указан заголовок некорректной страницы. Ожидаем = Сравнение товаров, факт = %s",
                                comparePage.getComparePageTitle())),
                () -> assertEquals(testLaptop, comparePage.getProductTitle(),
                        String.format("Товар для сравнения не корректный. Ожидаем = %s, факт = %s",
                                testLaptop, comparePage.getComparePageTitle())),
                () -> assertEquals(priceOfCurrentProduct, comparePage.getProductPrice(),
                        String.format("Цена товара указанна не корректно. Ожидаем = %s, факт = %s",
                                priceOfCurrentProduct, comparePage.getComparePageTitle())));
    }

    @ParameterizedTest
    @MethodSource("ru.citilink.CitilinkTestData#checkFilterProductsByParametersTestData")
    public void checkFilterProductsByParameters(String categoryName,
                                                String brandFilterCategory,
                                                String screenDiagonalFilterCategory,
                                                String processorSeriesFilterCategory,
                                                String brandName,
                                                String diagonalValue,
                                                String cpuValue) {

        open(confProperties.getProperty("test-site"));

        mainPage.clickPopularCategoryTile(categoryName);

        resultsPage.clickFilterDropDownCategoryAndValue(brandFilterCategory, brandName)
                .clickFilterDropDownCategoryAndValue(screenDiagonalFilterCategory, diagonalValue)
                .clickFilterDropDownCategoryAndValue(processorSeriesFilterCategory, cpuValue)
                .clickDetailCatalogModeButton()
                .checkProductsAfterFiltration(brandName, diagonalValue, cpuValue);
    }

    @ParameterizedTest
    @MethodSource("ru.citilink.CitilinkTestData#checkIncreaseInQuantityWhenAddProductsToCartTestData")
    public void checkIncreaseInQuantityWhenAddProductsToCart(String inputText,
                                                             String rawMemoryRequiredParameter,
                                                             String diskRequiredParameter,
                                                             int expectedAmountOfProduct) {
        open(confProperties.getProperty("test-site"));

        mainPage.checkIfCorrectPageOpen()
                .searchProductByInputBox(inputText);

        resultsPage.checkIfCorrectPageOpen()
                .enableDetailedCatalogMode()
                .requiredProductWithParametersBuyingClick(rawMemoryRequiredParameter, diskRequiredParameter)
                .checkAppearWindowWithAddedProductInCartStatus()
                .closeWindowWithAddedProductInCartStatusClick()
                .checkDisappearWindowWithAddedProductInCartStatus()
                .cartButtonClick();

        cartPage.checkIfCorrectPageOpen()
                .increaseTheAmountOfProductInCartButtonClick();
        assertEquals(String.valueOf(expectedAmountOfProduct),
                cartPage.getAmountOfProductInCart(),
                String.format("Фактическое количество товаров в корзине = %s не соответствует ожидаемому = %s",
                        cartPage.getAmountOfProductInCart(), expectedAmountOfProduct));
    }

    @ParameterizedTest
    @MethodSource("ru.citilink.CitilinkTestData#dataForComparisonTest")
    public void checkRemoveProductFromComparison(String testLaptop, String productCategory) {
        open(confProperties.getProperty("test-site"));
        mainPage.inputBoxWriteText("lenovo")
                .productSearchExtraResultListClick(productCategory);
        assertEquals(productCategory, resultsPage.getSubcategoryPageTitle(),
                String.format("Указан заголовок некорректной страницы. Ожидаем = %s, факт = %s",
                        productCategory, resultsPage.getSubcategoryPageTitle()));

        resultsPage.detailedCatalogModeButtonClick()
                .comparingCurrentProductButtonClick(testLaptop);
        assertTrue(mainPage.compareValueIsDisplayed(), "Товар не добавлен в сравнение");
        mainPage.compareButtonClick();
        assertAll(
                () -> assertEquals("Сравнение товаров", comparePage.getComparePageTitle(),
                        String.format("Указан заголовок некорректной страницы. Ожидаем = Сравнение товаров, факт = %s",
                                comparePage.getComparePageTitle())),
                () -> assertEquals(testLaptop, comparePage.getProductTitle(),
                        String.format("Товар для сравнения не корректный. Ожидаем = %s, факт = %s",
                                testLaptop, comparePage.getComparePageTitle())));

        comparePage.deleteProductButtonClick();
        assertAll(
                () -> assertFalse(comparePage.compareValueIsDisplayed(), "Товар не удалён из сравнение"),
                () -> assertTrue(comparePage.noProductsForCompareIsDisplayed(), "Отсутствует уведомление Нет товаров для сравнения"));
    }

    @ParameterizedTest
    @MethodSource("ru.citilink.CitilinkTestData#checkProductAddToCompareSectionTestData")
    public void checkProductAddToCompareSection(int amountOfProductsForAdding) {
        open(confProperties.getProperty("test-site"));

        mainPage.checkIfCorrectPageOpen()
                .productCatalogClick()
                .televisionsAndAudioVideoEquipmentCategoryClick()
                .oledTelevisionsCategoryClick();

        resultsPage.checkIfCorrectPageOpen()
                .enableDetailedCatalogMode()
                .someProductAddToComparingClick(amountOfProductsForAdding)
                .checkAmountOfAddedProductsToCompare(amountOfProductsForAdding)
                .comparingButtonClick();

        comparePage.checkIfCorrectPageOpen()
                .checkAmountOfAddedProductsToCompare(amountOfProductsForAdding);
    }

    @ParameterizedTest
    @MethodSource("ru.citilink.CitilinkTestData#checkProductAddToCartTestData")
    public void checkProductAddToCart(String inputText,
                                      String productFromDropDownList,
                                      String observedProduct,
                                      String expectedProductCode) {
        open(confProperties.getProperty("test-site"));

        mainPage.checkIfCorrectPageOpen()
                .inputBoxWriteText(inputText)
                .clickOnProductFromDropDownList(productFromDropDownList);

        resultsPage.checkIfCorrectPageOpen()
                .enableDetailedCatalogMode()
                .requiredProductBuyingClick(observedProduct)
                .checkAppearWindowWithAddedProductInCartStatus()
                .goToCartButtonClickWithPopupWindow();

        cartPage.checkIfCorrectPageOpen()
                .checkIsCorrectCodeNumberOfProductInCart(expectedProductCode);
    }

    @ParameterizedTest
    @MethodSource("ru.citilink.CitilinkTestData#checkTheDeletingOfProductFromCartTestData")
    public void checkTheDeletingOfProductFromCart(String inputText,
                                                  String productFromDropDownList,
                                                  String observedProduct) {
        open(confProperties.getProperty("test-site"));

        mainPage.checkIfCorrectPageOpen()
                .inputBoxWriteText(inputText)
                .clickOnProductFromDropDownList(productFromDropDownList);

        resultsPage.checkIfCorrectPageOpen()
                .enableDetailedCatalogMode()
                .requiredProductBuyingClick(observedProduct)
                .checkAppearWindowWithAddedProductInCartStatus()
                .closeWindowWithAddedProductInCartStatusClick()
                .checkDisappearWindowWithAddedProductInCartStatus()
                .returnToMainPage();

        mainPage.checkIfCorrectPageOpen()
                .cartButtonClick();

        cartPage.checkIfCorrectPageOpen()
                .deleteRequiredProductInCartButtonClick(observedProduct)
                .checkIsVisibleStatusOfMissingProductsInCart()
                .goBackToShoppingButtonClick();

        mainPage.checkIfCorrectPageOpen();
    }

    @ParameterizedTest
    @MethodSource("ru.citilink.CitilinkTestData#checkItemAddToCartTestData")
    public void checkItemAddToCart(String productName) {
        open(confProperties.getProperty("test-site"));

        mainPage.enterSearchProductInputLine(productName);
        resultsPage
                .clickAddItemToBasketButton(productName)
                .clickCloseUpSaleBasketLayoutButton()
                .clickBasketFresnelContainerButton();

        assertEquals(productName, cartPage.getNameProductFromBasketSnippet(productName));
    }

    @ParameterizedTest
    @MethodSource("ru.citilink.CitilinkTestData#checkProductCompareTestData")
    public void checkProductCompare(String brandName,
                                    String productName,
                                    String maxPrice,
                                    String engineType,
                                    String engineTypeValue,
                                    String screwShape,
                                    String screwShapeValue) {

        open(confProperties.getProperty("test-site"));

        mainPage.checkIfCorrectPageOpen()
                .enterProductInputLineAndClickSearchButton(brandName);

        resultsPage.checkIfCorrectPageOpen()
                .enterFilterDropdownInputMaxPrice(maxPrice)
                .clickDetailCatalogModeButton()
                .clickCollectionCompareProductButton()
                .clickFresnelContainerCompareButton();

        comparePage.clickSelectedProductCartButton(engineType, engineTypeValue, screwShape, screwShapeValue)
                .clickUpsaleBasketBlockGoShopCartButton();

        cartPage.checkProductNameInCart(productName);
    }

    @ParameterizedTest
    @MethodSource("ru.citilink.CitilinkTestData#dataForCheckCorrectReflection")
    public void checkCorrectReflectionProductNameInAccordanceWithFilterParameters(String searchedProduct,
                                                                                  String sortingParameter,
                                                                                  String rating,
                                                                                  String observedProduct,
                                                                                  String category) {
        open(confProperties.getProperty("test-site"));
        mainPage.inputBoxWriteText(searchedProduct).searchButtonClick();
        assertEquals(String.format("Результаты для «%s»", searchedProduct), resultsPage.getSearchTitle(),
                String.format("Указан заголовок некорректной страницы. Ожидаем = Результаты для «%s», факт = %s",
                        searchedProduct, resultsPage.getSearchTitle()));

        resultsPage.detailedCatalogModeButtonClick()
                .laptopCategoryButtonClick();
        assertEquals(category, resultsPage.getCategoryTitle(),
                String.format("Указана не верная категория. Ожидаем = %s, факт = %s",
                        category, resultsPage.getCategoryTitle()));

        resultsPage.sortingItemClick(sortingParameter)
                .sortingItemStatus(sortingParameter);
        assertTrue(resultsPage.sortingPriceResult(), "Цена в списке идёт не по возрастанию");

        resultsPage.feedbackFilterClick(rating)
                .feedbackFilterStatus(rating);

        resultsPage.buyFirstProductFromList()
                .checkAppearWindowWithAddedProductInCartStatus()
                .goToCartButtonClickWithPopupWindow();

        cartPage.checkProductTitleCart(observedProduct);
    }
}