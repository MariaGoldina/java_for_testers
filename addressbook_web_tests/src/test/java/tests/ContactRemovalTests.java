package tests;

import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;

import java.util.ArrayList;
import java.util.Random;

@Feature("Contacts")
public class ContactRemovalTests extends TestBase {
    @Story("Remove contact")
    @Test
    public void canRemoveContact() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getContactDBCount() == 0) {
                        app.hbm().createContactInDB(new ContactData(
                                "", "firstname", "lastname", "middlename",
                                "address", "email", "", "mobilephone", "", "", "", "", ""));
                    }
                });
        var oldContacts = app.hbm().getContactsDBList();
        var index = new Random().nextInt(oldContacts.size());
        app.contacts().reloadHomePage();
        app.contacts().selectContact(oldContacts.get(index));
        app.contacts().removeContacts();
        var newContacts = app.hbm().getContactsDBList();
        Allure.step("Validating results", step -> {
            var expectedContacts = new ArrayList<>(oldContacts);
            expectedContacts.remove(index);
            Assertions.assertEquals(newContacts, expectedContacts);
        });
    }

    @Story("Remove several contacts")
    @Test
    public void canRemoveSeveralContacts() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getContactDBCount() < 2) {
                        app.hbm().createContactInDB(new ContactData().withFirstName("1"));
                        app.hbm().createContactInDB(new ContactData().withFirstName("2"));
                    }
                });
        var oldContacts = app.hbm().getContactsDBList();
        app.contacts().reloadHomePage();
        app.contacts().selectContact(oldContacts.get(0));
        app.contacts().selectContact(oldContacts.get(1));
        app.contacts().removeContacts();
        var newContacts = app.hbm().getContactsDBList();
        Allure.step("Validating results", step -> {
            var expectedContacts = new ArrayList<>(oldContacts);
            expectedContacts.remove(0);
            expectedContacts.remove(0);
            Assertions.assertEquals(newContacts, expectedContacts);
        });
    }

    @Story("Can not remove contact without select")
    @Test
    public void cannotRemoveWithNotSelectedContacts() {
        try {
            app.contacts().removeContacts();
            Assertions.fail();
        } catch (UnhandledAlertException e) {
            Allure.step("Contacts without select are not removed");
        }
    }

    @Story("Remove all contacts")
    @Test
    public void canRemoveAllContacts() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getContactDBCount() == 0) {
                        app.hbm().createContactInDB(new ContactData().withFirstName("1"));
                    }
                });
        app.contacts().reloadHomePage();
        app.contacts().selectCheckbox(By.id("MassCB"));
        app.contacts().removeContacts();
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(0, app.hbm().getContactDBCount());
            Assertions.assertEquals(0, app.contacts().getContactCount());
        });
    }
}
