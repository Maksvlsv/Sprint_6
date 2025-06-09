package ru.yandex.scooter.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    private final int TIMEOUT_SECONDS = 5;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    //Локаторы первой страницы формы заказа

    // Поле ввода имени
    private final By nameInput = By.xpath("//input[@placeholder='* Имя']");

    // Поле ввода фамилии
    private final By surnameInput = By.xpath("//input[@placeholder='* Фамилия']");

    // Поле ввода адреса
    private final By addressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");

    // Выпадающий список станций метро
    private final By metroDropdown = By.className("select-search__value");

    // Первый элемент в выпадающем списке метро
    private final By metroOption = By.className("select-search__option");

    // Поле ввода телефона
    private final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка "Далее"
    private final By nextButton = By.xpath("//button[text()='Далее']");


    //Локаторы второй страницы формы заказа

    // Поле выбора даты доставки
    private final By dateInput = By.xpath("//input[@placeholder='* Когда привезти самокат']");

    // Календарь (должен исчезнуть после выбора даты)
    private final By calendarOverlay = By.className("react-datepicker");

    // Выпадающее меню выбора срока аренды
    private final By durationDropdown = By.className("Dropdown-control");

    // Опция "двое суток" в выпадающем меню
    private final By durationOptionOneDay = By.xpath("//div[@class='Dropdown-menu']//div[text()='двое суток']");

    // Чекбокс выбора цвета самоката (черный)
    private final By blackColorCheckbox = By.id("black");

    // Поле ввода комментария для курьера
    private final By commentInput = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопка "Заказать" на финальном экране
    private final By orderButton = By.xpath("//div[@class='Order_Buttons__1xGrp']//button[text()='Заказать']");

    // Кнопка подтверждения заказа "Да"
    private final By confirmButton = By.xpath("//button[text()='Да']");

    // Заголовок окна подтверждения заказа
    private final By orderConfirmationModal = By.className("Order_ModalHeader__3FDaJ");


    // Заполнение первой страницы формы (личные данные)
    public void fillPersonalInfo(String name, String surname, String address, String phone) {
        driver.findElement(nameInput).sendKeys(name);
        driver.findElement(surnameInput).sendKeys(surname);
        driver.findElement(addressInput).sendKeys(address);

        WebElement metro = driver.findElement(metroDropdown);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", metro);
        metro.click();
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(metroOption)).click();

        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    // Заполнение второй страницы формы (дата, срок, цвет, комментарий)
    public void fillRentalInfo(String date, String comment) {
        driver.findElement(dateInput).sendKeys(date);
        driver.findElement(dateInput).sendKeys(Keys.ENTER);

        // Явное ожидание, пока исчезнет календарь
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS))
                .until(ExpectedConditions.invisibilityOfElementLocated(calendarOverlay));

        driver.findElement(durationDropdown).click();
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(durationOptionOneDay)).click();

        driver.findElement(blackColorCheckbox).click();
        driver.findElement(commentInput).sendKeys(comment);

        driver.findElement(orderButton).click();

        // Попытка нажать на кнопку подтверждения
        new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }

    // Проверка появления окна подтверждения
    public boolean isOrderConfirmed() {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationModal))
                .getText().contains("Заказ оформлен");
    }
}