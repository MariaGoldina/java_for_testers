package tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import common.CommonFunctions;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.ContactData;
import model.GroupData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Feature("Contacts")
public class ContactCreationTests extends TestBase {

    public static List<ContactData> contactProvider() throws IOException {
        var result = new ArrayList<>(List.of(
                new ContactData("", CommonFunctions.randomStringWithNumbers(1000), CommonFunctions.randomStringWithNumbers(1000), CommonFunctions.randomStringWithNumbers(1000),
                        CommonFunctions.randomStringWithNumbers(1000), CommonFunctions.randomStringWithNumbers(1000), "", CommonFunctions.randomStringWithNumbers(1000),
                        "", "", "", "", "")
//                ,
//                new ContactData("", "firstname" + CommonFunctions.randomStringWithNumbers(1000),
//                        "lastname" + CommonFunctions.randomStringWithNumbers(1000),
//                        "middlename" + CommonFunctions.randomStringWithNumbers(1000),
//                        "address" + CommonFunctions.randomStringWithNumbers(1000),
//                        "email" + CommonFunctions.randomStringWithNumbers(1000),
//                        "mobile" + CommonFunctions.randomStringWithNumbers(1000),
//                        ""),
//                new ContactData("", "first name.,/-+;:?\"@#!$%^&*()_=",
//                        "last name.,/-+;:?\"@#!$%^&*()_=",
//                        "middle name.,/-+;:?\"@#!$%^&*()_=",
//                        "address .,/-+;:?\"@#!$%^&*()_=",
//                        "email .,/-+;:?\"@#!$%^&*()_=",
//                        "mobile .,/-+;:?\"@#!$%^&*()_=",
//                        ""),
//                new ContactData().withFirstName("firstname"),
//                new ContactData().withLastName("lastname"),
//                new ContactData().withMiddleName("middlename"),
//                new ContactData().withAddress("address"),
//                new ContactData().withEmail("email"),
//                new ContactData().withMobilePhone("mobile")
        ));

//        for (var address : List.of("", "address")) {
//            for (var email : List.of("", "email")) {
//                for (var mobile : List.of("", "mobile")) {
//                    result.add(new ContactData()
//                            .withFirstName("firstname")
//                            .withLastName("lastname")
//                            .withMiddleName("middlename")
//                            .withAddress(address)
//                            .withEmail(email)
//                            .withMobilePhone(mobile));
//                }
//            }
//        }

//        var json = "";
//        try (var reader = new FileReader("contacts.json");
//             var breader = new BufferedReader(reader)
//        ) {
//            var line = breader.readLine();
//            while (line != null) {
//                json = json + line;
//                line = breader.readLine();
//            }
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        var value = mapper.readValue(json, new TypeReference<List<ContactData>>() {
//        });
//        var mapper = new YAMLMapper();
//        var value = mapper.readValue(new File("contacts.yaml"), new TypeReference<List<ContactData>>() {
//        });
        var mapper = new XmlMapper();
        var value = mapper.readValue(new File("contacts.xml"), new TypeReference<List<ContactData>>() {
        });
        result.addAll(value);
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

    public static List<ContactData> singleContactProvider() {
        return List.of(
                new ContactData()
                        .withFirstName(CommonFunctions.randomString(10))
                        .withLastName(CommonFunctions.randomString(10))
                        .withMiddleName(CommonFunctions.randomString(10))
                        .withAddress(CommonFunctions.randomString(10))
                        .withEmail(CommonFunctions.randomString(5) + "@mail.com")
                        .withMobilePhone("7" + CommonFunctions.randomStringWithNumbers(1000000000)));
    }

    public static Stream<ContactData> randomContactProvider() {
        Supplier<ContactData> randomContact = () -> new ContactData()
                .withFirstName(CommonFunctions.randomString(10))
                .withLastName(CommonFunctions.randomString(10))
                .withMiddleName(CommonFunctions.randomString(10))
                .withAddress(CommonFunctions.randomString(10))
                .withEmail(CommonFunctions.randomString(5) + "@mail.com")
                .withMobilePhone("7" + CommonFunctions.randomStringWithNumbers(1000000000));
        return Stream.generate(randomContact).limit(3);
    }

    @Story("Create contact")
    @ParameterizedTest
    @MethodSource("randomContactProvider")
    public void canCreateContact(ContactData contact) {
        Allure.parameter("contact", contact);
        var oldContacts = app.hbm().getContactsDBList();
        app.contacts().createContact(contact);
        var newContacts = app.hbm().getContactsDBList();
        newContacts.sort(app.contacts().compareById);
        Allure.step("Validating results in DB", step -> {
                    var expectedContacts = new ArrayList<>(oldContacts);
                    expectedContacts.add(contact.withId(newContacts.get(newContacts.size() - 1).id()));
                    expectedContacts.sort(app.contacts().compareById);
                    Assertions.assertEquals(newContacts, expectedContacts);
                });

        Allure.step("Validating results in UI", step -> {
            var newUIContacts = app.contacts().getContactList();
            newUIContacts.sort(app.contacts().compareById);
            var expectedContactsFromDB = new ArrayList<>();
            for (var newContact : newContacts) {
                expectedContactsFromDB.add(newContact
                        .withMiddleName("")
                        .withAddress("")
                        .withEmail("")
                        .withEmail2("")
                        .withEmail3("")
                        .withMobilePhone("")
                        .withHomePhone("")
                        .withWorkPhone("")
                        .withPhoto(""));
            }
            Assertions.assertEquals(newUIContacts, expectedContactsFromDB);
        });
    }

    @Story("Can not create uncorrect contact, negative")
    @ParameterizedTest
    @MethodSource("negativeContactProvider")
    public void cannotCreateContact(ContactData contact) {
        Allure.parameter("uncorrect contact", contact);
        var oldContacts = app.hbm().getContactsDBList();
        app.contacts().createContact(contact);
        var newContacts = app.hbm().getContactsDBList();
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(newContacts, oldContacts);
        });
    }

    @Story("Create contact with photo")
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
        var oldContacts = app.hbm().getContactDBCount();
        app.contacts().createContact(contact);
        var newContacts = app.hbm().getContactDBCount();
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(oldContacts + 1, newContacts);
        });
    }

    @Story("Create contact in group")
    @Test
    public void canCreateContactInGroup() {
        var contact = new ContactData()
                .withFirstName("ingroup")
                .withLastName("contact")
                .withMiddleName("")
                .withAddress("")
                .withEmail("")
                .withMobilePhone("");
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getGroupsDBCount() == 0) {
                        app.hbm().createGroupInDB(new GroupData("", "new group", "group header", "group footer"));
                    }
                });
        var group = app.hbm().getGroupsDBList().get(0);

        var oldContacts = app.hbm().getContactDBCount();
        var oldRelated = app.hbm().getContactsInGroup(group);
        oldRelated.sort(app.contacts().compareById);
        app.contacts().createContact(contact, group);
        var newContacts = app.hbm().getContactDBCount();
        var newRelated = app.hbm().getContactsInGroup(group);
        newRelated.sort(app.contacts().compareById);
        Allure.step("Validating results", step -> {
            var expectedRelated = new ArrayList<>(oldRelated);
            expectedRelated.add(contact.withId(newRelated.get(newRelated.size() - 1).id()));
            expectedRelated.sort(app.contacts().compareById);
            Assertions.assertEquals(oldContacts + 1, newContacts);
            Assertions.assertEquals(expectedRelated, newRelated);
        });
    }
}
