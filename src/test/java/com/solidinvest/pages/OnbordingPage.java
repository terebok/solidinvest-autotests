package com.solidinvest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OnbordingPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public OnbordingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));

//    public WebElement openAccLink  = driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[1]/div[2]/div[2]/div/div[1]/a"));
//    public WebElement  inputLastName  = driver.findElement(By.xpath(" //*[@id=\"inputLastName\"]"));
//    public WebElement inputFirstName  = driver.findElement(By.xpath(" //*[@id=\"inputFirstName\"]"));
//    public WebElement inputMiddleName  = driver.findElement(By.xpath("//*[@id=\"inputMiddleName\"]"));
//    public WebElement inputMobile  = driver.findElement(By.xpath("//*[@id=\"inputMobile\"]"));
//    public WebElement inputEmail  = driver.findElement(By.xpath(" //*[@id=\"inputEmail\"]"));
    }

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasContactInformation() {
        try {
            String pageSource = driver.getPageSource().toLowerCase();
            return pageSource.contains("адрес") ||
                    pageSource.contains("телефон") ||
                    pageSource.contains("email") ||
                    pageSource.contains("address") ||
                    pageSource.contains("phone") ||
                    pageSource.contains("e-mail") ||
                    pageSource.contains("контакт");
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}