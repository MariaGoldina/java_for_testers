package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase {
    public SessionHelper(ApplicationManager manager) {
        super(manager);
    }

    public void login(String user, String password) {
        openStartPage();
        typeText(By.name("username"), user);
        click(By.cssSelector("input[type='submit']"));
        typeText(By.name("password"), password);
        click(By.cssSelector("input[type='submit']"));
    }

    public boolean isLoggedIn() {
        return isElementPresent(By.cssSelector("span.user-info"));
    }
}
