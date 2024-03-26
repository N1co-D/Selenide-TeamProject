package ru.citilink.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public abstract class BasePage {
    static final Duration WAITING_TIME = Duration.ofSeconds(20);

    public void jsClick(SelenideElement selenideElement) {
        selenideElement.should(visible, WAITING_TIME);
        executeJavaScript("arguments[0].click();", selenideElement);
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] makeScreenshot() {
        sleep(2000);
        return screenshot(OutputType.BYTES);
    }
}