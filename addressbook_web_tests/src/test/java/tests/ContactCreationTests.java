package tests;

import common.CommonFunctions;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() {
        var result = new ArrayList<>(List.of(
                new ContactData("", CommonFunctions.randomStringWithNumbers(1000), CommonFunctions.randomStringWithNumbers(1000), CommonFunctions.randomStringWithNumbers(1000),
                        CommonFunctions.randomStringWithNumbers(1000), CommonFunctions.randomStringWithNumbers(1000), CommonFunctions.randomStringWithNumbers(1000),
                        ""),
                new ContactData("", "firstname" + CommonFunctions.randomStringWithNumbers(1000),
                        "lastname" + CommonFunctions.randomStringWithNumbers(1000),
                        "middlename" + CommonFunctions.randomStringWithNumbers(1000),
                        "address" + CommonFunctions.randomStringWithNumbers(1000),
                        "email" + CommonFunctions.randomStringWithNumbers(1000),
                        "mobile" + CommonFunctions.randomStringWithNumbers(1000),
                        ""),
                new ContactData("", "first name.,/-+;:?\"@#!$%^&*()_=",
                        "last name.,/-+;:?\"@#!$%^&*()_=",
                        "middle name.,/-+;:?\"@#!$%^&*()_=",
                        "address .,/-+;:?\"@#!$%^&*()_=",
                        "email .,/-+;:?\"@#!$%^&*()_=",
                        "mobile .,/-+;:?\"@#!$%^&*()_=",
                        ""),
                new ContactData().withFirstName("firstname"),
                new ContactData().withLastName("lastname"),
                new ContactData().withMiddleName("middlename"),
                new ContactData().withAddress("address"),
                new ContactData().withEmail("email"),
                new ContactData().withMobilePhone("mobile")
        ));

        for (var address : List.of("", "address")) {
            for (var email : List.of("", "email")) {
                for (var mobile : List.of("", "mobile")) {
                    result.add(new ContactData()
                            .withFirstName("firstname")
                            .withLastName("lastname")
                            .withMiddleName("middlename")
                            .withAddress(address)
                            .withEmail(email)
                            .withMobilePhone(mobile));
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            result.add(new ContactData()
                    .withFirstName(CommonFunctions.randomString(i * 10))
                    .withLastName(CommonFunctions.randomString(i * 10))
                    .withMiddleName(CommonFunctions.randomString(i * 10))
                    .withAddress(CommonFunctions.randomString(i * 10))
                    .withEmail(CommonFunctions.randomString(i * 10))
                    .withMobilePhone(CommonFunctions.randomString(i * 10)));
        }
        return result;
    }

    public static List<ContactData> negativeContactProvider() {
        var result = new ArrayList<>(List.of(
                new ContactData().withFirstName("firstname'"),
                new ContactData().withLastName("lastname'"),
                new ContactData().withMiddleName("middlename'"),
                new ContactData().withAddress("address'"),
                new ContactData().withEmail("email'"),
                new ContactData().withMobilePhone("mobile'")
        ));
        return result;
    }

    @ParameterizedTest
    @MethodSource("contactProvider")
    public void canCreateContact(ContactData contact) {
        var oldContacts = app.contacts().getContactList();
        app.contacts().createContact(contact);
        var newContacts = app.contacts().getContactList();
        newContacts.sort(app.contacts().compareById);
        var expectedContacts = new ArrayList<>(oldContacts);
        expectedContacts.add(contact.withId(newContacts.get(newContacts.size() - 1).id())
                .withMiddleName("")
                .withAddress("")
                .withEmail("")
                .withMobilePhone(""));
        expectedContacts.sort(app.contacts().compareById);
        Assertions.assertEquals(newContacts, expectedContacts);
    }

    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void cannotCreateContact(ContactData contact) {
        var oldContacts = app.contacts().getContactList();
        app.contacts().createContact(contact);
        var newContacts = app.contacts().getContactList();
        Assertions.assertEquals(newContacts, oldContacts);
    }

    @Test
    public void canCreateContactWithPhoto() {
        var contact = new ContactData()
                .withFirstName("contact1")
                .withLastName("withPhoto")
                .withMiddleName("")
                .withAddress("")
                .withEmail("")
                .withMobilePhone("")
                .withPhoto(CommonFunctions.randomFile("src/test/resources/images"));
        app.contacts().createContact(contact);
    }
}
