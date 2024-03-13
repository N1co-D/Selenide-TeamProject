package ru.citilink.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.UIAssertionError;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ComparePage extends BasePage {
    private final String comparePageTitle = "//div[@class='ComparePage__header']//h2[text()]";
    private final String productTitle = "//div[normalize-space(text())='Модель']/following-sibling::div//a";
    private final String productPrice = "//span[contains(@class,'Compare__product-cell_price_current-price')]";
    private final String deleteProductButton = "//div[@class='Compare__product-row']//div[@class='Compare__image-wrapper']/button";
    private final String noProductsForCompare = "//div[normalize-space(text())='Нет товаров для сравнения' and @style='display: block;']";
    private final String compareValue = "//div[contains(@class, 'HeaderMenu__count') and text()]";
    private final String showOnlyDifferenceCheckbox = "//label[contains(@class,'Compare__actions_show-differences')]";
    private final String amountOfAddedProductsToCompare = "//div[@class='Tabs js--Tabs']//div";

    public String getComparePageTitle() {
        return $x(comparePageTitle).shouldBe(visible, WAITING_TIME).getText();
    }

    public String getProductTitle() {
        return $x(productTitle).shouldBe(visible, WAITING_TIME).getText();
    }

    public String getProductPrice() {
        return $x(productPrice).shouldBe(visible, WAITING_TIME).getText();
    }

    public void deleteProductButtonClick() {
        $x(deleteProductButton).shouldBe(visible, WAITING_TIME).click();
    }

    public boolean noProductsForCompareIsDisplayed() {
        return $x(noProductsForCompare).shouldBe(visible, WAITING_TIME).isDisplayed();
    }

    public boolean compareValueIsDisplayed() {
        return $x(compareValue).should(not(visible), WAITING_TIME).isDisplayed();
    }

    public ComparePage checkIfCorrectPageOpen() {
        try {
            $x(showOnlyDifferenceCheckbox).should(visible, WAITING_TIME);
        } catch (UIAssertionError e) {
            fail("Не удалось подтвердить открытие ожидаемой страницы. Уникальный элемент " +
                    "страницы 'showOnlyDifferenceCheckbox' не был найден в течение заданного времени.");
        }
        return this;
    }

    private String getAmountOfAddedProductsToCompare() {
        return $x(amountOfAddedProductsToCompare).should(visible, WAITING_TIME)
                .getText();
    }

    public ComparePage checkAmountOfAddedProductsToCompare(int expectedAmountOfProductsForAdding) {
        assertEquals(getAmountOfAddedProductsToCompare(),
                String.valueOf(expectedAmountOfProductsForAdding),
                String.format("Фактическое количество добавленных для сравнения товаров = %s " +
                                " не соответствует ожидаемому = %s",
                        getAmountOfAddedProductsToCompare(), expectedAmountOfProductsForAdding));
        return this;
    }
    public List<Integer> createCompareSpecificsRowValueIndexList(String specific, String value) {
        List<Integer> indexList = new ArrayList<>();
        indexList.clear();
        int count = 1;
        for (SelenideElement element : createElementsCollection(String.format(compareSpecificsRowValue, specific))) {
            if (element.getText().equals(value)) {
                indexList.add(count);
            }
            count++;
        }
        System.out.println(indexList);
        return indexList;
    }

    public List<Integer> getRetainAllList() {
        var items3 = new ArrayList<>(createCompareSpecificsRowValueIndexList("Марка топлива", "АИ-92"));
        items3.retainAll(createCompareSpecificsRowValueIndexList("Самоходный", "да"));
        System.out.println(items3);
        return items3;
    }

    public void getIndexProductAfter(){
        for (Integer element : getRetainAllList()){
            System.out.println(element.intValue());
        }
    }

    private ElementsCollection createElementsCollection(String xPath) {
        return $$x(xPath).should(CollectionCondition.sizeGreaterThan(0), WAITING_TIME);
    }
}