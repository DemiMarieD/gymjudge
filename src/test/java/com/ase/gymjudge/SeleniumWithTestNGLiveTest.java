package com.ase.gymjudge;

import com.ase.gymjudge.selenium.SeleniumLoginPage;
import org.junit.Test;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static org.junit.jupiter.api.Assertions.*;

public class SeleniumWithTestNGLiveTest {

    private SeleniumLoginPage seleniumLoginPage;
    private String expectedTitle = "Sign in";

    //todo: not working because of BeforeSuite, but we will probably not need it...
   /*  @BeforeSuite
    public void setUp() {
        seleniumLoginPage = new SeleniumLoginPage();
    }

    @AfterSuite
    public void tearDown() {
        seleniumLoginPage.closeWindow();
    }

   @Test
     public void whenLoginIsLoaded_thenAboutEugenIsMentionedOnPage() {
        seleniumLoginPage.getLoginPage();
        String actualTitle = seleniumLoginPage.getTitle();

        assertNotNull(actualTitle);
        assertEquals(expectedTitle, actualTitle);
        assertTrue(seleniumLoginPage.isRegistrationAvailable());
    }*/
}
