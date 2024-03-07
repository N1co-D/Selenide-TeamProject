package ru.citilink;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.citilink.pages.CartPage;
import ru.citilink.pages.MainPage;
import ru.citilink.pages.ResultsPage;
import ru.citilink.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BruceLeeTest extends BaseTest {
    MainPage mainPage = new MainPage();
    ResultsPage resultsPage = new ResultsPage();
    CartPage cartPage = new CartPage();
    ConfProperties confProperties = new ConfProperties();

    @ParameterizedTest
    @CsvSource({"'Переходники', 'Переходники на евровилку', " +
            "'Адаптер-переходник на евровилку PREMIER 11626/20, темно-серый'"})
    public void checkingTheDeletingOfProductFromCart(String inputText, String productFromDropDownList,
                                                     String observedProduct) {
        open(confProperties.getProperty("test-site"));
        assertTrue(mainPage.getPagesUniqueElement(),
                "Ошибка в открытии главной страницы");

        mainPage.inputBoxWriteText(inputText).clickOnProductFromDropDownList(productFromDropDownList);
        assertTrue(resultsPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с результатами поиска");

        resultsPage.enableDetailedCatalogMode().requiredProductBuyingClick(observedProduct);
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

        cartPage.deleteRequiredProductInCartButtonClick(observedProduct);
        assertTrue(cartPage
                        .getStatusOfMissingProductsInCart(),
                "Ошибка в удалении товара из магазина");

        cartPage.goBackToShoppingButtonClick();
        assertTrue(mainPage.getPagesUniqueElement(),
                "Ошибка в открытии главной страницы");
    }
}