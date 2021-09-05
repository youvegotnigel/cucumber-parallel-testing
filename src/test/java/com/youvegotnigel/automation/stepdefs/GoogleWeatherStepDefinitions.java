package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.base.TestBase;
import com.youvegotnigel.automation.pages.GoogleWeather;
import io.cucumber.java.en.Given;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class GoogleWeatherStepDefinitions extends TestBase {

    GoogleWeather googleWeather = new GoogleWeather(driver);

    @Given("The Application has been launched")
    public void application_is_launched() {
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(googleWeather.getDisplayedCityName(), "Colombo");
        sa.assertAll();
    }

    @Given("I enter {string} in search box")
    public void set_city(String city){
        googleWeather.inputTextInSearch("weather " + city);
    }

    @Given("I should see city name {string} displayed")
    public void is_city_displayed(String city) {
        Assert.assertEquals(googleWeather.getDisplayedCityName(), city);
    }
}
