package ru.dns_shop;

import org.junit.jupiter.api.Test;
import ru.dns_shop.citilink.CatalogPage;
import ru.dns_shop.citilink.ComparePage;
import ru.dns_shop.citilink.MainPage;
import ru.dns_shop.utilities.ConfProperties;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class BruceLeeTest extends BaseTest {
    private final MainPage mainPage = new MainPage();
    private final CatalogPage catalogPage = new CatalogPage();
    private final ComparePage comparePage = new ComparePage();
    private final ConfProperties confProperties = new ConfProperties();
    private static final String TEST_LAPTOP = "Ноутбук Lenovo IdeaPad 1 15AMN7 82VG00LSUE, 15.6\"" +
            ", TN, AMD Ryzen 3 7320U, 4-ядерный, 8ГБ LPDDR5, 256ГБ SSD, AMD Radeon 610M, серый";

    @Test
    public void checkingAdditionOfProductToComparison() {
        open(confProperties.getProperty("test-site"));
        mainPage.enterDataSearchField("lenovo").productSearchExtraResultListClick("Ноутбуки Lenovo");
        assertEquals(catalogPage.getSubcategoryPageTitle(), "Ноутбуки Lenovo",
                String.format("Указан заголовок некорректной страницы. Ожидаем = Ноутбуки Lenovo, факт = %s",
                        catalogPage.getSubcategoryPageTitle()));
        catalogPage.detailedCatalogModeButtonClick().comparingCurrentProductButtonClick(TEST_LAPTOP);
        assertTrue(mainPage.compareValueIsDisplayed(), "Товар не добавлен в сравнение");
        String priceOfCurrentProduct = catalogPage.getPriceOfCurrentProduct(TEST_LAPTOP);
        mainPage.compareButtonClick();
        assertAll(
                () -> assertEquals(comparePage.getComparePageTitle(), "Сравнение товаров",
                        String.format("Указан заголовок некорректной страницы. Ожидаем = Сравнение товаров, факт = %s",
                                comparePage.getComparePageTitle())),
                () -> assertEquals(comparePage.getTitleOfCurrentProduct(), TEST_LAPTOP,
                        String.format("Товар для сравнения не корректный. Ожидаем = %s, факт = %s",
                                TEST_LAPTOP, comparePage.getComparePageTitle())),
                () -> assertEquals(comparePage.getPriceOfCurrentProduct(), priceOfCurrentProduct,
                        String.format("Цена товара указанна не корректно. Ожидаем = %s, факт = %s",
                                priceOfCurrentProduct, comparePage.getComparePageTitle())));
    }
}