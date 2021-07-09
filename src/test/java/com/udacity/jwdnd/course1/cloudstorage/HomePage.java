package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    
    @FindBy(id="logout-btn")
    private WebElement logout;
    private final WebDriver driver;
    private JavascriptExecutor executor;

    public HomePage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
        this.executor = (JavascriptExecutor)this.driver;
    }

    public void clickLogoutButton(){
        this.executor.executeScript("arguments[0].click();", logout);
    }
}
