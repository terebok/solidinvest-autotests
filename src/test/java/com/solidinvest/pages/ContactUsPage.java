package com.solidinvest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactUsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public void clickContactsLink() {
        WebElement contactsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(), 'Контакты') or contains(text(), 'контакты')]")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", contactsLink);
        js.executeScript("arguments[0].click();", contactsLink);
    }

    public void clickContactUsLink() {
        WebElement contactUsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(), 'Связаться') or contains(text(), 'связаться')]")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", contactUsLink);
        js.executeScript("arguments[0].click();", contactUsLink);
    }

    public void waitForForm() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form")));
    }

    public void fillName(String name) {
        WebElement field = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@name='name' or @placeholder='Имя' or @placeholder='Ваше имя']")));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", field);
        field.clear();
        field.sendKeys(name);
    }

    public void fillPhone(String phone) {
        WebElement field = driver.findElement(
                By.xpath("//input[@name='phone' or @placeholder='Телефон' or @placeholder='Ваш телефон']"));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", field);
        field.clear();
        field.sendKeys(phone);
    }

    public void fillEmail(String email) {
        WebElement field = driver.findElement(
                By.xpath("//input[@name='email' or @placeholder='Email' or @placeholder='Ваш email']"));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", field);
        field.clear();
        field.sendKeys(email);
    }

    public void fillMessage(String message) {
        WebElement field = driver.findElement(
                By.xpath("//textarea[@name='message' or @placeholder='Сообщение' or @placeholder='Ваше сообщение']"));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", field);
        field.clear();
        field.sendKeys(message);
    }

    public void checkAllCheckboxes() {
        for (WebElement checkbox : driver.findElements(By.xpath("//input[@type='checkbox']"))) {
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
            if (!checkbox.isSelected()) {
                js.executeScript("arguments[0].click();", checkbox);
            }
        }
    }

    public void submitForm() {
        WebElement submitButton = driver.findElement(
                By.xpath("//button[@type='submit' or contains(text(), 'Отправить') or contains(text(), 'Отправить сообщение')]"));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", submitButton);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        js.executeScript("arguments[0].click();", submitButton);
    }

    public boolean isSuccessMessagePresent() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(), 'Спасибо') or contains(text(), 'отправлено') or contains(text(), 'успешно') or contains(text(), 'принято')]")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}