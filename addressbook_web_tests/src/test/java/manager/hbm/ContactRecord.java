package manager.hbm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "addressbook")
public class ContactRecord {
    @Id
    public int id;

    public String firstname;
    public String lastname;
    public String middlename;
    public String address;
    public String email;
    public String mobile;
    public String nickname="";
    public String company="";
    public String title="";
    public String home;
    public String work;
    public String fax="";
    public String email2;
    public String email3;
    public String homepage="";


    public ContactRecord() {
    }

    public ContactRecord(
            int id, String firstname, String lastname, String middlename, String address, String email, String mobile,
            String home, String work, String email2, String email3) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.home = home;
        this.work = work;
        this.email2 = email2;
        this.email3 = email3;
    }
}
