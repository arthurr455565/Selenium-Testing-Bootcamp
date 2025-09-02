//Author: Bishal Roy

package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.AmazonSearchBar;


public class OpenGoogleTest {

    private WebDriver driver;

    @Test
    public void openGoogleHomePage() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        // Wait 3 seconds to see the browser open
        sleep(3000);

        // Navigate to Amazon, wait 3s, then search for iPhone 14 via Page Object
        driver.get("https://www.amazon.com");
        sleep(3000);

        AmazonSearchBar searchBar = new AmazonSearchBar(driver);
        searchBar.search("iphone 14");
        sleep(3000);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}


