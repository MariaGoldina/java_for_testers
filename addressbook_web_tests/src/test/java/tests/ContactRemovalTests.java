package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;

import java.util.ArrayList;
import java.util.Random;

public class ContactRemovalTests extends TestBase {

    @Test
    public void canRemoveContact() {
        if (app.contacts().getContactCount() == 0) {
            app.contacts().createContact(new ContactData(
                    "", "firstname", "lastname", "middlename",
                    "address", "email", "mobilephone"));
        }
        var oldContacts = app.contacts().getContactList();
        var index = new Random().nextInt(oldContacts.size());
        app.contacts().selectContact(oldContacts.get(index));
        app.contacts().removeContacts();
        var newContacts = app.contacts().getContactList();
        var expectedContacts = new ArrayList<>(oldContacts);
        expectedContacts.remove(index);
        Assertions.assertEquals(newContacts, expectedContacts);
    }

    @Test
    public void canRemoveSeveralContacts() {
        if (app.contacts().getContactCount() < 2) {
            app.contacts().createContact(new ContactData().withFirstName("1"));
            app.contacts().createContact(new ContactData().withFirstName("2"));
        }
        var oldContacts = app.contacts().getContactList();
        app.contacts().selectContact(oldContacts.get(0));
        app.contacts().selectContact(oldContacts.get(1));
        app.contacts().removeContacts();
        var newContacts = app.contacts().getContactList();
        var expectedContacts = new ArrayList<>(oldContacts);
        expectedContacts.remove(0);
        expectedContacts.remove(0);
        Assertions.assertEquals(newContacts, expectedContacts);
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
        if (app.contacts().getContactCount() == 0) {
            app.contacts().createContact(new ContactData().withFirstName("1"));
        }
        app.contacts().selectCheckbox(By.id("MassCB"));
        app.contacts().removeContacts();
        Assertions.assertEquals(0, app.contacts().getContactCount());
    }
}
