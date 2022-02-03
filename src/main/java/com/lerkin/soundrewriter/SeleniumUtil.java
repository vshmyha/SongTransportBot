package com.lerkin.soundrewriter;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.util.function.Supplier;

public class SeleniumUtil {
    private static Supplier<WebDriver> driverSupplier;

    static {
        init("ff");
    }

    public static WebDriver initDriver() {
        WebDriver driver = driverSupplier.get();
        return driver;
    }

    private static void init(String driverName) {
        switch (driverName) {
            case "chrome":
            case "chr":
            case "ch":
                WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
                driverSupplier = ChromeDriver::new;
                break;
            case "ff":
            case "firefox":
            case "fox":
            case "mozilla":
                WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
                driverSupplier = FirefoxDriver::new;
                break;
            default:
                throw new RuntimeException("Unknown driver name");
        }
    }

    public static File takeScreenshot(WebDriver webDriver) {
        File screenshotAs = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        return screenshotAs;
    }

}
