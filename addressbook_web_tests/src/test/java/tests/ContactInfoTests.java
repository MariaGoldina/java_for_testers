package tests;

import common.CommonFunctions;
import model.ContactData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    public void testContactPhonesOnHomePage() {
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
        var contacts = app.hbm().getContactsDBList();
        var index = new Random().nextInt(contacts.size());
        var contact = contacts.get(index);
        app.contacts().reloadHomePage();
        var phones = app.contacts().getPhones(contact);
        var expectedPhones = Stream.of(contact.home(), contact.mobile(), contact.work())
                .filter(s -> s!=null && !s.isEmpty())
                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expectedPhones, phones);
    }

    @Test
    public void testAllPhonesOnHomePage() {
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
        var contacts = app.hbm().getContactsDBList();
        var expectedPhones = contacts.stream().collect(Collectors.toMap(ContactData::id, contact ->
                Stream.of(contact.home(), contact.mobile(), contact.work())
                .filter(s -> s!=null && !s.isEmpty())
                .collect(Collectors.joining("\n"))));
        app.contacts().reloadHomePage();
        var allPhones = app.contacts().getAllPhones();
        Assertions.assertEquals(expectedPhones, allPhones);
    }

    @Test
    public void testContactPhonesOnEditPage() {
        if (app.hbm().getContactDBCount() == 0) {
            app.hbm().createContactInDB(new ContactData(
                    "", "firstname", "lastname", "",
                    "", "", CommonFunctions.randomStringWithNumbers(10000),
                    CommonFunctions.randomStringWithNumbers(10000),
                    CommonFunctions.randomStringWithNumbers(10000),
                    "", "", "", ""));
        }
        var contacts = app.hbm().getContactsDBList();
        var index = new Random().nextInt(contacts.size());
        var contact = contacts.get(index);
        app.contacts().reloadHomePage();
        var phones = app.contacts().getPhones(contact);
        var phonesOnEditPage = app.contacts().getPhonesOnEditPage(contact);
        var expectedPhones = Stream.of(contact.home(), contact.mobile(), contact.work())
                .filter(s -> s!=null && !s.isEmpty())
                .collect(Collectors.joining("\n"));
        Assertions.assertEquals(expectedPhones, phonesOnEditPage);
        Assertions.assertEquals(phones, phonesOnEditPage);
    }

    @Test
    public void testContactInfo() {
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
        var contacts = app.hbm().getContactsDBList();
        var index = new Random().nextInt(contacts.size());
        var contact = contacts.get(index);
        app.contacts().reloadHomePage();
        var contactInfo = app.contacts().getInfo(contact);
        var contactInfoOnEditPage = app.contacts().getInfoOnEditPage(contact);
        var expectedInfo = getContactInfo(contact);
        Assertions.assertEquals(expectedInfo, contactInfo);
        Assertions.assertEquals(expectedInfo, contactInfoOnEditPage);
        Assertions.assertEquals(contactInfo, contactInfoOnEditPage);
    }



}
