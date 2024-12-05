package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (!app.contacts().isContactPresent(By.name("selected[]"))) {
            app.contacts().createContact(new ContactData(
                    "firstname", "lastname", "middlename",
                    "address", "email", "mobilephone"));
        }
        app.contacts().selectContact(By.name("selected[]"));
        app.contacts().removeContacts();
    }

    @Test
    public void canRemoveSeveralContacts() {
        if (!app.contacts().isContactPresent(By.name("selected[]"))) {
            app.contacts().createContact(new ContactData().withFirstName("1"));
        }
        if (!app.contacts().isContactPresent(By.xpath("(//input[@name=\'selected[]\'])[2]"))) {
            app.contacts().createContact(new ContactData().withFirstName("2"));
        }
        app.contacts().selectContact(By.name("selected[]"));
        app.contacts().selectContact(By.xpath("(//input[@name=\'selected[]\'])[2]"));
        app.contacts().removeContacts();
    }

    @Test
    public void cannotRemoveWithNotSelectedContacts() {
        try {
            app.contacts().removeContacts();
            Assertions.fail();
        } catch (UnhandledAlertException e) {
//            OK
        }
    }

    @Test
    public void canRemoveAllContacts() {
        if (!app.contacts().isContactPresent(By.name("selected[]"))) {
            app.contacts().createContact(new ContactData().withFirstName("1"));
        }
        app.contacts().selectContact(By.id("MassCB"));
        app.contacts().removeContacts();
    }
}
