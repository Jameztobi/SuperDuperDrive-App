package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
    @LocalServerPort
    private int port;
    private String firstname="Oluwatobi";
    private String lastname="James";
    private String username="Tobi";
    private String password="password";
    private String url="www.google.com";

    private String notetitle="Lord of the Rings";
    private String notedescription="This is a fierce movie contain different forms of plays and excitement";

    private String notetitleEdited="Lord of the Rings";
    private String notedescriptionEdited="This is a fierce movie contain different forms of plays and excitement but it ended sadly";


    LoginPage loginPage;

    NotePage notePage;

    CredentialPage credentialPage;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private EncryptionService encryptionService;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        loginPage = new LoginPage(driver);
        notePage= new NotePage(driver);
        credentialPage = new CredentialPage(driver);

    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void testUnauthorizedUserCanOnlyAccessLoginAndSignUpPage() throws InterruptedException {
        String username="Tobi";
        String password="password";

        driver.get("http://localhost:" + this.port + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setLogin(username, password);

        loginPage.clickLogin();

        Thread.sleep(3000);

        Assertions.assertEquals("Login", driver.getTitle());

        Thread.sleep(3000);

        loginPage.clickSignUp();

        Thread.sleep(3000);

        Assertions.assertEquals("Sign Up", driver.getTitle());

    }

    @Test
    public void testSignUpNewUser() throws InterruptedException {

        driver.get("http://localhost:" + this.port + "/login");
        //LoginPage loginPage = new LoginPage(driver);
        Thread.sleep(3000);

        loginPage.clickSignUp();
        Thread.sleep(3000);

        SignupPage signup = new SignupPage(driver);
        signup.setSignUp(firstname, lastname, username, password);
        Thread.sleep(3000);
        signup.clickSignUp();

        Thread.sleep(3000);

        signup.clickLogin();

        Thread.sleep(3000);
        Assertions.assertEquals("Login", driver.getTitle());

    }

    @Test
    public void testNewUserLogIn() throws InterruptedException {
        testSignUpNewUser();
        Thread.sleep(3000);
        loginPage.setLogin(username, password);

        Thread.sleep(3000);

        loginPage.clickLogin();

        Thread.sleep(3000);

        Assertions.assertEquals("Home", driver.getTitle());

    }

    @Test
    public void testNewUserLogInAndLogout() throws InterruptedException {
        testSignUpNewUser();
        Thread.sleep(3000);
        loginPage.setLogin(username, password);

        Thread.sleep(3000);

        loginPage.clickLogin();

        Thread.sleep(3000);

        Assertions.assertEquals("Home", driver.getTitle());

        Thread.sleep(3000);
        HomePage homePage = new HomePage(driver);
        Thread.sleep(3000);

        homePage.clickLogoutButton();
        Thread.sleep(3000);

        Assertions.assertEquals("Login", driver.getTitle());


    }

    @Test
    public void testHomePageIsNotAvailableAfterLogout() throws InterruptedException {
        testNewUserLogInAndLogout();
        Thread.sleep(3000);
        driver.get("http://localhost:" + this.port + "/home");

        Thread.sleep(3000);
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void testNoteCreationAndVerifyNote() throws InterruptedException {
        testNewUserLogIn();
        Thread.sleep(3000);
        notePage.clickNoteTab();
        Thread.sleep(3000);
        notePage.clickAddNote();
        Thread.sleep(3000);
        notePage.setNote(notetitle, notedescription);
        Thread.sleep(2000);
        notePage.clickSave();
        Thread.sleep(2000);
        Assertions.assertTrue(notePage.getSuccessMessage());
        Thread.sleep(2000);
        notePage.clickNoteTab();
        Thread.sleep(2000);
        Assertions.assertEquals(notetitle, notePage.getDisplayNameTitle());
        Assertions.assertEquals(notedescription, notePage.getDisplayNameDescription());
        Thread.sleep(2000);
    }

    @Test
    public void testEditNoteAndVerifyChanges() throws InterruptedException {
        testNoteCreationAndVerifyNote();
        notePage.clickEdit();
        Thread.sleep(2000);
        notePage.clearNoteAndReplace(notetitleEdited, notedescriptionEdited);
        Thread.sleep(3000);
        notePage.clickSave();
        Thread.sleep(3000);
        Assertions.assertTrue(notePage.getSuccessMessage());
        Thread.sleep(3000);
        notePage.clickNoteTab();
        Thread.sleep(3000);
        Assertions.assertEquals(this.notetitleEdited, notePage.getDisplayNameTitle());
        Assertions.assertEquals(this.notedescriptionEdited, notePage.getDisplayNameDescription());
        Thread.sleep(3000);

    }

    @Test
    public void testDeleteNoteAndVerifyDisplay() throws InterruptedException {
        testNoteCreationAndVerifyNote();
        Thread.sleep(2000);
        notePage.clickDelete();
        Thread.sleep(2000);
        Assertions.assertTrue(notePage.getSuccessMessage());
        Thread.sleep(3000);
        notePage.clickNoteTab();
        Thread.sleep(3000);
        Assertions.assertEquals("", notePage.getDisplayNameTitle());
        Assertions.assertEquals("", notePage.getDisplayNameDescription());
        Thread.sleep(3000);

    }

    @Test
    public void testSetCredentialVerifyDetailsAndEncryption() throws InterruptedException {
        testNewUserLogIn();
        Thread.sleep(3000);
        credentialPage.clickCredTab();
        Thread.sleep(2000);
        credentialPage.clickAddCred();
        Thread.sleep(2000);
        credentialPage.setCredential(url, username, password);
        Thread.sleep(2000);
        credentialPage.clickSaveCred();
        Thread.sleep(2000);
        Assertions.assertTrue(credentialPage.getSuccessMessage());
        Thread.sleep(2000);
        credentialPage.clickCredTab();
        Thread.sleep(2000);
        Assertions.assertEquals(url, credentialPage.getDisplayCredentialUrl());
        Assertions.assertEquals(username, credentialPage.getCredUsername());
        List<Credentials> credential = credentialService.getCredential(1);
        Assertions.assertEquals(credentialPage.getDisplayCredentialPassword(), encryptionService.encryptValue(password, credential.get(0).getKey()));

    }

    @Test
    public void testCredentialEditAndViewDetails() throws InterruptedException {
        testSetCredentialVerifyDetailsAndEncryption();
        Thread.sleep(2000);
        credentialPage.clickEditCred();
        Thread.sleep(2000);
        credentialPage.clearCredentialAndReplace("mylink.net", "mybatis", "helper");
        Thread.sleep(2000);
        credentialPage.clickSaveCred();
        Thread.sleep(2000);
        Assertions.assertTrue(credentialPage.getSuccessMessage());
        Thread.sleep(2000);
        credentialPage.clickCredTab();
        Thread.sleep(2000);
        Assertions.assertEquals("mylink.net", credentialPage.getDisplayCredentialUrl());
        Assertions.assertEquals("mybatis", credentialPage.getCredUsername());
        List<Credentials> credential = credentialService.getCredential(1);
        Assertions.assertEquals(credentialPage.getDisplayCredentialPassword(), encryptionService.encryptValue("helper", credential.get(0).getKey()));




    }


    @Test
    public void testDeleteCredential() throws InterruptedException {
        testSetCredentialVerifyDetailsAndEncryption();
        Thread.sleep(2000);
        credentialPage.clickDeleteCred();
        Thread.sleep(2000);
        Assertions.assertTrue(credentialPage.getSuccessMessage());

    }






}
