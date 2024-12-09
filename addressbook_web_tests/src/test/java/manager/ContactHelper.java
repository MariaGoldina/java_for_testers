package manager;

import model.ContactData;
import org.openqa.selenium.By;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public void openHomePage() {
        if (!manager.contacts().isElementPresent(By.name("add"))) {
            openPage(By.linkText("home"));
        }
    }

    public void createContact(ContactData contact) {
        initContactCreation();
        fillContactForm(contact);
        submitContactCreation();
        returnToHomePage();
    }

    public void selectContact(By locator) {
        openHomePage();
        click(locator);
    }

    public void removeContacts() {
        openHomePage();
        initContactRemove();
        openHomePage();
    }

    public void closeDeleteAllert() {
        closeAllert();
    }

    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void initContactRemove() {
        click(By.xpath("//div[2]/input"));
    }

    private void submitContactCreation() {
        click(By.name("submit"));
    }

    private void fillContactForm(ContactData contact) {
        typeText(By.name("firstname"), contact.firstName());
        typeText(By.name("lastname"), contact.lastName());
        typeText(By.name("middlename"), contact.middleName());
        typeText(By.name("address"), contact.address());
        typeText(By.name("email"), contact.email());
        typeText(By.name("mobile"), contact.mobile());
    }

    private void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public int getContactCount() {
        openHomePage();
        return manager.driver.findElements(By.name("selected[]")).size();
    }
}
