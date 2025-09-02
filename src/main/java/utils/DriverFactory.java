package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

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

                // Create a unique user-data-dir to avoid profile-in-use conflicts on the runner
                try {
                    Path tmpProfile = Files.createTempDirectory("chrome-profile-");
                    ch.addArguments("--user-data-dir=" + tmpProfile.toAbsolutePath().toString());
                    // Best-effort cleanup on JVM exit
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        try {
                            Files.walk(tmpProfile)
                                 .sorted(Comparator.reverseOrder())
                                 .map(Path::toFile)
                                 .forEach(File::delete);
                        } catch (Exception ignored) {
                        }
                    }));
                } catch (IOException ignored) {
                    // If tmp dir creation fails, continue without setting user-data-dir
                }

                return new ChromeDriver(ch);
        }
    }
}


