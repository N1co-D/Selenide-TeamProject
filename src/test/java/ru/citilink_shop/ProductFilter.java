package ru.citilink_shop;

import org.junit.jupiter.api.Test;
import ru.citilink_shop.utilities.pages.MainPage;
import ru.citilink_shop.utilities.pages.catalog.Noutbuki;

import static com.codeborne.selenide.Selenide.open;

public class ProductFilter extends BaseTest{
    private final static String NOUTBUKI_CATEGORY = "Ноутбуки";
    private final static String BREND_FILTER_CATEGORY = "Бренд";
    private final static String SCREEN_DIAGONAL_FILTER_CATEGORY = "Диагональ экрана";
    private final static String PROCESSOR_SERIES_FILTER_CATEGORY = "Серия процессора";

    @Test
    public void checkFilterProductsByParameters() throws InterruptedException {
        open("https://www.citilink.ru/");
        new MainPage().clickPopularCategoryTile(NOUTBUKI_CATEGORY);
        new Noutbuki().clickFilterDropDownCategoryAndValue(BREND_FILTER_CATEGORY,"HUAWEI")
                .clickFilterDropDownCategoryAndValue(SCREEN_DIAGONAL_FILTER_CATEGORY,"14")
                .clickFilterDropDownCategoryAndValue(PROCESSOR_SERIES_FILTER_CATEGORY,"Core i7")
                .clickButtonWithFilteringResults()
                .clickButtonDetailCatalogMode()
                .checkProductsAfterFiltration();
        Thread.sleep(5000);
    }
}