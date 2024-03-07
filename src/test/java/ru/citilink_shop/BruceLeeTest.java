package ru.citilink_shop;

import org.junit.jupiter.api.Test;
import ru.citilink_shop.utilities.ConfProperties;
import ru.citilink_shop.utilities.pages.MainPage;
import ru.citilink_shop.utilities.pages.catalog.Noutbuki;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;

public class BruceLeeTest extends BaseTest {
    private final static String NOUTBUKI_CATEGORY = "Ноутбуки";
    private final static String BREND_FILTER_CATEGORY = "Бренд";
    private final static String SCREEN_DIAGONAL_FILTER_CATEGORY = "Диагональ экрана";
    private final static String PROCESSOR_SERIES_FILTER_CATEGORY = "Серия процессора";
    ConfProperties confProperties = new ConfProperties();

    @Test
    public void checkFilterProductsByParameters() throws InterruptedException {
        open(confProperties.getProperty("test-site"));
        new MainPage().clickPopularCategoryTile(NOUTBUKI_CATEGORY);
        new Noutbuki().clickFilterDropDownCategoryAndValue(BREND_FILTER_CATEGORY,"HUAWEI")
                .clickFilterDropDownCategoryAndValue(SCREEN_DIAGONAL_FILTER_CATEGORY,"14")
                .clickFilterDropDownCategoryAndValue(PROCESSOR_SERIES_FILTER_CATEGORY,"Core i7")
                .clickButtonWithFilteringResults()
                .clickButtonDetailCatalogMode()
                .checkProductsAfterFiltration();
        sleep(5000);

    }
}
