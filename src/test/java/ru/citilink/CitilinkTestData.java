package ru.citilink;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class CitilinkTestData {
    static Stream<Arguments> dataForComparisonTest() {
        return Stream.of(
                Arguments.of("Ноутбук Lenovo IdeaPad 1 15AMN7 82VG00LSUE, 15.6\", TN, AMD Ryzen 3 7320U, 4-ядерный, 8ГБ LPDDR5, 256ГБ SSD, AMD Radeon 610M, серый", "Ноутбуки Lenovo")
        );
    }
}