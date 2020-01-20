package com.ase.gymjudge.selenium;

import com.ase.gymjudge.configuration.SeleniumConfig;
import org.openqa.selenium.By;

public class SeleniumLoginPage {
    private SeleniumConfig config;
    //todo: Application has to be run first...
    private String url = "http://localhost:8080/";

    public SeleniumLoginPage() {
        config = new SeleniumConfig();
        config.getDriver().get(url);
    }

    public void closeWindow() {
        this.config.getDriver().close();
    }

    public String getTitle() {
        return this.config.getDriver().getTitle();
    }

    public void getLoginPage(){
        clickLogin();
    }

    private void clickLogin() {
        this.config.getDriver().findElement(By.linkText("Login")).click();
    }

    public boolean isRegistrationAvailable() {
        return this.config.getDriver()
                .findElement(By.cssSelector("a[href*='signup']"))
                .isDisplayed();
    }


  /*  public void getAboutBaeldungPage() {
        closeOverlay();
        clickAboutLink();
        clickAboutUsLink();
    }

     private void closeOverlay() {
        List<WebElement> webElementList = this.config.getDriver()
                .findElements(By.tagName("a"));
        if (webElementList != null) {
            webElementList.stream()
                    .filter(webElement -> "Close".equalsIgnoreCase(webElement.getAttribute("title")))
                    .filter(WebElement::isDisplayed)
                    .findAny()
                    .ifPresent(WebElement::click);
        }
    }

    private void clickAboutLink() {
        this.config.getDriver().findElement(By.partialLinkText("About")).click();
    }

    private void clickAboutUsLink() {
        Actions builder = new Actions(config.getDriver());
        WebElement element = this.config.getDriver()
                .findElement(By.partialLinkText("About Baeldung"));
        builder.moveToElement(element)
                .build()
                .perform();
    }

    public boolean isAuthorInformationAvailable() {
        return this.config.getDriver()
                .findElement(By.cssSelector("article > .row > div"))
                .isDisplayed();
    }*/


  }
