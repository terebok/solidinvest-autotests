package com.solidinvest.tests;

import com.solidinvest.pages.ContactsPage;
import com.solidinvest.pages.MainPage;
import com.solidinvest.pages.OnbordingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import java.time.Duration;

@Feature("Страница контактов")
public class OnbordingTest {
    private WebDriver driver;
    private MainPage mainPage;
    private OnbordingPage onbordingPage;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless=new");
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
        onbordingPage = new OnbordingPage(driver);
    }

    @Test
    @Description("Проверка перехода по ссылке 'Открыть счет'")
    @Story("Навигация по сайту")
    public void testContactsLinkNavigation() throws InterruptedException {
        // Шаг 1: Открыть главную страницу
        mainPage.open();
        Assert.assertTrue(mainPage.isPageLoaded(), "Главная страница не загрузилась");

        // Шаг 2: Найти ссылку "Открыть счет" в хедере


        WebElement openAccLink  = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[1]/div[2]/div[2]/div/div[1]/a"));
        openAccLink.click();
        Assert.assertTrue(mainPage.isPageLoaded(), "Cтраница .\"Дистанционное открытие счета\" не загрузилась");


        WebElement  closeCookie  = driver.findElement(By.xpath("//*[@id=\"main-content\"]/div[2]/div/button"));
        WebElement  inputLastName  = driver.findElement(By.xpath("//*[@id=\"inputLastName\"]"));
        WebElement inputFirstName  = driver.findElement(By.xpath(" //*[@id=\"inputFirstName\"]"));
        WebElement inputMiddleName  = driver.findElement(By.xpath("//*[@id=\"inputMiddleName\"]"));
        WebElement inputMobile  = driver.findElement(By.xpath("//*[@id=\"inputMobile\"]"));
        WebElement inputEmail  = driver.findElement(By.xpath(" //*[@id=\"inputEmail\"]"));
        WebElement selRezRF  = driver.findElement(By.xpath("//*[@id=\"tax-resident\"]/div/div[1]/span"));
        WebElement countryBirth  = driver.findElement(By.xpath("//*[@id=\"birthCountry-other\"]"));
        WebElement representation  = driver.findElement(By.xpath("//*[@id=\"selectFilial\"]/div[1]"));
        WebElement city  = driver.findElement(By.xpath("//*[@id=\"selectFilial-multiselect-option-Москва\"]"));


        WebElement agreement1  = driver.findElement(By.xpath("//*[@id=\"acceptPersonalDataPolitics\"]"));
        WebElement agreement2  = driver.findElement(By.xpath("//*[@id=\"acceptMailing\"]"));






        closeCookie.click();
        inputLastName.sendKeys("Паниковский");
        inputFirstName.sendKeys("Михаил");
        inputMiddleName.sendKeys("Самуэльевич");
        inputMobile.sendKeys("9999999999");
        inputEmail.sendKeys("test@test.ru");
        selRezRF.click();
        countryBirth.click();
        representation.click();
        city.click();
        agreement1.click();
        agreement2.click();
        Thread.sleep(1500);
//        WebElement notRobot  = driver.findElement(By.xpath("//*[@id=\"js-button\"]"));
//        notRobot.click();
//        Thread.sleep(1500);
//        WebElement okContinue  = driver.findElement(By.xpath("//*[@id=\"buttonContinuePersonalData\"]"));
//        okContinue.click();


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