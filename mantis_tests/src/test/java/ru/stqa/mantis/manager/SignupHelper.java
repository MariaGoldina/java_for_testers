package ru.stqa.mantis.manager;

import org.openqa.selenium.By;
import ru.stqa.mantis.model.UserData;

public class SignupHelper extends HelperBase {
    public SignupHelper(ApplicationManager manager) {
        super(manager);
    }

    public void signUp(UserData user) {
        openStartPage();
        click(By.cssSelector("a.back-to-login-link"));
        typeText(By.name("username"), user.username());
        typeText(By.name("email"), user.email());
        click(By.cssSelector("input[type='submit']"));
        if (!isElementPresent(By.xpath("//strong[text()='Account registration processed.']"))) {
            throw new RuntimeException("SignUp was failed");
        }
    }

    public String getUserInfoOnSignupPage() {
        String userInfo = "";
        if (isElementPresent(By.xpath("//strong[text()='Account registration processed.']"))) {
            userInfo = manager.driver()
                    .findElement(By.xpath("//div[@class='center']"))
                    .getText()
                    .replace("Account registration processed.\n" +
                            "[", "")
                    .replace("]", "");
        } else {
            throw new RuntimeException("Open unexpected page");
        }
        if (!userInfo.isEmpty()) {
            return userInfo;
        } else {
            throw new RuntimeException("User email not found on page");
        }
    }

    public void confirmRegistrationFromLink(String linkUrl, UserData user) {
        openUrlInNewTab(linkUrl);
        if (isElementPresent(By.id("reset-passwd-msg"))) {
            typeText(By.name("realname"), user.name());
            typeText(By.name("password"), user.password());
            typeText(By.name("password_confirm"), user.password());
            click(By.cssSelector("button[type='submit']"));
            switchToTab(0);
        } else {
            throw new RuntimeException("Confirm registration from mail link was failed");
        }
    }
}
