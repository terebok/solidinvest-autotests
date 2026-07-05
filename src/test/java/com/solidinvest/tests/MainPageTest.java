package com.solidinvest.tests;

import com.solidinvest.pages.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

@Feature("Главная страница")
public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
       // options.addArguments("--headless"); // для CI
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
    }

    @Test
    @Description("Проверка загрузки главной страницы")
    @Story("Доступность сайта")
    public void testMainPageLoads() {
        mainPage.open();

        // Ожидаемый результат: страница загружена, заголовок или контент содержит "СОЛИД" или "инвестиции"
        Assert.assertTrue(mainPage.isPageLoaded(), "Страница не загрузилась (не виден основной элемент)");

        String title = mainPage.getPageTitle();
        boolean containsExpected = title.contains("СОЛИД") || title.contains("инвестиции")
                || mainPage.isTextPresent("СОЛИД") || mainPage.isTextPresent("инвестиции");

        Assert.assertTrue(containsExpected,
                "На странице или в заголовке не найдено упоминание 'СОЛИД' или 'инвестиции'");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}