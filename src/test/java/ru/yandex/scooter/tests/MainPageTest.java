package ru.yandex.scooter.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.yandex.scooter.page.MainPage;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MainPageTest {

    private WebDriver driver;
    private MainPage mainPage;

    private final int index;
    private final String expectedQuestion;
    private final String expectedAnswer;

    public MainPageTest(int index, String expectedQuestion, String expectedAnswer) {
        this.index = index;
        this.expectedQuestion = expectedQuestion;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters(name = "Проверка вопроса №{0}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."}
        });
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
    }

    @Test
    public void testFaqQuestionAndAnswer() {
        mainPage.open();
        mainPage.closeCookieBannerIfPresent();

        // Проверка вопроса
        String actualQuestion = mainPage.getQuestionTextByIndex(index);
        assertEquals("Вопрос не совпадает", expectedQuestion, actualQuestion);

        // Проверка ответа
        mainPage.clickQuestionByIndex(index);
        String actualAnswer = mainPage.getAnswerTextByIndex(index);
        assertEquals("Ответ не совпадает", expectedAnswer, actualAnswer);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}