package ru.yandex.scooter.tests;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.scooter.page.MainPage;
import ru.yandex.scooter.page.OrderPage;

import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class OrderPageTest {

    private WebDriver driver;
    private final String name;
    private final String surname;
    private final String address;
    private final String phone;
    private final String date;
    private final String comment;
    private final boolean useTopOrderButton;

    public OrderPageTest(String name, String surname, String address, String phone, String date, String comment, boolean useTopOrderButton) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.date = date;
        this.comment = comment;
        this.useTopOrderButton = useTopOrderButton;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Максим", "Власов", "Москва, ул. Ленина, д.1", "89130001122", "10.03.2025", "Позвоните за один час", true},
                {"Михаил", "Петров", "Москва, ул. Пушкина, д.2", "89991112233", "12.06.2025", "", false}
        };
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        new MainPage(driver).closeCookieBannerIfPresent();
    }

    @Test
    public void testPositiveOrderFlow() {
        MainPage mainPage = new MainPage(driver);
        if (useTopOrderButton) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillPersonalInfo(name, surname, address, phone);
        orderPage.fillRentalInfo(date, comment);

        Assert.assertTrue("Окно подтверждения не появилось", orderPage.isOrderConfirmed());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}