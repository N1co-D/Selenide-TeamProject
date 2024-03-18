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
        String observedProduct = "Ноутбук Apple MacBook Air A2337 MGN63ZP/A, 13.3\", IPS, Apple M1 8 core 3.2ГГц, 8-ядерный, 8ГБ 256ГБ SSD, Mac OS, серый космос";
        return Stream.of(Arguments.of(searchedProduct, sortingParameter, rating, observedProduct, category));
    }
}