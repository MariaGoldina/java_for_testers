package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(ApplicationManager manager) {
        super(manager);
    }

    public Comparator<ContactData> compareById = (o1, o2) -> {
        return Integer.compare(Integer.parseInt(o1.id()), Integer.parseInt(o2.id()));
    };

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

    public void selectContact(ContactData contact) {
        openHomePage();
        click(By.cssSelector(String.format("input[value='%s']", contact.id())));
    }

    public void selectCheckbox(By locator) {
        openHomePage();
        click(locator);
    }

    public void removeContacts() {
        openHomePage();
        initContactRemove();
        openHomePage();
    }

    public void modifyContact(ContactData contact, ContactData modifiedContact) {
        openHomePage();
        selectContact(contact);
        initContactModification(contact);
        fillContactForm(modifiedContact);
        submitContactModification();
        returnToHomePage();
    }

    public void closeDeleteAllert() {
        closeAllert();
    }

    private void initContactCreation() {
        click(By.linkText("add new"));
    }

    private void initContactModification(ContactData contact) {
        click(By.xpath(String.format("//a[@href='edit.php?id=%s']", contact.id())));
    }

    private void initContactRemove() {
        click(By.xpath("//div[2]/input"));
    }

    private void submitContactCreation() {
        click(By.name("submit"));
    }

    private void submitContactModification() {
        click(By.name("update"));
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

    public List<ContactData> getContactList() {
        openHomePage();
        var contacts = new ArrayList<ContactData>();
        var table = manager.driver.findElement(By.xpath("//table[@id='maintable']/tbody"));
        var tableRows = table.findElements(By.xpath("//tr[@name='entry']"));
        for (var tableRow : tableRows) {
            var tableCells = tableRow.findElements(By.tagName("td"));
            var lastName = tableCells.get(1).getText();
            var firstName = tableCells.get(2).getText();
            var address = tableCells.get(3).getText();
            var checkbox = tableCells.get(0).findElement(By.tagName("input"));
            var id = checkbox.getAttribute("id");
            contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName));
        }
        return contacts;
    }
}
