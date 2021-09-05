package com.youvegotnigel.automation.base;

import com.youvegotnigel.automation.pages.GoogleWeather;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class TestBase {

    public static WebDriver driver;
    public static Properties config;

    protected GoogleWeather googleWeather;
    private static final String fileSeparator = File.separator;
    public static final Logger log = LogManager.getLogger(TestBase.class.getName());

    public void LoadConfigProperty(){
        try {
            config = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "//src//test//resources//config//config.properties");
            config.load(ip);
            log.info("Properties file loaded successfully");
        }catch (Exception e){
            log.error("Configuration Properties file not found." + Arrays.toString(e.getStackTrace()));
        }

    }

    public void openBrowser(){
        // loads the config options
        LoadConfigProperty();

        switch (config.getProperty("BROWSER_TYPE")) {
            case "firefox":

                FirefoxBinary firefoxBinary = new FirefoxBinary();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setBinary(firefoxBinary);
                firefoxOptions.setHeadless(Boolean.parseBoolean(config.getProperty("SET_HEADLESS")));  //set headless mode true or false

                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions);
                log.debug("Initializing firefox driver");
                break;

            case "edge":

                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                log.debug("Initialize edge driver");
                break;

            case "chrome":

                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();

                LoggingPreferences logPrefs = new LoggingPreferences();
                logPrefs.enable(LogType.BROWSER, Level.ALL);

                options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                options.setHeadless(Boolean.parseBoolean(config.getProperty("SET_HEADLESS")));

                driver = new ChromeDriver(options);
                log.debug("Initialize chrome driver");
                break;
        }
    }

    public void tearDown(){
        driver.quit();
        log.debug("Browser is closed");
    }

    public void maximizeWindow() {
        driver.manage().window().setSize(new Dimension(1440, 900));
        driver.manage().window().maximize();
        googleWeather = new GoogleWeather(this.driver);
        log.debug("Maximizing browser window");
    }

    public void implicitWait(int time) {
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    public void explicitWait(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 3000);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void pageLoad(int time) {
        driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
    }

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
        log.debug("Deleting all cookies");
    }

    public void setEnv(){
        LoadConfigProperty();
        String baseUrl = config.getProperty("LOGIN_URL");
        driver.get(baseUrl);
        //log.debug("Loading url : " + baseUrl);
    }

    public String getGlobalVariable(String variable){

        if(variable.startsWith("_")){
            LoadConfigProperty();
            return config.getProperty(variable);
        }
        return  variable;
    }

    public void setTextInputForLabel(String label_name, String value){

        String xpath = "//label[contains(text(),'"+ label_name +"')]/following::input[1]";
        WebElement element = driver.findElement(By.xpath(xpath));
        element.sendKeys(getGlobalVariable(value));
    }

    public void setTextInputForLabel(String label_name, String index, String value){

        String xpath = "(//label[contains(text(),'"+ label_name +"')])["+ index +"]/following::input[1]";
        WebElement element = driver.findElement(By.xpath(xpath));
        element.sendKeys(getGlobalVariable(value));
    }
}
