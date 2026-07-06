package com.solidinvest.tests;

import com.solidinvest.pages.ContactUsPage;
import com.solidinvest.pages.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ContactUsTest {
    private WebDriver driver;
    private MainPage mainPage;
    private ContactUsPage contactUsPage;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        String headless = System.getProperty("headless", "false");
        if (Boolean.parseBoolean(headless)) {
            options.addArguments("--headless=new");
        }
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
        contactUsPage = new ContactUsPage(driver);
    }

    @Test
    public void testContactUsForm() throws InterruptedException {
        mainPage.open();
        Assert.assertTrue(mainPage.isPageLoaded());
        Thread.sleep(1000);

        try {
            WebElement agreeButton = driver.findElement(By.xpath("//button[contains(text(), 'Согласен') or contains(text(), 'согласен')]"));
            js.executeScript("arguments[0].click();", agreeButton);
            System.out.println("✅ Клик по 'Согласен' выполнен");
            Thread.sleep(1000);
        } catch (Exception ex) {
            System.out.println("ℹ️ Кнопка 'Согласен' не найдена");
        }

        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        System.out.println("✅ Скролл до конца главной страницы выполнен");
        Thread.sleep(2000);

        WebElement contactsLink = driver.findElement(By.xpath("//a[contains(text(), 'Контакты') or contains(text(), 'контакты')]"));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", contactsLink);
        Thread.sleep(500);
        js.executeScript("arguments[0].click();", contactsLink);
        System.out.println("✅ Клик по 'Контакты' выполнен");
        Thread.sleep(3000);

        WebElement contactUsButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[@id=\"__nuxt\"]/div/main/section[2]/div/div/div[2]/div[2]/button")));
        System.out.println("✅ Кнопка 'Связаться с нами' найдена");

        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", contactUsButton);
        Thread.sleep(500);
        js.executeScript("arguments[0].click();", contactUsButton);
        System.out.println("✅ Клик по 'Связаться с нами' выполнен");
        Thread.sleep(3000);

        WebElement formContainer = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div[1]/div/div[2]/div/div")));
        System.out.println("✅ Форма 'Получить консультацию' загружена");

        WebElement emailField = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div/div/div[2]/div/div/div[3]/div/div/input"));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", emailField);
        emailField.clear();
        emailField.sendKeys("test@test.ru");
        System.out.println("✅ Поле Email заполнено: test@test.ru");

        try {
            WebElement citySelect = formContainer.findElement(By.xpath(".//select[@name='city' or contains(@placeholder, 'Город') or contains(@id, 'city')]"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", citySelect);
            Thread.sleep(500);
            Select select = new Select(citySelect);
            select.selectByIndex(1);
            System.out.println("✅ Выбран город из списка: " + select.getFirstSelectedOption().getText());
        } catch (Exception ex) {
            try {
                WebElement cityInput = formContainer.findElement(By.xpath(".//input[@placeholder='Город' or contains(@name, 'city')]"));
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", cityInput);
                cityInput.clear();
                cityInput.sendKeys("Москва");
                Thread.sleep(1000);

                try {
                    WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(@class, 'option') or contains(@class, 'item')][1]")));
                    js.executeScript("arguments[0].click();", firstOption);
                    System.out.println("✅ Выбран город: Москва");
                } catch (Exception exc) {
                    System.out.println("ℹ️ Не удалось выбрать город из выпадающего списка");
                }
            } catch (Exception exc) {
                System.out.println("ℹ️ Поле 'Город' не найдено");
            }
        }

        List<WebElement> allInputs = formContainer.findElements(By.xpath(".//input[@type='text' or @type='email' or @type='tel' or @type='number' or @type='password']"));
        System.out.println("Найдено полей для заполнения: " + allInputs.size());

        for (WebElement input : allInputs) {
            try {
                if (input.equals(emailField)) {
                    continue;
                }

                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", input);
                Thread.sleep(200);

                String placeholder = input.getAttribute("placeholder");
                String name = input.getAttribute("name");
                String type = input.getAttribute("type");

                if ((type != null && type.equals("tel")) ||
                        (placeholder != null && placeholder.toLowerCase().contains("телефон")) ||
                        (name != null && name.toLowerCase().contains("phone"))) {
                    input.clear();
                    input.sendKeys("+7 (999) 123-45-67");
                    System.out.println("✅ Заполнено поле Телефон: +7 (999) 123-45-67");
                } else if ((placeholder != null && placeholder.toLowerCase().contains("имя")) ||
                        (name != null && name.toLowerCase().contains("name"))) {
                    input.clear();
                    input.sendKeys("Тестовый Пользователь");
                    System.out.println("✅ Заполнено поле Имя: Тестовый Пользователь");
                } else if (placeholder != null && placeholder.toLowerCase().contains("сообщ")) {
                    input.clear();
                    input.sendKeys("Тестовое сообщение для проверки работы формы");
                    System.out.println("✅ Заполнено поле Сообщение");
                } else if (placeholder != null && placeholder.toLowerCase().contains("коммент")) {
                    input.clear();
                    input.sendKeys("Тестовый комментарий");
                    System.out.println("✅ Заполнено поле Комментарий");
                } else {
                    input.clear();
                    input.sendKeys("Тестовое значение");
                    System.out.println("✅ Заполнено поле: " + (placeholder != null ? placeholder : name));
                }
            } catch (Exception ex) {
                System.out.println("⚠️ Не удалось заполнить поле: " + ex.getMessage());
            }
        }

        List<WebElement> allTextareas = formContainer.findElements(By.xpath(".//textarea"));
        for (WebElement textarea : allTextareas) {
            try {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", textarea);
                Thread.sleep(200);
                textarea.clear();
                textarea.sendKeys("Тестовое сообщение для проверки работы формы");
                System.out.println("✅ Заполнено текстовое поле");
            } catch (Exception ex) {
                System.out.println("⚠️ Не удалось заполнить textarea: " + ex.getMessage());
            }
        }

        List<WebElement> allCheckboxes = formContainer.findElements(By.xpath(".//input[@type='checkbox']"));
        System.out.println("Найдено чекбоксов: " + allCheckboxes.size());

        for (WebElement checkbox : allCheckboxes) {
            try {
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
                Thread.sleep(200);
                if (!checkbox.isSelected()) {
                    js.executeScript("arguments[0].click();", checkbox);
                    System.out.println("✅ Чекбокс отмечен");
                } else {
                    System.out.println("ℹ️ Чекбокс уже отмечен");
                }
            } catch (Exception ex) {
                System.out.println("⚠️ Не удалось отметить чекбокс: " + ex.getMessage());
            }
        }

        WebElement submitButton = formContainer.findElement(By.xpath(".//button[contains(text(), 'Выслать код') or contains(text(), 'выслать код')]"));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", submitButton);
        Thread.sleep(500);
        js.executeScript("arguments[0].click();", submitButton);
        System.out.println("✅ Кнопка 'Выслать код' нажата");
        Thread.sleep(3000);

        System.out.println("⚠️ Браузер оставлен открытым для отладки");
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