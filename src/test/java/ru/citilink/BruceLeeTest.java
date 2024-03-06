package ru.citilink;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.citilink.pages.ComparingPage;
import ru.citilink.pages.MainPage;
import ru.citilink.pages.ResultsPage;
import ru.citilink.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;

public class BruceLeeTest extends BaseTest {
    MainPage mainPage = new MainPage();
    ComparingPage comparingPage = new ComparingPage();
    private static final String MAIN_PAGE = new ConfProperties().getProperty("test-site");

    @Test
    public void checkTheAdditionOfProductToCompareSection() {
        open(MAIN_PAGE);
        Assertions.assertTrue(mainPage.getPagesUniqueElement(),
                "Ошибка в открытии главной страницы");

        ResultsPage resultsPage = new ResultsPage();

        mainPage.productCatalogClick().televisionsAndAudioVideoEquipmentCategoryClick().oledTelevisionsCategoryClick();
        Assertions.assertTrue(resultsPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы с результатами поиска");

        int amountOfProductsForAdding = 2;

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