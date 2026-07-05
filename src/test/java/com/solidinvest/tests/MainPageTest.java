package com.solidinvest.tests;

import com.solidinvest.pages.ContactsPage;
import com.solidinvest.pages.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import java.time.Duration;

@Feature("Главная страница")
public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private ContactsPage contactsPage;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        mainPage = new MainPage(driver);
        contactsPage = new ContactsPage(driver);
    }

    @Test
    @Description("Проверка загрузки главной страницы")
    @Story("Доступность сайта")
    public void testMainPageLoads() {
        mainPage.open();

        Assert.assertTrue(mainPage.isPageLoaded(), "Страница не загрузилась");

        String title = mainPage.getPageTitle();
        boolean containsExpected = title.toLowerCase().contains("солид") ||
                title.toLowerCase().contains("инвестиции") ||
                mainPage.isTextPresent("СОЛИД") ||
                mainPage.isTextPresent("инвестиции");

        Assert.assertTrue(containsExpected,
                "На странице или в заголовке не найдено упоминание 'СОЛИД' или 'инвестиции'");
    }

    @Test
    @Description("Проверка перехода по ссылке 'Контакты'")
    @Story("Навигация по сайту")
    public void testContactsLinkNavigation() throws InterruptedException {
        // Шаг 1: Открыть главную страницу
        mainPage.open();
        Assert.assertTrue(mainPage.isPageLoaded(), "Главная страница не загрузилась");

        // Шаг 2: Найти ссылку "Контакты" в футере
        WebElement contactsLink = null;

        // Пробуем найти через частичный текст
        try {
            contactsLink = driver.findElement(By.partialLinkText("Контакты"));
        } catch (Exception e) {
            try {
                contactsLink = driver.findElement(By.xpath("//a[contains(text(), 'Контакты') or contains(text(), 'контакты')]"));
            } catch (Exception ex) {
                try {
                    contactsLink = driver.findElement(By.xpath("//a[contains(@href, 'contacts') or contains(@href, 'kontakty')]"));
                } catch (Exception exc) {
                    // Ищем любую ссылку, содержащую "контакт" в тексте или href
                    java.util.List<WebElement> allLinks = driver.findElements(By.tagName("a"));
                    for (WebElement link : allLinks) {
                        String text = link.getText().toLowerCase();
                        String href = link.getAttribute("href");
                        if (text.contains("контакт") ||
                                text.contains("contact") ||
                                (href != null && (href.contains("contacts") || href.contains("kontakty")))) {
                            contactsLink = link;
                            break;
                        }
                    }
                }
            }
        }

        Assert.assertNotNull(contactsLink, "Ссылка 'Контакты' не найдена на странице");

        // Шаг 3: Прокрутить к элементу и кликнуть через JavaScript
        js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", contactsLink);
        Thread.sleep(500);

        // Клик через JavaScript (обходит все преграды)
        js.executeScript("arguments[0].click();", contactsLink);

        System.out.println("Клик по ссылке 'Контакты' выполнен через JavaScript");

        // Шаг 4: Проверить загрузку страницы контактов
        Thread.sleep(1000);
        Assert.assertTrue(contactsPage.isPageLoaded(), "Страница контактов не загрузилась");

        // Шаг 5: Проверить наличие контактной информации
        boolean hasInfo = contactsPage.hasContactInformation();
        Assert.assertTrue(hasInfo,
                "На странице контактов отсутствует информация о контактах (адрес, телефон, email)");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // Игнорируем ошибки при закрытии
            }
        }
    }
}