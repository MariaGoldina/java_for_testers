package manager;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase {
    public LoginHelper(ApplicationManager manager) {
        super(manager);
    }

    public void login(String user, String password) {
        typeText(By.name("user"), user);
        typeText(By.name("pass"), password);
        click(By.xpath("//input[@value=\'Login\']"));
    }
}
