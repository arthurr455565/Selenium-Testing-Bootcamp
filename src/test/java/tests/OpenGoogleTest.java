//Author: Bishal Roy

package tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

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

        // CI-friendly flags
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--remote-allow-origins=* ");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // Create a unique user-data-dir to avoid profile-in-use conflicts on the runner
        Path tmpProfile = null;
        try {
            tmpProfile = Files.createTempDirectory("chrome-profile-");
            options.addArguments("--user-data-dir=" + tmpProfile.toAbsolutePath().toString());
        } catch (IOException ignored) {
        }

        driver = new ChromeDriver(options);

        // Register best-effort cleanup for the temp profile
        final Path profileToDelete = tmpProfile;
        if (profileToDelete != null) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Files.walk(profileToDelete)
                         .sorted(Comparator.reverseOrder())
                         .map(Path::toFile)
                         .forEach(File::delete);
                } catch (Exception ignored) {
                }
            }));
        }

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


