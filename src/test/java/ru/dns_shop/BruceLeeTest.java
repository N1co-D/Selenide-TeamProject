package ru.dns_shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.dns_shop.pages.ComparingPage;
import ru.dns_shop.pages.MainPage;
import ru.dns_shop.pages.ResultsPage;
import ru.dns_shop.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;

public class BruceLeeTest extends BaseTest {
    private static final String MAIN_PAGE = new ConfProperties().getProperty("test-site");

    @Test
    public void checkTheAdditionOfProductToCompareSection() {
        MainPage mainPage = new MainPage();

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

        ComparingPage comparingPage = new ComparingPage();

        resultsPage.comparingButtonClick();
        Assertions.assertTrue(comparingPage.getPagesUniqueElement(),
                "Ошибка в открытии страницы 'Сравнение товаров'");

        Assertions.assertEquals(comparingPage.getAmountOfAddedProductsToCompare(),
                String.valueOf(amountOfProductsForAdding),
                "Ошибка в  корректном отражении количества добавленных для сравнения товаров");
    }
}
