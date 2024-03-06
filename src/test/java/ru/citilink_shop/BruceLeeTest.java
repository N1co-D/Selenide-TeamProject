package ru.citilink_shop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.citilink_shop.utilities.pages.MainPage;
import ru.citilink_shop.utilities.pages.OrderPage;
import ru.citilink_shop.utilities.pages.ProductPage;

import static com.codeborne.selenide.Selenide.open;

public class BruceLeeTest extends BaseTest {
    private final static String HUAWEI_NOVA_MGA_LX3_BLACK = "Смартфон Huawei nova Y72 8/128Gb,  MGA-LX3,  черный";

    @Test
    public void checkAddItemToShoppingBasket() {
        open("https://www.citilink.ru/");
        new MainPage().enterProductSearchInputLine(HUAWEI_NOVA_MGA_LX3_BLACK);
        new ProductPage()
                .clickButtonForAddingItemToBasket(HUAWEI_NOVA_MGA_LX3_BLACK)
                .clickButtonCloseUpSaleBasketLayout()
                .clickButtonBasketFresnelContainer();
        Assertions.assertEquals("Смартфон Huawei nova Y72 8/128Gb, MGA-LX3, черный"
                , new OrderPage().getNameProductFromBasketSnippet(HUAWEI_NOVA_MGA_LX3_BLACK));
    }
}