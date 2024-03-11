package ru.citilink;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.citilink.pages.CartPage;
import ru.citilink.pages.ComparePage;
import ru.citilink.pages.MainPage;
import ru.citilink.pages.ResultsPage;
import ru.citilink.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
                                                                   String expectedAmountOfProduct) {
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
        assertEquals(expectedAmountOfProduct,
                cartPage.getAmountOfProductInCart(),
                "Ошибка в увеличении количества товара в корзине");
    }

    @ParameterizedTest
    @CsvSource({"2"})
    public void checkTheAdditionOfProductToCompareSection(int amountOfProductsForAdding) {
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

        comparePage.getPagesUniqueElement()
                .checkAmountOfAddedProductsToCompare(amountOfProductsForAdding);
    }
}