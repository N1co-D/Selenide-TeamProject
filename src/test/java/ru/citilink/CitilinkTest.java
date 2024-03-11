package ru.citilink;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.citilink.pages.CartPage;
import ru.citilink.pages.MainPage;
import ru.citilink.pages.ResultsPage;
import ru.citilink.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CitilinkTest extends BaseTest {
    private final MainPage mainPage = new MainPage();
    private final ResultsPage resultsPage = new ResultsPage();
    private final CartPage cartPage = new CartPage();
    private final ConfProperties confProperties = new ConfProperties();

    @ParameterizedTest
    @CsvSource({"'Ноутбук Huawei MateBook D 14 53013XFA, 14', '8 ГБ, LPDDR4x', 'SSD 512 ГБ', '2'"})
    public void checkTheIncreaseInQuantityWhenAddingProductsToCart(String inputText, String rawMemoryRequiredParameter,
                                                                   String diskRequiredParameter, String expectedAmountOfProduct) {
        open(confProperties.getProperty("test-site"));
        assertTrue(mainPage.getPagesUniqueElement(),
                "Ошибка в открытии главной страницы");

        mainPage.searchProductByInputBox(inputText);
        assertTrue(resultsPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с результатами поиска");

        resultsPage.enableDetailedCatalogMode().requiredProductWithParametersBuyingClick(rawMemoryRequiredParameter,
                diskRequiredParameter);
        assertTrue(resultsPage
                        .checkAppearingWindowWithAddedProductInCartStatus(),
                "Ошибка в открытии всплывающего окна с сообщением о добавлении товара в корзину");

        resultsPage.closeWindowWithAddedProductInCartStatusClick();
        assertTrue(resultsPage
                        .checkDisappearingWindowWithAddedProductInCartStatus(),
                "Ошибка в закрытии всплывающего окна с сообщением о добавлении товара в корзину");

        resultsPage.cartButtonClick();
        assertTrue(cartPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с корзиной");

        cartPage.increaseTheAmountOfProductInCartButtonClick();
        assertEquals(cartPage.getAmountOfProductInCart(), expectedAmountOfProduct,
                "Ошибка в увеличении количества товара в корзине");
    }

    @ParameterizedTest
    @CsvSource({"'Ноутбуки','Бренд','Диагональ экрана','Серия процессора','HUAWEI','14','Core i7'"})
    public void checkFilterProductsByParameters(String noutbukiCategory, String brandFilterCategory
            , String screenDiagonalFilterCategory, String processorSeriesFilterCategory, String brandValue
            , String diagonalValue, String cpuValue) {
        open(confProperties.getProperty("test-site"));
        new MainPage().clickPopularCategoryTile(noutbukiCategory);
        new ResultsPage().clickFilterDropDownCategoryAndValue(brandFilterCategory, brandValue)
                .clickFilterDropDownCategoryAndValue(screenDiagonalFilterCategory, diagonalValue)
                .clickFilterDropDownCategoryAndValue(processorSeriesFilterCategory, cpuValue)
                .clickButtonDetailCatalogMode()
                .checkProductsAfterFiltration(brandValue, diagonalValue, cpuValue);
    }
}