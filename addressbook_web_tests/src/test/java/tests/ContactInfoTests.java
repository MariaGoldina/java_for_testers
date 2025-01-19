package tests;

import common.CommonFunctions;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Feature("Contacts")
public class ContactInfoTests extends TestBase {
    private static HashMap<String, String> getContactInfo(ContactData contact) {
        var contactAddress = contact.address();
        var contactEmails = Stream.of(contact.email(), contact.email2(), contact.email3())
                .filter(s -> s!=null && !s.isEmpty())
                .collect(Collectors.joining("\n"));
        var contactPhones = Stream.of(contact.home(), contact.mobile(), contact.work())
                .filter(s -> s!=null && !s.isEmpty())
                .collect(Collectors.joining("\n"));
        var contactInfo = new HashMap<String, String>();
        contactInfo.put("address", contactAddress);
        contactInfo.put("emails", contactEmails);
        contactInfo.put("phones", contactPhones);
        return contactInfo;
    }

    @Story("Check contact phones on home page")
    @Test
    public void testContactPhonesOnHomePage() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getContactDBCount() == 0) {
                        app.hbm().createContactInDB(new ContactData(
                                "", "firstname", "lastname", "",
                                CommonFunctions.randomString(10), CommonFunctions.randomString(10) + "1",
                                CommonFunctions.randomStringWithNumbers(10000),
                                CommonFunctions.randomStringWithNumbers(10000),
                                CommonFunctions.randomStringWithNumbers(10000),
                                "", "", CommonFunctions.randomString(10) + "2",
                                CommonFunctions.randomString(10) + "3"));
                    }
                });
        var contacts = app.hbm().getContactsDBList();
        var index = new Random().nextInt(contacts.size());
        var contact = contacts.get(index);
        app.contacts().reloadHomePage();
        var phones = app.contacts().getPhones(contact);
        Allure.step("Validating results", step -> {
            var expectedPhones = Stream.of(contact.home(), contact.mobile(), contact.work())
                    .filter(s -> s != null && !s.isEmpty())
                    .collect(Collectors.joining("\n"));
            Assertions.assertEquals(expectedPhones, phones);
        });
    }

    @Story("Check all phones on home page")
    @Test
    public void testAllPhonesOnHomePage() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getContactDBCount() < 2) {
                        app.hbm().createContactInDB(new ContactData(
                                "", "", "contact1", "",
                                "", "", CommonFunctions.randomStringWithNumbers(10000),
                                CommonFunctions.randomStringWithNumbers(10000),
                                CommonFunctions.randomStringWithNumbers(10000),
                                "", "", "", ""));
                        app.hbm().createContactInDB(new ContactData(
                                "", "", "contact2", "",
                                "", "", CommonFunctions.randomStringWithNumbers(10000),
                                CommonFunctions.randomStringWithNumbers(10000),
                                CommonFunctions.randomStringWithNumbers(10000),
                                "", "", "", ""));
                    }
                });
        var contacts = app.hbm().getContactsDBList();
        var expectedPhones = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
                Stream.of(contact.home(), contact.mobile(), contact.work())
                .filter(s -> s!=null && !s.isEmpty())
                .collect(Collectors.joining("\n"))));
        app.contacts().reloadHomePage();
        var allPhones = app.contacts().getAllPhones();
        Allure.step("Validating results", step -> {
            Assertions.assertEquals(expectedPhones, allPhones);
        });
    }

    @Story("Check contact phones on edit page")
    @Test
    public void testContactPhonesOnEditPage() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getContactDBCount() == 0) {
                        app.hbm().createContactInDB(new ContactData(
                                "", "firstname", "lastname", "",
                                "", "", CommonFunctions.randomStringWithNumbers(10000),
                                CommonFunctions.randomStringWithNumbers(10000),
                                CommonFunctions.randomStringWithNumbers(10000),
                                "", "", "", ""));
                    }
                });
        var contacts = app.hbm().getContactsDBList();
        var index = new Random().nextInt(contacts.size());
        var contact = contacts.get(index);
        app.contacts().reloadHomePage();
        var phones = app.contacts().getPhones(contact);
        var phonesOnEditPage = app.contacts().getPhonesOnEditPage(contact);
        Allure.step("Validating results", step -> {
            var expectedPhones = Stream.of(contact.home(), contact.mobile(), contact.work())
                    .filter(s -> s != null && !s.isEmpty())
                    .collect(Collectors.joining("\n"));
            Assertions.assertEquals(expectedPhones, phonesOnEditPage);
            Assertions.assertEquals(phones, phonesOnEditPage);
        });
    }

    @Story("Check contact info on home page and edit page")
    @Test
    public void testContactInfo() {
        Allure.step("Creating preconditions", step -> {
                    if (app.hbm().getContactDBCount() == 0) {
                        app.hbm().createContactInDB(new ContactData(
                                "", "firstname", "lastname", "",
                                CommonFunctions.randomString(10) + "., -/" + CommonFunctions.randomString(20),
                                CommonFunctions.randomString(10) + "@mail1.com",
                                CommonFunctions.randomStringWithNumbers(10000),
                                CommonFunctions.randomStringWithNumbers(10000),
                                CommonFunctions.randomStringWithNumbers(10000),
                                "", "",
                                CommonFunctions.randomString(10) + "@mail2.ru",
                                CommonFunctions.randomString(10) + "@mail3.kz"));
                    }
                });
        var contacts = app.hbm().getContactsDBList();
        var index = new Random().nextInt(contacts.size());
        var contact = contacts.get(index);
        app.contacts().reloadHomePage();
        var contactInfo = app.contacts().getInfo(contact);
        var contactInfoOnEditPage = app.contacts().getInfoOnEditPage(contact);

        var expectedInfo = getContactInfo(contact);
        Allure.step("Validating results from home page", step -> {
                    Assertions.assertEquals(expectedInfo, contactInfo);
                });
        Allure.step("Validating results from edit page", step -> {
            Assertions.assertEquals(expectedInfo, contactInfoOnEditPage);
            Assertions.assertEquals(contactInfo, contactInfoOnEditPage);
        });
    }
}
