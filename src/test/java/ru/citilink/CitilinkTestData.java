package ru.citilink;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class CitilinkTestData {
    static Stream<Arguments> dataForComparisonTest() {
        String testLaptop = "Ноутбук Lenovo IdeaPad 1 15AMN7 82VG00LSUE, 15.6\", TN, AMD Ryzen 3 7320U, 4-ядерный, 8ГБ LPDDR5, 256ГБ SSD, AMD Radeon 610M, серый";
        String productCategory = "Ноутбуки Lenovo";
        return Stream.of(Arguments.of(testLaptop, productCategory));
    }

    static Stream<Arguments> dataNoutbukFilterParam() {
        String noutbukiCategory = "Ноутбуки";
        String brandFilterCategory = "Бренд";
        String screenDiagonalFilterCategory = "Диагональ экрана";
        String processorSeriesFilterCategory = "Серия процессора";
        String huawei = "HUAWEI";
        String diagonalValue_14 = "14";
        String cpuCore_i7 = "Core i7";
        return Stream.of(Arguments.of(noutbukiCategory,
                brandFilterCategory,
                screenDiagonalFilterCategory,
                processorSeriesFilterCategory,
                huawei,
                diagonalValue_14,
                cpuCore_i7));
    }

    static Stream<Arguments> dataSmartphone() {
        String huaweiNovaY72 = "Смартфон Huawei nova Y72 8/128Gb, MGA-LX3, черный";
        return Stream.of(Arguments.of(huaweiNovaY72));
    }

    static Stream<Arguments> dataSnowplow() {
        String huterBrandName = "Снегоуборщик Huter SGC";
        String huterSGC4000L = "Снегоуборщик Huter SGC 4000L, бензиновый, 6.5л.с., самоходный [70/7/22]";
        String maxPrice = "45 000 ₽";
        String engineType = "Тип двигателя";
        String engineTypeBenzin = "бензиновый";
        String screwShape = "Форма шнека";
        String screwShapeTooth = "зубчатая";
        return Stream.of(Arguments.of(huterBrandName,
                huterSGC4000L,
                maxPrice,
                engineType,
                engineTypeBenzin,
                screwShape,
                screwShapeTooth));
    }
}