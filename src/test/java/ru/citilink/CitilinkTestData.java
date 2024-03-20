package ru.citilink;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class CitilinkTestData {
    static Stream<Arguments> dataForComparisonTest() {
        String testLaptop = "Ноутбук Lenovo IdeaPad 1 15AMN7 82VG00LSUE, 15.6\", TN, AMD Ryzen 3 7320U, 4-ядерный, 8ГБ LPDDR5, 256ГБ SSD, AMD Radeon 610M, серый";
        String productCategory = "Ноутбуки Lenovo";
        return Stream.of(Arguments.of(testLaptop, productCategory));
    }

    static Stream<Arguments> dataForCheckCorrectReflection() {
        String searchedProduct = "Apple MacBook Air";
        String sortingParameter = "по цене";
        String rating = "4,5 и выше";
        String category = "Ноутбуки";
        String observedProduct = "Ноутбук Apple MacBook Air A2337 MGN63HN/A, 13.3\", IPS, Apple M1 8 core 3.2ГГц, 8-ядерный, 8ГБ 256ГБ SSD, Mac OS, серый космос";
        return Stream.of(Arguments.of(searchedProduct, sortingParameter, rating, observedProduct, category));
    }

    /**
     * TC-ID7
     */
    static Stream<Arguments> checkIncreaseInQuantityWhenAddProductsToCartTestData() {
        String inputText = "Ноутбук Huawei MateBook D 14 53013XFA, 14";
        String rawMemoryRequiredParameter = "8 ГБ, LPDDR4x";
        String diskRequiredParameter = "SSD 512 ГБ";
        int expectedAmountOfProduct = 2;
        return Stream.of(Arguments.of(inputText, rawMemoryRequiredParameter,
                diskRequiredParameter, expectedAmountOfProduct));
    }

    /**
     * TC-ID8
     */
    static Stream<Arguments> checkProductAddToCompareSectionTestData() {
        int amountOfProductsForAdding = 2;
        return Stream.of(Arguments.of(amountOfProductsForAdding));
    }

    /**
     * TC-ID1
     */
    static Stream<Arguments> checkProductAddToCartTestData() {
        String inputText = "Переходники";
        String productFromDropDownList = "Переходники на евровилку";
        String observedProduct = "Адаптер-переходник на евровилку PREMIER 11626/20, темно-серый";
        String expectedProductCode = "1860968";
        return Stream.of(Arguments.of(inputText, productFromDropDownList, observedProduct, expectedProductCode));
    }

    /**
     * TC-ID2
     */
    static Stream<Arguments> checkTheDeletingOfProductFromCartTestData() {
        String inputText = "Переходники";
        String productFromDropDownList = "Переходники на евровилку";
        String observedProduct = "Адаптер-переходник на евровилку PREMIER 11626/20, темно-серый";
        return Stream.of(Arguments.of(inputText, productFromDropDownList, observedProduct));
    }

    static Stream<Arguments> checkProductNameAfterFilterParamDataTest() {
        String categoryName = "Смарт-часы, гаджеты и фото";
        String subcategoryName = "Смарт-часы и аксессуары";
        String brandName = "Apple Watch";
        String seriesFilterCategory = "Серия";
        String seriesValue = "Watch SE 2023";
        String productName = "Смарт-часы Apple Watch SE 2023 A2722, 40мм, темная ночь / темная ночь [mre03ll/a]";
        String productAvailFilterCategory = "Наличие товара";
        String productAvailValue = "Забрать через 5 минут";
        return Stream.of(Arguments.of(categoryName,
                subcategoryName,
                brandName,
                seriesFilterCategory,
                seriesValue,
                productName,
                productAvailFilterCategory,
                productAvailValue));
    }
}