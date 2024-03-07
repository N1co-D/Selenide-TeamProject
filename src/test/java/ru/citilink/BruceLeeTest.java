package ru.citilink;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.citilink.pages.CartPage;
import ru.citilink.pages.MainPage;
import ru.citilink.pages.ResultsPage;
import ru.citilink.pages.catalog.Noutbuki;
import ru.citilink.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BruceLeeTest extends BaseTest { //todo имя
    private final static String NOUTBUKI_CATEGORY = "Ноутбуки";
    private final static String BREND_FILTER_CATEGORY = "Бренд";
    private final static String SCREEN_DIAGONAL_FILTER_CATEGORY = "Диагональ экрана";
    private final static String PROCESSOR_SERIES_FILTER_CATEGORY = "Серия процессора";
    ConfProperties confProperties = new ConfProperties();
    MainPage mainPage = new MainPage();  //todo модификаторы
    ResultsPage resultsPage = new ResultsPage();
    CartPage cartPage = new CartPage();


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


    @Test
    public void checkFilterProductsByParameters() throws InterruptedException {
        open(confProperties.getProperty("test-site"));
        new MainPage().clickPopularCategoryTile(NOUTBUKI_CATEGORY);
        new Noutbuki().clickFilterDropDownCategoryAndValue(BREND_FILTER_CATEGORY,"HUAWEI")
//                .clickFilterDropDownCategoryAndValue(BREND_FILTER_CATEGORY,"LENOVO")
                .clickFilterDropDownCategoryAndValue(SCREEN_DIAGONAL_FILTER_CATEGORY,"14")
                .clickFilterDropDownCategoryAndValue(PROCESSOR_SERIES_FILTER_CATEGORY,"Core i7")
//                .clickButtonWithFilteringResults()
                .clickButtonDetailCatalogMode()
                .checkProductsAfterFiltration("HUAWEI","14","Core i7");
        sleep(5000);
    }
}

