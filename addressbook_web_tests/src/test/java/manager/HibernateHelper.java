package manager;

import io.qameta.allure.Step;
import manager.hbm.ContactRecord;
import manager.hbm.GroupRecord;
import model.ContactData;
import model.GroupData;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.stream.Collectors;

public class HibernateHelper extends HelperBase {
    private SessionFactory sessionFactory;

    public HibernateHelper(ApplicationManager manager) {
        super(manager);
        sessionFactory = new Configuration()
                .addAnnotatedClass(ContactRecord.class)
                .addAnnotatedClass(GroupRecord.class)
                .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:mysql://localhost/addressbook?zeroDateTimeBehavior=convertToNull")
                .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "root")
                .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "")
                .buildSessionFactory();
    }

    private static List<GroupData> converList(List<GroupRecord> records) {
        return records.stream().map(HibernateHelper::convert).collect(Collectors.toList());
    }

    private static List<ContactData> converContactList(List<ContactRecord> records) {
        return records.stream().map(HibernateHelper::convertContact).collect(Collectors.toList());
    }

    private static GroupData convert(GroupRecord record) {
        return new GroupData("" + record.id, record.name, record.header, record.footer);
    }

    private static GroupRecord convert(GroupData groupData) {
        var id = groupData.id();
        if (id.isEmpty()) {
            id = "0";
        }
        return new GroupRecord(Integer.parseInt(id), groupData.name(), groupData.header(), groupData.footer());
    }

    private static ContactData convertContact(ContactRecord record) {
        return new ContactData(
                "" + record.id, record.firstname, record.lastname, record.middlename, record.address,
                record.email, record.home, record.mobile, record.work, record.fax, "",
                record.email2, record.email3);
    }

    private static ContactRecord convertContact(ContactData contactData) {
        var id = contactData.id();
        if (id.isEmpty()) {
            id = "0";
        }
        return new ContactRecord(Integer.parseInt(id), contactData.firstName(), contactData.lastName(),
                contactData.middleName(), contactData.address(), contactData.email(), contactData.mobile(),
                contactData.home(), contactData.work(), contactData.email2(), contactData.email3());
    }

    @Step
    public List<GroupData> getGroupsDBList() {
        return converList(sessionFactory.fromSession(session -> {
            return session.createQuery("from GroupRecord", GroupRecord.class).list();
        }));
    }

    @Step
    public List<ContactData> getContactsDBList() {
        return converContactList(sessionFactory.fromSession(session -> {
            return session.createQuery("from ContactRecord", ContactRecord.class).list();
        }));
    }

    public long getGroupsDBCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from GroupRecord", Long.class).getSingleResult();
        });
    }

    public long getContactDBCount() {
        return sessionFactory.fromSession(session -> {
            return session.createQuery("select count (*) from ContactRecord", Long.class).getSingleResult();
        });
    }

    public void createGroupInDB(GroupData groupData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convert(groupData));
            session.getTransaction().commit();
        });
    }

    @Step
    public void createContactInDB(ContactData contactData) {
        sessionFactory.inSession(session -> {
            session.getTransaction().begin();
            session.persist(convertContact(contactData));
            session.getTransaction().commit();
        });
    }

    @Step
    public List<ContactData> getContactsInGroup(GroupData group) {
        return sessionFactory.fromSession(session -> {
            return converContactList(session.get(GroupRecord.class, group.id()).contacts);
        });
    }
}
