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
    @CsvSource({"'Переходники', 'Переходники на евровилку', " +
            "'Адаптер-переходник на евровилку PREMIER 11626/20, темно-серый', '1860968'"})
    public void checkTheAdditionOfProductToCart(String inputText, String productFromDropDownList,
                                                String observedProduct, String expectedProductCode) {
        open(MAIN_PAGE);
        Assertions.assertTrue(mainPage.getPagesUniqueElement(),
                "Ошибка в открытии главной страницы");

        mainPage.inputBoxWriteText(inputText).clickOnProductFromDropDownList(productFromDropDownList);
        Assertions.assertTrue(resultsPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с результатами поиска");

        resultsPage.enableDetailedCatalogMode().requiredProductBuyingClick(observedProduct);
        Assertions.assertTrue(resultsPage
                        .checkAppearingWindowWithAddedProductInCartStatus(),
                "Ошибка в открытии всплывающего окна с сообщением о добавлении товара в корзину");

        resultsPage.goToCartButtonClick();
        Assertions.assertTrue(cartPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с корзиной");

        Assertions.assertTrue(cartPage.getCodeNumberOfProductInCart().contains(expectedProductCode),
                "Код товара в корзине неверен");
    }
}
