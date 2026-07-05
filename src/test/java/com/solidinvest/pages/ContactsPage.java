package com.solidinvest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ContactsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
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