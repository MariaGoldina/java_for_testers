package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

public class ContactModificationTests extends TestBase {
    @Test
    void canModifyContact() {
        if (app.contacts().getContactCount() == 0) {
            app.contacts().createContact(new ContactData(
                    "", "firstname", "lastname", "middlename",
                    "address", "email", "mobilephone", ""));
        }
        var oldContacts = app.contacts().getContactList();
        var index = new Random().nextInt(oldContacts.size());
        var testData = new ContactData().withFirstName("modified firstname").withLastName("modified lastname");
        app.contacts().modifyContact(oldContacts.get(index), testData);
        var newContacts = app.contacts().getContactList();
        var expectedContacts = new ArrayList<>(oldContacts);
        expectedContacts.set(index, testData.withId(oldContacts.get(index).id()));

        newContacts.sort(app.contacts().compareById);
        expectedContacts.sort(app.contacts().compareById);
        Assertions.assertEquals(newContacts, expectedContacts);
    }

    @Test
    void canModifyContactWithAllFields() {
        if (app.contacts().getContactCount() == 0) {
            app.contacts().createContact(new ContactData(
                    "", "firstname", "lastname", "middlename",
                    "address", "email", "mobilephone", ""));
        }
        var oldContacts = app.contacts().getContactList();
        var index = new Random().nextInt(oldContacts.size());
        var testData = new ContactData()
                .withFirstName("modified firstname")
                .withLastName("modified lastname")
                .withMiddleName("modified middlename")
                .withAddress("modified address")
                .withEmail("modified email")
                .withMobilePhone("modified mobilephone");
        app.contacts().modifyContact(oldContacts.get(index), testData);
        var newContacts = app.contacts().getContactList();
        var expectedContacts = new ArrayList<>(oldContacts);
        expectedContacts.set(index, testData
                .withId(oldContacts.get(index).id())
                .withMiddleName("")
                .withAddress("")
                .withEmail("")
                .withMobilePhone(""));

        newContacts.sort(app.contacts().compareById);
        expectedContacts.sort(app.contacts().compareById);
        Assertions.assertEquals(newContacts, expectedContacts);
    }
}
