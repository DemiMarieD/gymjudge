package com.ase.gymjudge.seleniumTest;

import com.ase.gymjudge.selenium.SeleniumLoginPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SeleniumLogin_WithJUnitLiveTest {
    private static SeleniumLoginPage seleniumLoginPage;
    private String expectedTitle = "Sign in";

    @BeforeClass
    public static void setUp() {
        seleniumLoginPage = new SeleniumLoginPage();
    }

    @AfterClass
    public static void tearDown() {
        seleniumLoginPage.closeWindow();
    }

 /*   @Test
    public void whenLoginIsLoaded_thenSignUpLinkIsAvailable() {
        seleniumLoginPage.getLoginPage();
        String actualTitle = seleniumLoginPage.getTitle();

        assertNotNull(actualTitle);
        assertEquals(expectedTitle, actualTitle);
        assertTrue(seleniumLoginPage.isRegistrationAvailable());
    } */

}
