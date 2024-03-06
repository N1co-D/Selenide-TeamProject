package ru.dns_shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.dns_shop.pages.CartPage;
import ru.dns_shop.pages.MainPage;
import ru.dns_shop.pages.ResultsPage;
import ru.dns_shop.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;

public class BruceLeeTest extends BaseTest {
    MainPage mainPage = new MainPage();
    ResultsPage resultsPage = new ResultsPage();
    CartPage cartPage = new CartPage();
    private static final String MAIN_PAGE = new ConfProperties().getProperty("test-site");

    @Test
    public void checkTheIncreaseInQuantityWhenAddingProductsToCart() {
        open(MAIN_PAGE);
        Assertions.assertTrue(mainPage.getPagesUniqueElement(),
                "Ошибка в открытии главной страницы");
        String inputText = "Ноутбук Huawei MateBook D 14 53013XFA, 14";

        mainPage.SearchGameByInputBox(inputText);
        Assertions.assertTrue(resultsPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с результатами поиска");

        String rawMemoryRequiredParameter = "8 ГБ, LPDDR4x";
        String diskRequiredParameter = "SSD 512 ГБ";

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
        String expectedAmountOfProduct = "2";
        Assertions.assertEquals(cartPage.getAmountOfProductInCart(), expectedAmountOfProduct,
                "Ошибка в увеличении количества товара в корзине");
    }
}