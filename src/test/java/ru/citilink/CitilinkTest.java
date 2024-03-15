package ru.citilink;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
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
    @MethodSource("ru.citilink.CitilinkTestData#dataNoutbukFilterParam")
    public void checkFilterProductsByParameters(String noutbukiCategory,
                                                String brandFilterCategory,
                                                String screenDiagonalFilterCategory,
                                                String processorSeriesFilterCategory,
                                                String brandValue,
                                                String diagonalValue,
                                                String cpuValue) {
        open(confProperties.getProperty("test-site"));

        mainPage.clickPopularCategoryTile(noutbukiCategory);

        resultsPage.clickFilterDropDownCategoryAndValue(brandFilterCategory, brandValue)
                .clickFilterDropDownCategoryAndValue(screenDiagonalFilterCategory, diagonalValue)
                .clickFilterDropDownCategoryAndValue(processorSeriesFilterCategory, cpuValue)
                .clickDetailCatalogModeButton()
                .checkProductsAfterFiltration(brandValue, diagonalValue, cpuValue);
    }

    @ParameterizedTest
    @CsvSource({"'Ноутбук Huawei MateBook D 14 53013XFA, 14', '8 ГБ, LPDDR4x', 'SSD 512 ГБ', '2'"})
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
    @ValueSource(ints = {2})
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
    @CsvSource({"'Переходники', 'Переходники на евровилку', " +
            "'Адаптер-переходник на евровилку PREMIER 11626/20, темно-серый', '1860968'"})
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
    @CsvSource({"'Переходники', 'Переходники на евровилку', " +
            "'Адаптер-переходник на евровилку PREMIER 11626/20, темно-серый'"})
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
    @MethodSource("ru.citilink.CitilinkTestData#dataSmartphone")
    public void checkItemAddToCart(String huaweiNovaY72) {
        open(confProperties.getProperty("test-site"));

        mainPage.enterSearchProductInputLine(huaweiNovaY72);
        resultsPage
                .clickAddItemToBasketButton(huaweiNovaY72)
                .clickCloseUpSaleBasketLayoutButton()
                .clickBasketFresnelContainerButton();

        assertEquals(huaweiNovaY72, cartPage.getNameProductFromBasketSnippet(huaweiNovaY72));
    }

    @ParameterizedTest
    @MethodSource("ru.citilink.CitilinkTestData#dataSnowplow")
    public void checkProductCompare(String huterBrandName,
                                    String huterSGC4000L,
                                    String maxPrice,
                                    String engineType,
                                    String engineTypeBenzin,
                                    String screwShape,
                                    String screwShapeTooth) {
        open(confProperties.getProperty("test-site"));

        mainPage.checkIfCorrectPageOpen()
                .enterProductInputLineAndClickSearchButton(huterBrandName);

        resultsPage.checkIfCorrectPageOpen()
                .enterFilterDropdownInputMaxPrice(maxPrice)
                .clickDetailCatalogModeButton()
                .clickCollectionCompareProductButton()
                .clickFresnelContainerCompareButton();

        comparePage.clickSelectedProductCartButton(engineType, engineTypeBenzin, screwShape, screwShapeTooth)
                .clickUpsaleBasketBlockGoShopCartButton();

        cartPage.checkProductNameInCart(huterSGC4000L);
    }
}