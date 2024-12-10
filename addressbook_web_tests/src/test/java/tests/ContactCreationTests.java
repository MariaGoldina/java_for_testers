package tests;

import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;

public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() {
        var result = new ArrayList<>(List.of(
                new ContactData(randomStringWithNumbers(1000), randomStringWithNumbers(1000), randomStringWithNumbers(1000),
                        randomStringWithNumbers(1000), randomStringWithNumbers(1000), randomStringWithNumbers(1000)
                ),
                new ContactData("firstname" + randomStringWithNumbers(1000),
                        "lastname" + randomStringWithNumbers(1000),
                        "middlename" + randomStringWithNumbers(1000),
                        "address" + randomStringWithNumbers(1000),
                        "email" + randomStringWithNumbers(1000),
                        "mobile" + randomStringWithNumbers(1000)
                ),
                new ContactData("first name.,/-+;:?\"@#!$%^&*()_=",
                        "last name.,/-+;:?\"@#!$%^&*()_=",
                        "middle name.,/-+;:?\"@#!$%^&*()_=",
                        "address .,/-+;:?\"@#!$%^&*()_=",
                        "email .,/-+;:?\"@#!$%^&*()_=",
                        "mobile .,/-+;:?\"@#!$%^&*()_="
                ),
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
                    result.add(new ContactData("firstname", "lastname", "middlename",
                            address, email, mobile));
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            result.add(new ContactData(randomString(i * 10), randomString(i * 10), randomString(i * 10),
                    randomString(i * 10), randomString(i * 10), randomString(i * 10)));
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
        int contactCount = app.contacts().getContactCount();
        app.contacts().createContact(contact);
        int newContactCount = app.contacts().getContactCount();
        Assertions.assertEquals(contactCount + 1, newContactCount);
    }

    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void cannotCreateContact(ContactData contact) {
        int contactCount = app.contacts().getContactCount();
        app.contacts().createContact(contact);
        int newContactCount = app.contacts().getContactCount();
        Assertions.assertEquals(contactCount, newContactCount);
    }
}
