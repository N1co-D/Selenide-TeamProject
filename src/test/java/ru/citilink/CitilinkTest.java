package ru.citilink;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
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
    private final CartPage cartPage = new CartPage();
    private final ComparePage comparePage = new ComparePage();
    private final ConfProperties confProperties = new ConfProperties();

    @ParameterizedTest
    @CsvSource({"'Ноутбук Huawei MateBook D 14 53013XFA, 14', '8 ГБ, LPDDR4x', 'SSD 512 ГБ', '2'"})
    public void checkTheIncreaseInQuantityWhenAddingProductsToCart(String inputText,
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
    @CsvSource({
            "'Ноутбук Lenovo IdeaPad 1 15AMN7 82VG00LSUE, 15.6\", TN, AMD Ryzen 3 7320U, 4-ядерный, 8ГБ LPDDR5, 256ГБ SSD, AMD Radeon 610M, серый', Ноутбуки Lenovo"
    })
    public void checkAddProductToCompare(String testLaptop, String productCategory) {
        open(confProperties.getProperty("test-site"));
        mainPage.inputBoxWriteText("lenovo").productSearchExtraResultListClick(productCategory);
        assertEquals(resultsPage.getSubcategoryPageTitle(), productCategory,
                String.format("Указан заголовок некорректной страницы. Ожидаем = %s, факт = %s",
                        productCategory, resultsPage.getSubcategoryPageTitle()));

        resultsPage.detailedCatalogModeButtonClick().comparingCurrentProductButtonClick(testLaptop);
        assertTrue(mainPage.compareValueIsDisplayed(), "Товар не добавлен в сравнение");
        String priceOfCurrentProduct = resultsPage.getPriceOfCurrentProduct(testLaptop);
        mainPage.compareButtonClick();

        assertAll(
                () -> assertEquals(comparePage.getComparePageTitle(), "Сравнение товаров",
                        String.format("Указан заголовок некорректной страницы. Ожидаем = Сравнение товаров, факт = %s",
                                comparePage.getComparePageTitle())),
                () -> assertEquals(comparePage.getProductTitle(), testLaptop,
                        String.format("Товар для сравнения не корректный. Ожидаем = %s, факт = %s",
                                testLaptop, comparePage.getComparePageTitle())),
                () -> assertEquals(comparePage.getProductPrice(), priceOfCurrentProduct,
                        String.format("Цена товара указанна не корректно. Ожидаем = %s, факт = %s",
                                priceOfCurrentProduct, comparePage.getComparePageTitle())));
    }

    @ParameterizedTest
    @CsvSource({"'Переходники', 'Переходники на евровилку', " +
            "'Адаптер-переходник на евровилку PREMIER 11626/20, темно-серый'"})
    public void checkingTheDeletingOfProductFromCart(String inputText,
                                                     String productFromDropDownList,
                                                     String observedProduct) {
        open(confProperties.getProperty("test-site"));

        mainPage.checkIfCorrectPageOpen()
                .writeTextInInputBox(inputText)
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
}