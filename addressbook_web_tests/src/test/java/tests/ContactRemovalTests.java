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
        if (app.hbm().getContactDBCount() == 0) {
            app.hbm().createContactInDB(new ContactData(
                    "", "firstname", "lastname", "middlename",
                    "address", "email", "", "mobilephone", "", "", "", "", ""));
        }
        var oldContacts = app.hbm().getContactsDBList();
        var index = new Random().nextInt(oldContacts.size());
        app.contacts().reloadHomePage();
        app.contacts().selectContact(oldContacts.get(index));
        app.contacts().removeContacts();
        var newContacts = app.hbm().getContactsDBList();
        var expectedContacts = new ArrayList<>(oldContacts);
        expectedContacts.remove(index);
        Assertions.assertEquals(newContacts, expectedContacts);
    }

    @Test
    public void canRemoveSeveralContacts() {
        if (app.hbm().getContactDBCount() < 2) {
            app.hbm().createContactInDB(new ContactData().withFirstName("1"));
            app.hbm().createContactInDB(new ContactData().withFirstName("2"));
        }
        var oldContacts = app.hbm().getContactsDBList();
        app.contacts().reloadHomePage();
        app.contacts().selectContact(oldContacts.get(0));
        app.contacts().selectContact(oldContacts.get(1));
        app.contacts().removeContacts();
        var newContacts = app.hbm().getContactsDBList();
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
        if (app.hbm().getContactDBCount() == 0) {
            app.hbm().createContactInDB(new ContactData().withFirstName("1"));
        }
        app.contacts().reloadHomePage();
        app.contacts().selectCheckbox(By.id("MassCB"));
        app.contacts().removeContacts();
        Assertions.assertEquals(0, app.hbm().getContactDBCount());
        Assertions.assertEquals(0, app.contacts().getContactCount());
    }
}
