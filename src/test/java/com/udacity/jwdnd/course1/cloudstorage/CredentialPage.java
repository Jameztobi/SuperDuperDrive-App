package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {
    private final WebDriver driver;
    private JavascriptExecutor executor;

    //Fields
    @FindBy(id="nav-credentials-tab")
    private WebElement credTab;

    @FindBy(id="credUserId")
    private WebElement credUserId;

    @FindBy(id="credential-url")
    private WebElement credUrl;

    @FindBy(id="credential-username")
    private WebElement credUsername;


    @FindBy(id="credential-password")
    private WebElement credPassword;


    @FindBy(id="credUrlText")
    private WebElement displayCredentialUrl;

    @FindBy(id="credUsernameText")
    private WebElement displayCredentialUsername;

    @FindBy(id="credPasswordText")
    private WebElement displayCredentialPassword;



    //Button
    @FindBy(name="addNewCredential")
    private WebElement addNewCredential;

    @FindBy(name="credSave")
    private WebElement credentialSave;

    @FindBy(id="cred-EditBtn")
    private WebElement credEditBtn;

    @FindBy(name="credDelete")
    private WebElement credDelete;

    @FindBy(id="success-message")
    private WebElement successMessage;




    public CredentialPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.executor = (JavascriptExecutor)this.driver;
    }

    public void setCredential(String url, String username, String password){
        this.executor.executeScript("arguments[0].value='" + url + "';", this.credUrl);
        this.executor.executeScript("arguments[0].value='" + username + "';", this.credUsername);
        this.executor.executeScript("arguments[0].value='" + password + "';", this.credPassword);
    }

    public String getDisplayCredentialUrl() {
        return displayCredentialUrl.getText();
    }

    public String getCredUsername() {
        return displayCredentialUsername.getText();
    }

    public void clickAddCred(){
        this.executor.executeScript("arguments[0].click();", this.addNewCredential);
    }

    public void clickCredTab(){
        this.executor.executeScript("arguments[0].click();", this.credTab);
    }

    public void clickSaveCred(){
        this.executor.executeScript("arguments[0].click();", this.credentialSave);
    }

    public void clickEditCred(){
        this.executor.executeScript("arguments[0].click();", this.credEditBtn);
    }

    public void clickDeleteCred(){
        this.executor.executeScript("arguments[0].click();", this.credDelete);
    }

    public String getDisplayCredentialPassword() {
        return displayCredentialPassword.getText();
    }

    public boolean getSuccessMessage() {
        Boolean result=false;

        if(this.successMessage.isDisplayed()){
            result=true;
        }
        this.executor.executeScript("arguments[0].click();", this.successMessage);

        return result;
    }

    public void clearCredentialAndReplace( String url, String username, String password){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).clear();
        WebElement credentialUrlEdited = driver.findElement(By.id("credential-url"));
        credentialUrlEdited.sendKeys(url);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username"))).clear();
        WebElement credentialUsernameEdited = driver.findElement(By.id("credential-username"));
        credentialUsernameEdited.sendKeys(username);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password"))).clear();
        WebElement credentialpasswordEdited = driver.findElement(By.id("credential-password"));
        credentialpasswordEdited.sendKeys(password);

    }
}
