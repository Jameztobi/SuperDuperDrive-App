package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Instant;

public class NotePage {
    //driver
    private final WebDriver driver;
    private  JavascriptExecutor executor;

    //Fields
    @FindBy(name="notetitle")
    private WebElement noteTitle;

    @FindBy(name="notedescription")
    private WebElement noteDescription;


    //Button
    @FindBy(id="nav-notes-tab")
    private WebElement note_tab;

    @FindBy(name="addNewNote")
    private WebElement addNewNote;

    @FindBy(name="noteSave")
    private WebElement noteSave;

    @FindBy(name="noteEditBtn")
    private WebElement noteEditBtn;

    @FindBy(name="noteDelete")
    private WebElement noteDelete;

    @FindBy(id="success-message")
    private WebElement successMessage;


    public NotePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver,  this );
        this.executor = (JavascriptExecutor)this.driver;
    }

    public void setNote(String notetitle, String notedescription){
        this.executor.executeScript("arguments[0].value='" + notetitle + "';", this.noteTitle);
        this.executor.executeScript("arguments[0].value='" + notedescription + "';", this.noteDescription);
    }


    public String getDisplayNameTitle() {
        return this.noteTitle.getText();
    }

    public String getDisplayNameDescription() {
        return this.noteDescription.getText();
    }


    public void clickAddNote(){
        this.executor.executeScript("arguments[0].click();", this.addNewNote);
    }

    public void clickNoteTab(){
        this.executor.executeScript("arguments[0].click();", this.note_tab);
    }

    public void clickSave(){
        this.executor.executeScript("arguments[0].click();", this.noteSave);
    }

    public void clickEdit(){
        this.executor.executeScript("arguments[0].click();", this.noteEditBtn);
    }

    public void clickDelete(){
        this.executor.executeScript("arguments[0].click();", this.noteDelete);
    }

    public boolean getSuccessMessage() {
        Boolean result=false;

        if(this.successMessage.isDisplayed()){
            result=true;
        }
        this.executor.executeScript("arguments[0].click();", this.successMessage);

        return result;
    }

    public void clearNoteAndReplace( String notetitle_edited, String notedescription_edited){
        WebDriverWait wait = new WebDriverWait(driver, 20);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).clear();
        WebElement noteTitle_edited = driver.findElement(By.id("note-title"));
        noteTitle_edited.sendKeys(notetitle_edited);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).clear();
        WebElement noteDescription_edited = driver.findElement(By.id("note-description"));
        noteDescription_edited.sendKeys(notedescription_edited);

    }


}
