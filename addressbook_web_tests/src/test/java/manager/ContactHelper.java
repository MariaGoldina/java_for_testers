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

//    public List<ContactData> getContactList() {
//        openHomePage();
//        var contacts = new ArrayList<ContactData>();
//        var tableLines = manager.driver.findElements(By.xpath("//table[@id='maintable']/tbody/tr"));
//        tableLines.remove(tableLines.get(0));
//        for (var tableLine : tableLines) {
//            var lastName = tableLine.findElement(By.xpath(".1")).getText();
//            var firstName = tableLine.findElement(By.xpath(".2")).getText();
//            var address = tableLine.findElement(By.xpath(".3")).getText();
//            var checkbox = tableLine.findElement(By.xpath(".0/input[@name='selected[]']"));
//            var id = checkbox.getAttribute("id");
//            contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName).withAddress(address));
//        }
//        return contacts;
//    }
}
