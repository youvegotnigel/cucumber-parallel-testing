package com.youvegotnigel.automation.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/default",
        glue = {"com/youvegotnigel/automation/stepdefs"},
        tags = "@regression and not @ignore",
        dryRun = false,
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json"
        })
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
