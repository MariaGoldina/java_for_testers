package tests;

import model.ContactData;
import org.junit.jupiter.api.Test;

public class ContactCreationTests extends TestBase {

    @Test
    public void canCreateEmptyContact() {
        app.contacts().createContact(new ContactData());
    }

    @Test
    public void canCreateContactWithFirstNameOnly() {
        app.contacts().createContact(new ContactData().withFirstName("firstname"));
    }

    @Test
    public void canCreateContactWithLastNameOnly() {
        app.contacts().createContact(new ContactData().withLastName("lastname"));
    }

    @Test
    public void canCreateContactWithMiddleNameOnly() {
        app.contacts().createContact(new ContactData().withMiddleName("middlename"));
    }

    @Test
    public void canCreateContactWithAllGeneralFields() {
        app.contacts().createContact(new ContactData(
                "firstname", "lastname", "middlename", "address",
                "email", "mobilephone"));
    }

    @Test
    public void canCreateContactAllFieldsWithNumbers() {
        app.contacts().createContact(new ContactData(
                "1234567890", "1234567890", "1234567890", "1234567890",
                "abc1234567890", "1234567890"));
    }

    @Test
    public void canCreateContactAllFieldsWithSymbols() {
        app.contacts().createContact(new ContactData(
                "first - name", "last - name", "middle - name", "abc.,/-",
                "._-@.", "+ - ()"));
    }
}
