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
    public String home="";
    public String work="";
    public String fax="";
    public String email2="";
    public String email3="";
    public String homepage="";


    public ContactRecord() {
    }

    public ContactRecord(
            int id, String firstname, String lastname, String middlename, String address, String email, String mobile) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
    }
}
