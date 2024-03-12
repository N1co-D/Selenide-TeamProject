package ru.citilink.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$x;

public class ComparePage extends BasePage {
    private final String compareSpecificsRowValue = "//div[contains(text(),'%s')]/following-sibling::div[@class='Compare__specification-row_wrapper']/div/div";


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