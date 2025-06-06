package ru.yandex.scooter.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    private final int TIMEOUT_SECONDS = 5;

    // Локатор кнопки принятия куки
    private final By cookieBannerButton = By.id("rcc-confirm-button");

    // Локаторы вопросов и ответов (по индексам от 0 до 7)
    private final By[] questionLocators = new By[]{
            By.id("accordion__heading-0"),
            By.id("accordion__heading-1"),
            By.id("accordion__heading-2"),
            By.id("accordion__heading-3"),
            By.id("accordion__heading-4"),
            By.id("accordion__heading-5"),
            By.id("accordion__heading-6"),
            By.id("accordion__heading-7")
    };

    private final By[] answerLocators = new By[]{
            By.id("accordion__panel-0"),
            By.id("accordion__panel-1"),
            By.id("accordion__panel-2"),
            By.id("accordion__panel-3"),
            By.id("accordion__panel-4"),
            By.id("accordion__panel-5"),
            By.id("accordion__panel-6"),
            By.id("accordion__panel-7")
    };


    // Верхняя кнопка "Заказать"
    private final By orderButtonTop = By.className("Button_Button__ra12g");

    // Нижняя кнопка "Заказать"
    private final By orderButtonBottom = By.xpath("(//button[contains(@class, 'Button_Button__ra12g') and text()='Заказать'])[2]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Открыть главную страницу
    public void open() {
        driver.get(PAGE_URL);
    }

    // Закрыть баннер с куки, если он есть
    public void closeCookieBannerIfPresent() {
        if (driver.findElements(cookieBannerButton).size() > 0) {
            driver.findElement(cookieBannerButton).click();
        }
    }

    // Клик по вопросу по индексу
    public void clickQuestionByIndex(int index) {
        WebElement question = driver.findElement(questionLocators[index]);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", question);
        question.click();
    }

    // Получить текст ответа по индексу
    public String getAnswerTextByIndex(int index) {
        By answerLocator = answerLocators[index];

        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(answerLocator));

        WebElement answerElement = driver.findElement(answerLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", answerElement);

        return answerElement.getText().trim();
    }


    // Клик по верхней кнопке "Заказать"
    public void clickOrderButtonTop() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }

    // Клик по нижней кнопке "Заказать"
    public void clickOrderButtonBottom() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }
}