package ru.citilink;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.citilink.pages.ComparingPage;
import ru.citilink.pages.MainPage;
import ru.citilink.pages.ResultsPage;
import ru.citilink.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;

public class BruceLeeTest extends BaseTest {
    MainPage mainPage = new MainPage();
    ResultsPage resultsPage = new ResultsPage();
    ComparingPage comparingPage = new ComparingPage();
    private static final String MAIN_PAGE = new ConfProperties().getProperty("test-site");

    @ParameterizedTest
    @CsvSource({"2"})
    public void checkTheAdditionOfProductToCompareSection(int amountOfProductsForAdding) {
        open(MAIN_PAGE);
        Assertions.assertTrue(mainPage.getPagesUniqueElement(),
                "Ошибка в открытии главной страницы");

        mainPage.productCatalogClick().televisionsAndAudioVideoEquipmentCategoryClick().oledTelevisionsCategoryClick();
        Assertions.assertTrue(resultsPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с результатами поиска");

        resultsPage.enableDetailedCatalogMode().someProductAddToComparingClick(amountOfProductsForAdding);
        Assertions.assertEquals(resultsPage.getAmountOfAddedProductsToCompare(),
                String.valueOf(amountOfProductsForAdding),
                "Ошибка в  корректном отражении количества добавленных для сравнения товаров");

        resultsPage.comparingButtonClick();
        Assertions.assertTrue(comparingPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы 'Сравнение товаров'");

        Assertions.assertEquals(comparingPage.getAmountOfAddedProductsToCompare(),
                String.valueOf(amountOfProductsForAdding),
                "Ошибка в  корректном отражении количества добавленных для сравнения товаров");
    }
}