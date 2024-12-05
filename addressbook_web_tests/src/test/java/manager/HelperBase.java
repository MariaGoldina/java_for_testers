package manager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

public class HelperBase {
    protected final ApplicationManager manager;

    public HelperBase(ApplicationManager manager) {
        this.manager = manager;
    }

    protected void openPage(By locator) {
        click(locator);
    }

    public boolean isElementPresent(By locator) {
        try {
            manager.driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void click(By locator) {
        manager.driver.findElement(locator).click();
    }

    protected void typeText(By locator, String text) {
        click(locator);
        manager.driver.findElement(locator).clear();
        manager.driver.findElement(locator).sendKeys(text);
    }
}