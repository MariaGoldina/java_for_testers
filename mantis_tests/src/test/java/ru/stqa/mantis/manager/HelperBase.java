package ru.stqa.mantis.manager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WindowType;

import java.nio.file.Paths;

public class HelperBase {
    protected final ApplicationManager manager;

    public HelperBase(ApplicationManager manager) {
        this.manager = manager;
    }

    protected void openPage(By locator) {
        click(locator);
    }

    public void openStartPage() {
        if (!isElementPresent(By.cssSelector("a.back-to-login-link"))) {
            manager.driver().get(String.format("%slogin.php", manager.property("web.baseUrl")));
        }
    }

    public void openUrlInNewTab(String url) {
        manager.driver().switchTo().newWindow(WindowType.TAB);
        manager.driver().get(url);
    }

    public void switchToTab(int tabIndex) {
        Object[] windowHandles = manager.driver().getWindowHandles().toArray();
        manager.driver().switchTo().window((String) windowHandles[tabIndex]);
    }

    public boolean isElementPresent(By locator) {
        try {
            manager.driver().findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void click(By locator) {
        manager.driver().findElement(locator).click();
    }

    protected void typeText(By locator, String text) {
        click(locator);
        manager.driver().findElement(locator).clear();
        manager.driver().findElement(locator).sendKeys(text);
    }

    protected void attach(By locator, String file) {
        manager.driver().findElement(locator).sendKeys(Paths.get(file).toAbsolutePath().toString());
    }
}
