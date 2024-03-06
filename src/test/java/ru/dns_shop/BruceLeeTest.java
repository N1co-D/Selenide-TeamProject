package ru.dns_shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.dns_shop.pages.CartPage;
import ru.dns_shop.pages.MainPage;
import ru.dns_shop.pages.ResultsPage;
import ru.dns_shop.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;

public class BruceLeeTest extends BaseTest {
    private static final String MAIN_PAGE = new ConfProperties().getProperty("test-site");

    @Test
    public void checkTheAdditionOfProductToCart() {
        MainPage mainPage = new MainPage();

        open(MAIN_PAGE);
        Assertions.assertTrue(mainPage.getPagesUniqueElement(),
                "Ошибка в открытии главной страницы");
        String inputText = "Переходники";
        String productFromDropDownList = "Переходники на евровилку";
        String observedProduct = "Адаптер-переходник на евровилку PREMIER 11626/20, темно-серый";

        ResultsPage resultsPage = new ResultsPage();

        mainPage.inputBoxWriteText(inputText).clickOnProductFromDropDownList(productFromDropDownList);
        Assertions.assertTrue(resultsPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с результатами поиска");

        resultsPage.enableDetailedCatalogMode().requiredProductBuyingClick(observedProduct);
        Assertions.assertTrue(resultsPage
                        .checkAppearingWindowWithAddedProductInCartStatus(),
                "Ошибка в открытии всплывающего окна с сообщением о добавлении товара в корзину");

        CartPage cartPage = new CartPage();

        resultsPage.goToCartButtonClick();
        Assertions.assertTrue(cartPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с корзиной");

        String expectedProductCode = "1860968";

        Assertions.assertTrue(cartPage.getCodeNumberOfProductInCart().contains(expectedProductCode),
                "Код товара в корзине неверен");
    }
}
