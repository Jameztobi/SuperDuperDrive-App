package com.udacity.jwdnd.course1.cloudstorage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
   //driver
   public final WebDriver driver;

   JavascriptExecutor executor;

   //field
   @FindBy(name="invalid-username")
   private WebElement invalidUsername;

   @FindBy(name="logged-out")
   private WebElement logged;

   @FindBy(name="username")
   private WebElement username;

   @FindBy(name="password")
   private WebElement password;

   //links and buttons
   @FindBy(name="login")
   private WebElement login;

   @FindBy(name="signUp")
   private WebElement signUpBtn;

   public LoginPage(WebDriver driver){
      this.driver=driver;
      PageFactory.initElements(driver, this);
      this.executor = (JavascriptExecutor)this.driver;
   }

   public void setLogin( String username, String password){
      this.executor.executeScript("arguments[0].value='" + username + "';", this.username);
      this.executor.executeScript("arguments[0].value='" + password + "';", this.password);
   }

   //clicks
   public void clickLogin(){
      this.executor.executeScript("arguments[0].click();", login);
   }

   public void clickSignUp(){
      this.executor.executeScript("arguments[0].click();", signUpBtn);
   }




}
