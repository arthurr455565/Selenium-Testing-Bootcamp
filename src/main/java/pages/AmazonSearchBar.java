//Author: Bishal Roy

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AmazonSearchBar {
    private final WebDriver driver;
    private final By searchInput = By.id("twotabsearchtextbox");
    private final By submitBtn = By.id("nav-search-submit-button");

    public AmazonSearchBar(WebDriver driver) { this.driver = driver; }

    public void search(String query) {
        WebElement input = driver.findElement(searchInput);
        input.clear();
        input.sendKeys(query);
        driver.findElement(submitBtn).click();
    }
}