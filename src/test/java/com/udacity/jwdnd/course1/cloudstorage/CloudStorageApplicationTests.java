package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    //random port
    @LocalServerPort
    private int port;

    //Webdriver driver
    private static WebDriver driver;

    public String baseURL;

    //LOgin Details
    String firstname = "David";
    String lastname = "Kings";
    String username = "dave.kgs";
    String password = "david";

    //note info
    String notetitle = "This is my note";
    String notedescription = "Every one needs to make a note of their regular activities on a daily basics";

    //note edited info
    String note_edited = notetitle + " edited";
    String noteDescription_edited = notedescription + " edited";

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private NoteService noteService;

    private EncryptionService encryptionService;


    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        baseURL = "http://localhost:" + port;
    }

    @AfterEach
    public void afterEach() throws InterruptedException {
        if (this.driver != null) {
            Thread.sleep(2000);
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void getLoginPage() {
        driver.get(baseURL + "/login");
        assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(2)
    public void checkForAccessByOnlyAuthorizedUsers() throws InterruptedException {
        driver.get(baseURL + "/home");
        assertEquals("Login", driver.getTitle());
        Thread.sleep(2000);

        driver.get(baseURL + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

    }

    @Test
    @Order(3)
    public void checkIfUserSignUpWasSuccessful() throws InterruptedException {
        //get the signup page
        driver.get(baseURL + "/signup");

        //Initializing the web driver
        SignupPage signupPage = new SignupPage(driver);

        //passing in user input
        signupPage.signup(firstname, lastname, username, password);

        //Testing the user input
        assertEquals(firstname, signupPage.getFirstNameField());
        assertEquals(lastname, signupPage.getLastNameField());
        assertEquals(username, signupPage.getUsernameField());
        assertEquals(password, signupPage.getPasswordField());

        //sleep for 2 seconds
        Thread.sleep(2000);

        //Click signup button
        signupPage.clickSignUpButton();

        //sleep for 2 seconds
        Thread.sleep(2000);

        //Click login in back
        signupPage.clickLoginInBack();

        //sleep for 2 seconds
        Thread.sleep(2000);

        //test if the user has been redirected to login page
        new WebDriverWait(driver, 4).until(ExpectedConditions.titleIs("Login"));

        assertEquals("Login", driver.getTitle());

    }

    @Test
    @Order(4)
    public void checkIfUserLoginWasSuccessful() throws InterruptedException {
        checkIfUserSignUpWasSuccessful();

        // get the login page
        driver.get(baseURL + "/login");

        //Initializing login page with the driver
        LoginPage loginPage = new LoginPage(driver);

        //get the users input for login
        loginPage.loginDetails(username, password);

        //Test
        assertEquals(username, loginPage.getUsernameField());
        assertEquals(password, loginPage.getPasswordField());

        //sleep for 2 seconds
        Thread.sleep(2000);

        //Click login button
        loginPage.clickLoginButton();

        //check if the home page was served
        assertEquals("Home", driver.getTitle());

    }


    @Test
    @Order(5)
    public void checkIfUserCanAccessHomePageAfterLogOut()
            throws InterruptedException {

        checkIfUserSignUpWasSuccessful();

        //Get the login Page
        driver.get(baseURL + "/login");

        //Initializing the web driver for the login page
        LoginPage loginPage = new LoginPage(driver);

        //get the users input for login
        loginPage.loginDetails(username, password);

        //Test
        assertEquals(username, loginPage.getUsernameField());
        assertEquals(password, loginPage.getPasswordField());

        //sleep for 2 seconds
        Thread.sleep(2000);

        //Click login button
        loginPage.clickLoginButton();

        //Check if the user is in the home page
        assertEquals("Home", driver.getTitle());

        //sleep for 2 seconds
        Thread.sleep(2000);

        //Initializing the web driver for the login page for home page
        HomePage homePage = new HomePage(driver);

        //to login out of the home page
        homePage.clickLogoutButton();

        //Wait ustil the login page is served
        new WebDriverWait(driver, 4).until(ExpectedConditions.titleIs("Login"));

        //To confirm that the login Page was been served
        assertEquals("Login", driver.getTitle());

        //sleep for 2 seconds
        Thread.sleep(2000);

        //Try accessing home page after logging out
        driver.get(baseURL + "/home");

        //To check if the user is still on the login page
        assertEquals("Login", driver.getTitle());
    }


    @Test
    @Order(6)
    public void createNoteAndViewNote() throws InterruptedException {
        checkIfUserLoginWasSuccessful();

        //sleep for 2 seconds
        Thread.sleep(2000);

        //Initializing the web driver for note page
        NotePage notesPage = new NotePage(driver);

        // Click on the notes tab:
        notesPage.clickNoteTab();

        Thread.sleep(2000);

        // simulate user to click "Add/Edit a Note" button to add new note:
        notesPage.clickAddNoteBtn();

        //sleep for 2 seconds
        Thread.sleep(2000);

        // stimulating user input
        notesPage.addNote(notetitle, notedescription);

        //sleep for 2 seconds
        Thread.sleep(2000);

        // Click save button
        notesPage.clickSaveNoteBtn();

        //sleep for 2 seconds
        Thread.sleep(2000);

        //click on the success continue link
        notesPage.clickSuccessLoginBtn();

        Thread.sleep(2000);


        // Click Notes tab:
        notesPage.clickNoteTab();

        Thread.sleep(2000);

        assertEquals(notetitle, notesPage.getNoteTitleText());
        assertEquals(notedescription, notesPage.getNoteDescriptionText());

        Thread.sleep(2000);

    }


    @Test
    @Order(7)
    public void editCreatedNoteAndViewNote() throws InterruptedException {

        createNoteAndViewNote();

        Thread.sleep(2000);

        //Initializing the web driver for note page
        NotePage notesPage = new NotePage(driver);

        // Click Notes tab:
        notesPage.clickNoteTab();

        Thread.sleep(2000);

        // simulating user clicking the edit button
        notesPage.clickEditBtn();

        Thread.sleep(2000);

        // simulate user to editing note with new data:
        notesPage.editNote(notetitle + " Edit", notedescription + " Edit");

        Thread.sleep(2000);

        // Click save button :
        notesPage.clickSaveEditNoteBtn();

        Thread.sleep(2000);

        notesPage.clickSuccessLoginBtn();

        Thread.sleep(2000);

        // user click Notes tab:
        notesPage.clickNoteTab();

        Thread.sleep(2000);

        assertEquals(notetitle + " Edit", notesPage.getNoteTitleText());
        assertEquals(notedescription + " Edit", notesPage.getNoteDescriptionText());


    }


    @Test
    @Order(8)
    public void checkIfUserCanDeleteNoteAndView() throws InterruptedException {
        createNoteAndViewNote();
        //sleep for 2 seconds
        Thread.sleep(2000);

        //Initializing the web driver for note page
        NotePage notesPage = new NotePage(driver);

        // Click Notes tab:
        notesPage.clickNoteTab();

        Thread.sleep(2000);

        // simulating the user clicking on the delete button
        notesPage.clickDeleteBtn();

        Thread.sleep(2000);

        //check if edit successful message is being displayed
        assertTrue(notesPage.clickSuccessLoginBtn());

    }


    @Test
    @Order(9)
    public void createCredentialAndViewCredential() throws InterruptedException {

        checkIfUserLoginWasSuccessful();

        //sleep for 2 seconds
        Thread.sleep(2000);

        // initiating the encryption service class
        encryptionService = new EncryptionService();

        //Initializing the web driver for credentialsPage
        CredentialPage credentialsPage = new CredentialPage(driver);


        // simulating a user clicking on the credential tab
        credentialsPage.clickCredTab();

        //sleep for 2 seconds
        Thread.sleep(2000);

        int i = 1;

        // simulating a user clicking on the add credential button
        credentialsPage.clickAddCredBtn();
        Thread.sleep(2000);

        credentialsPage.enterCredentialData("cred " + i, "cred" + i, "cred" + i);
        Thread.sleep(2000);

        credentialsPage.clickAddCredBtn();
        Thread.sleep(2000);

        List<Credentials> credential = this.credentialService.getCredential(i);

        assertTrue(credentialsPage.clickSuccessLoginButton());
        Thread.sleep(2000);
        new WebDriverWait(driver, 4).until(ExpectedConditions.titleIs("Home"));
        Thread.sleep(2000);

        // simulating a user clicking on the credential tab
        credentialsPage.clickCredTab();
        Thread.sleep(2000);


        assertEquals("cred " + i, credentialsPage.getUrlText(i));
        assertEquals("cred" + i, credentialsPage.getUsernameText(i));
        assertEquals(this.encryptionService.encryptValue("cred" + i, credential.get(0).getKey()), credentialsPage.getPasswordText(i));
        

    }

    @Test
    @Order(10)
    public void editCreatedCredentialAndViewEditedCredential() throws InterruptedException {

        createCredentialAndViewCredential();

         //sleep for a 2 seconds
        Thread.sleep(2000);

        // initiating the encryption service class
        encryptionService = new EncryptionService();

        //Initializing the web driver for credentialsPage
        CredentialPage credentialsPage = new CredentialPage(driver);

        // simulating a user clicking on the credential tab
        credentialsPage.clickCredTab();

        //sleep for a 2 seconds
        Thread.sleep(2000);


        int i = 1;

        // simulate user to click on Add new credential button:
        credentialsPage.clickEditBtn(i);

        //sleep for a 2 seconds
        Thread.sleep(2000);


        credentialsPage.enterCredentialData("cred " + i + " edit", "cred" + i, "cred" + i);
        Thread.sleep(2000);

        credentialsPage.clickAddCredBtn();

        Thread.sleep(2000);

        credentialsPage.clickSuccessLoginButton();
        Thread.sleep(2000);


        credentialsPage.clickCredTab();
        Thread.sleep(2000);

        // initialize Credential object
        List<Credentials> credential = this.credentialService.getCredential(i);

        new WebDriverWait(driver, 4).until(ExpectedConditions.titleIs("Home"));

        assertEquals("cred " + i + " edit", credentialsPage.getUrlText(i));
        assertEquals("cred" + i, credentialsPage.getUsernameText(i));
        assertEquals(this.encryptionService.encryptValue("cred" + i, credential.get(0).getKey()), credentialsPage.getPasswordText(i));


    }

    @Test
    @Order(11)
    public void deleteCredentialAndViewDisplay() throws InterruptedException {
        createCredentialAndViewCredential();

        Thread.sleep(2000);

        // initiating the encryption service class
        encryptionService = new EncryptionService();

        //Initializing the web driver for credentialsPage
        CredentialPage credentialsPage = new CredentialPage(driver);


        // simulating a user clicking on the credential tab
        credentialsPage.clickCredTab();

        Thread.sleep(2000);

        int i=1;

        List<Credentials> credential = this.credentialService.getCredential(i);

        credentialsPage.clickDeleteBtn();

        Thread.sleep(2000);
        
        assertTrue(credentialsPage.clickSuccessLoginButton());


    }

}
