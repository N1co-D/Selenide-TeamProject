package ru.citilink;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class CitilinkTestData {
    static Stream<Arguments> dataForComparisonTest() {
        String testLaptop = "Ноутбук Lenovo IdeaPad 1 15AMN7 82VG00LSUE, 15.6\", TN, AMD Ryzen 3 7320U, 4-ядерный, 8ГБ LPDDR5, 256ГБ SSD, AMD Radeon 610M, серый";
        String productCategory = "Ноутбуки Lenovo";
        return Stream.of(Arguments.of(testLaptop, productCategory));
    }

    static Stream<Arguments> dataSmartWatchesNaccessories() {
        String categoryName = "Смарт-часы, гаджеты и фото";
        String subcategoryName = "Смарт-часы и аксессуары";
        String watchCategory = "Apple Watch";
        String series = "Серия";
        String watchSE2023 = "Watch SE 2023";
        String productName = "Смарт-часы Apple Watch SE 2023 A2722, 40мм, темная ночь / темная ночь [mre03ll/a]";
        String productAvailFilterCategory = "Наличие товара";
        String productAvailPickUp_5min = "Забрать через 5 минут";
        return Stream.of(Arguments.of(categoryName, subcategoryName, watchCategory, series, watchSE2023, productName,
                productAvailFilterCategory,
                productAvailPickUp_5min));
    }
}