package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Creates WebDriver instances based on configuration.
 */
public final class DriverFactory {

    private DriverFactory() {}

    public static WebDriver createDriver() {
        String browser = Config.getBrowser().toLowerCase();
        boolean headless = Config.isHeadless();

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions ff = new FirefoxOptions();
                if (headless) ff.addArguments("-headless");
                ff.addArguments("--width=1920");
                ff.addArguments("--height=1080");
                return new FirefoxDriver(ff);
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions ch = new ChromeOptions();
                ch.addArguments("--disable-notifications");
                ch.addArguments("--disable-popup-blocking");
                ch.addArguments("--remote-allow-origins=*");
                ch.addArguments("--window-size=1920,1080");
                if (headless) {
                    ch.addArguments("--headless=new");
                    ch.addArguments("--no-sandbox");
                    ch.addArguments("--disable-dev-shm-usage");
                }
                return new ChromeDriver(ch);
        }
    }
}


