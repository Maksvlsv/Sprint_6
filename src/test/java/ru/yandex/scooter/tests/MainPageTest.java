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

import static org.junit.Assert.assertFalse;

@RunWith(Parameterized.class)
public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private final int index;

    public MainPageTest(int index) {
        this.index = index;
    }

    @Parameterized.Parameters(name = "Вопрос №{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}
        });
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        mainPage = new MainPage(driver);
        driver.manage().window().maximize();
    }

    @Test
    public void testQuestionShowsNonEmptyAnswer() {
        mainPage.open();
        mainPage.closeCookieBannerIfPresent();
        mainPage.clickQuestionByIndex(index);
        String answer = mainPage.getAnswerTextByIndex(index);
        assertFalse("Ответ на вопрос #" + index + " должен быть не пустым", answer.isEmpty());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}