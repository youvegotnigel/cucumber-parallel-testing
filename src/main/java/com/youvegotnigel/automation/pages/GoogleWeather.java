package com.youvegotnigel.automation.pages;

import com.youvegotnigel.automation.base.PageBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class GoogleWeather extends PageBase {

    private By cityName = By.xpath("//div[@id='wob_loc']");
    private By inputSearch = By.xpath("//input[@aria-label='Search']");

    private By tempInC = By.xpath("//span[@id='wob_tm']");
    private By tempInF = By.xpath("//span[@id='wob_ttm']");

    private By tempIconC = By.xpath("//a[@class='wob_t']/span[@aria-label='°Celsius']");
    private By tempIconF = By.xpath("//a[@class='wob_t']/span[@aria-label='°Fahrenheit']");

    public static final Logger log = LogManager.getLogger(GoogleWeather.class.getName());

    //Constructor
    public GoogleWeather(WebDriver driver) {
        super(driver);
    }

    public void inputTextInSearch(String text){
        clearText(inputSearch);
        setText(inputSearch, text);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(inputSearch).sendKeys(Keys.ENTER);
    }

    public String getDisplayedCityName(){
        return driver.findElement(cityName).getText();
    }
}
