package com.youvegotnigel.automation.stepdefs;

import com.youvegotnigel.automation.base.PageBase;
import com.youvegotnigel.automation.base.TestBase;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class BasePageStepDefinitions extends TestBase{

    PageBase pageBase = new PageBase(driver);
    public static final Logger log = LogManager.getLogger(BasePageStepDefinitions.class.getName());

    @And("^I wait for \"(.+)\" seconds$")
    public void wait_time(String time) {
        implicitWait(Integer.parseInt(time));
    }

    @And("I click on {string} button")
    public void click_on_button(String text) {

        if(text.matches(".*\\[[\\d.]]")){
            var valueAndIndex = getValueAndIndex(text);
            pageBase.clickOnButtonByName(valueAndIndex[0], valueAndIndex[1]);
        }else {
            pageBase.clickOnButtonByName(text);
        }
    }

    @And("I click on {string} link")
    public void click_on_link(String text) {

        if(text.matches(".*\\[[\\d.]]")){
            var valueAndIndex = getValueAndIndex(text);
            pageBase.clickOnLinkByName(valueAndIndex[0], valueAndIndex[1]);
        }else {
            pageBase.clickOnLinkByName(text);
        }
    }

    @And("^I click on the '(.+)' (?: |button|link|text)$")
    public void click_on_normalize_space(String text) {

        if(text.matches(".*\\[[\\d.]]")){
            var valueAndIndex = getValueAndIndex(text);
            pageBase.clickOnNormalizeSpace(text, valueAndIndex[1]);
        }else {
            pageBase.clickOnNormalizeSpace(text);
        }
    }

    @And("I should see the text {string} displayed")
    public void text_is_displayed(String text) {

        if(text.matches(".*\\[[\\d.]]")){
            var valueAndIndex = getValueAndIndex(text);
            Assert.assertTrue(pageBase.isDisplayedInNormalizeSpace(valueAndIndex[0], valueAndIndex[1]),"Not found text ::: "+ text);
        }else {
            Assert.assertTrue(pageBase.isDisplayedInNormalizeSpace(text),"Not found text ::: "+ text);
        }
    }

    @And("I should not see the text {string} displayed")
    public void text_is_not_displayed(String text) {

        if(text.matches(".*\\[[\\d.]]")){
            var valueAndIndex = getValueAndIndex(text);
            Assert.assertFalse(pageBase.isDisplayedInNormalizeSpace(valueAndIndex[0], valueAndIndex[1]),"Found text ::: "+ text);
        }else {
            Assert.assertFalse(pageBase.isDisplayedInNormalizeSpace(text),"Found text ::: "+ text);
        }
    }

    @And("^I set value \"(.+)\" for \"(.+)\"$")
    public void set_text_for_label(String answer, String question) {

        if(question.matches(".*\\[[\\d.]]")){
            var valueAndIndex = getValueAndIndex(question);
            setTextInputForLabel(valueAndIndex[0], valueAndIndex[1], answer);
        }else {
            setTextInputForLabel(question, answer);
        }
    }

    public String[] getValueAndIndex(String value) {
        String[] values = value.split(Pattern.quote("["));
        values[1] = values[1].replaceAll("[^\\d.]", "");
        return values;
    }


}
