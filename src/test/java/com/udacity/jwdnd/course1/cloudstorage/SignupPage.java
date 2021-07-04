package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    public final WebDriver driver;

    JavascriptExecutor executor;

    @FindBy(name="firstName")
    public WebElement firstname;
    @FindBy(name="lastName")
    public WebElement lastname;
    @FindBy(name="username")
    public WebElement username;
    @FindBy(name="password")
    public WebElement password;

    //clicks
    @FindBy(name="sign-up")
    public WebElement signUp;

    @FindBy(name="login-redirect")
    public WebElement login;


    public SignupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.executor= (JavascriptExecutor)this.driver;

    }

    public void setSignUp(String firstname, String lastname, String username, String password){
        this.executor.executeScript("arguments[0].value='" + firstname + "';", this.firstname);
        this.executor.executeScript("arguments[0].value='" + lastname + "';", this.lastname);
        this.executor.executeScript("arguments[0].value='" + username + "';", this.username);
        this.executor.executeScript("arguments[0].value='" + password + "';", this.password);
    }

    public void clickLogin(){
        this.executor.executeScript("arguments[0].click();", this.login);
    }

    public void clickSignUp(){
        this.executor.executeScript("arguments[0].click();", this.signUp);
    }

}
