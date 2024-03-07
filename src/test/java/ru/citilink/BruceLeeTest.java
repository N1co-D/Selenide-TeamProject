package ru.citilink;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.citilink.pages.CartPage;
import ru.citilink.pages.MainPage;
import ru.citilink.pages.ResultsPage;
import ru.citilink.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;

public class BruceLeeTest extends BaseTest {
    MainPage mainPage = new MainPage();
    ResultsPage resultsPage = new ResultsPage();
    CartPage cartPage = new CartPage();
    private static final String MAIN_PAGE = new ConfProperties().getProperty("test-site");

    @ParameterizedTest
    @CsvSource({"'Ноутбук Huawei MateBook D 14 53013XFA, 14', '8 ГБ, LPDDR4x', 'SSD 512 ГБ', '2'"})
    public void checkTheIncreaseInQuantityWhenAddingProductsToCart(String inputText, String rawMemoryRequiredParameter,
                                                                   String diskRequiredParameter, String expectedAmountOfProduct) {
        open(MAIN_PAGE);
        Assertions.assertTrue(mainPage.getPagesUniqueElement(),
                "Ошибка в открытии главной страницы");

        mainPage.SearchGameByInputBox(inputText);
        Assertions.assertTrue(resultsPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с результатами поиска");

        resultsPage.enableDetailedCatalogMode().requiredProductWithParametersBuyingClick(rawMemoryRequiredParameter, diskRequiredParameter);
        Assertions.assertTrue(resultsPage
                        .checkAppearingWindowWithAddedProductInCartStatus(),
                "Ошибка в открытии всплывающего окна с сообщением о добавлении товара в корзину");

        resultsPage.closeWindowWithAddedProductInCartStatusClick();
        Assertions.assertTrue(resultsPage
                        .checkDisappearingWindowWithAddedProductInCartStatus(),
                "Ошибка в закрытии всплывающего окна с сообщением о добавлении товара в корзину");

        resultsPage.cartButtonClick();
        Assertions.assertTrue(cartPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с корзиной");

        cartPage.increaseTheAmountOfProductInCartButtonClick();
        Assertions.assertEquals(cartPage.getAmountOfProductInCart(), expectedAmountOfProduct,
                "Ошибка в увеличении количества товара в корзине");
    }
}