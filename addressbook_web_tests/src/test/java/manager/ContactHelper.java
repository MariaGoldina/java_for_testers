package manager;

import model.ContactData;
import model.GroupData;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

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

    public void reloadHomePage() {
        openPage(By.linkText("home"));
    }

    public void createContact(ContactData contact) {
        initContactCreation();
        if (!contact.photo().isEmpty()) {
            fillContactFormWithPhoto(contact);
        } else {
            fillContactForm(contact);
        }
        submitContactCreation();
        returnToHomePage();
    }

    public void createContact(ContactData contact, GroupData group) {
        initContactCreation();
        if (!contact.photo().isEmpty()) {
            fillContactFormWithPhoto(contact);
        } else {
            fillContactForm(contact);
        }
        selectGroup(group);
        submitContactCreation();
        returnToHomePage();
    }

    private void selectGroup(GroupData group) {
        new Select(manager.driver.findElement(By.name("new_group"))).selectByValue(group.id());
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

    public void addContactToGroup(ContactData contact, GroupData group) {
        openHomePage();
        selectContact(contact);
        selectGroupTo(group);
        click(By.name("add"));
    }

    public void removeContactFromGroup(ContactData contact) {
        selectContact(contact);
        click(By.name("remove"));
    }

    public void selectAllGroupsFrom() {
        openHomePage();
        new Select(manager.driver.findElement(By.name("group"))).selectByVisibleText("[all]");
    }

    public void selectGroupFrom(GroupData group) {
        openHomePage();
        new Select(manager.driver.findElement(By.name("group"))).selectByValue(group.id());
    }

    private void selectGroupTo(GroupData group) {
        new Select(manager.driver.findElement(By.name("to_group"))).selectByValue(group.id());
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

    private void fillContactFormWithPhoto(ContactData contact) {
        typeText(By.name("firstname"), contact.firstName());
        typeText(By.name("lastname"), contact.lastName());
        typeText(By.name("middlename"), contact.middleName());
        typeText(By.name("address"), contact.address());
        typeText(By.name("email"), contact.email());
        typeText(By.name("mobile"), contact.mobile());
        attach(By.name("photo"), contact.photo());
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
