package tests;

import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContactModificationTests extends TestBase {
    private List<ContactData> contactsNotInGroup(List<ContactData> list, GroupData group) {
        var newContactsList = new ArrayList<>(list);
        var relatedContacts = app.hbm().getContactsInGroup(group);
        for (var contactInGroup : relatedContacts) {
            for (var contact : list) {
                if (contactInGroup.id().equals(contact.id())) {
                    newContactsList.remove(contact);
                }
            }
        }
        return newContactsList;
    }

    private void checkNotEmptyGroup(GroupData group) {
        var allContacts = app.hbm().getContactsDBList();
        var relatedContacts = app.hbm().getContactsInGroup(group);
        if (relatedContacts.isEmpty()) {
            app.contacts().reloadHomePage();
            app.contacts().selectAllGroupsFrom();
            app.contacts().addContactToGroup(allContacts.get(0), group);
        }
    }

    @Test
    void canModifyContact() {
        if (app.hbm().getContactDBCount() == 0) {
            app.hbm().createContactInDB(new ContactData(
                    "", "firstname", "lastname", "middlename",
                    "address", "email", "mobilephone", ""));
        }
        var oldContacts = app.hbm().getContactsDBList();
        var index = new Random().nextInt(oldContacts.size());
        var testData = new ContactData().withFirstName("modified firstname").withLastName("modified lastname");
        app.contacts().reloadHomePage();
        app.contacts().modifyContact(oldContacts.get(index), testData);
        var newContacts = app.hbm().getContactsDBList();
        var expectedContacts = new ArrayList<>(oldContacts);
        expectedContacts.set(index, testData.withId(oldContacts.get(index).id()));

        newContacts.sort(app.contacts().compareById);
        expectedContacts.sort(app.contacts().compareById);
        Assertions.assertEquals(newContacts, expectedContacts);
    }

    @Test
    void canModifyContactWithAllFields() {
        if (app.hbm().getContactDBCount() == 0) {
            app.hbm().createContactInDB(new ContactData(
                    "", "firstname", "lastname", "middlename",
                    "address", "email", "mobilephone", ""));
        }
        var oldContacts = app.hbm().getContactsDBList();
        var index = new Random().nextInt(oldContacts.size());
        var testData = new ContactData()
                .withFirstName("modified firstname")
                .withLastName("modified lastname")
                .withMiddleName("modified middlename")
                .withAddress("modified address")
                .withEmail("modified email")
                .withMobilePhone("modified mobilephone");
        app.contacts().reloadHomePage();
        app.contacts().modifyContact(oldContacts.get(index), testData);
        var newContacts = app.hbm().getContactsDBList();
        var expectedContacts = new ArrayList<>(oldContacts);
        expectedContacts.set(index, testData.withId(oldContacts.get(index).id()));

        newContacts.sort(app.contacts().compareById);
        expectedContacts.sort(app.contacts().compareById);
        Assertions.assertEquals(newContacts, expectedContacts);
    }

    @Test
    public void canAddContactToGroup() {
        if (app.hbm().getContactDBCount() == 0) {
            app.hbm().createContactInDB(new ContactData(
                    "", "firstname", "lastname", "middlename",
                    "address", "email", "mobilephone", ""));
        }
        if (app.hbm().getGroupsDBCount() == 0) {
            app.hbm().createGroupInDB(new GroupData("", "new group", "group header", "group footer"));
        }
        var group = app.hbm().getGroupsDBList().get(0);
        ContactData addedContact;

        var oldContacts = app.hbm().getContactsDBList();
        var oldContactsNotInGroup = contactsNotInGroup(oldContacts, group);

        if (oldContactsNotInGroup.isEmpty()) {
            app.hbm().createContactInDB(new ContactData(
                    "", "newcontact", "forgroup", "",
                    "", "", "", ""));
            var listContacts = app.hbm().getContactsDBList();
            listContacts.sort(app.contacts().compareById);
            addedContact = listContacts.get(listContacts.size() - 1);
        } else {
            var index = new Random().nextInt(oldContactsNotInGroup.size());
            addedContact = oldContactsNotInGroup.get(index);
        }

        oldContacts = app.hbm().getContactsDBList();
        oldContacts.sort(app.contacts().compareById);
        var oldRelated = app.hbm().getContactsInGroup(group);
        app.contacts().reloadHomePage();
        app.contacts().selectAllGroupsFrom();
        app.contacts().addContactToGroup(addedContact, group);

        var newContacts = app.hbm().getContactsDBList();
        newContacts.sort(app.contacts().compareById);
        var newRelated = app.hbm().getContactsInGroup(group);
        newRelated.sort(app.contacts().compareById);
        var expectedRelated = new ArrayList<>(oldRelated);
        expectedRelated.add(addedContact);
        expectedRelated.sort(app.contacts().compareById);

        Assertions.assertEquals(oldContacts, newContacts);
        Assertions.assertEquals(expectedRelated, newRelated);
    }

    @Test
    public void canRemoveContactFromGroup() {
        if (app.hbm().getContactDBCount() == 0) {
            app.hbm().createContactInDB(new ContactData(
                    "", "firstname", "lastname", "middlename",
                    "address", "email", "mobilephone", ""));
        }
        if (app.hbm().getGroupsDBCount() == 0) {
            app.hbm().createGroupInDB(new GroupData("", "new group", "group header", "group footer"));
        }
        var group = app.hbm().getGroupsDBList().get(0);

        var oldContacts = app.hbm().getContactsDBList();
        oldContacts.sort(app.contacts().compareById);
        checkNotEmptyGroup(group);
        var oldRelated = app.hbm().getContactsInGroup(group);
        oldRelated.sort(app.contacts().compareById);
        var index = new Random().nextInt(oldRelated.size());
        app.contacts().selectGroupFrom(group);
        app.contacts().removeContactFromGroup(oldRelated.get(index));

        var newContacts = app.hbm().getContactsDBList();
        newContacts.sort(app.contacts().compareById);
        var newRelated = app.hbm().getContactsInGroup(group);
        newRelated.sort(app.contacts().compareById);
        var expectedRelated = new ArrayList<>(oldRelated);
        expectedRelated.remove(oldRelated.get(index));
        expectedRelated.sort(app.contacts().compareById);

        Assertions.assertEquals(oldContacts, newContacts);
        Assertions.assertEquals(expectedRelated, newRelated);
    }
}
