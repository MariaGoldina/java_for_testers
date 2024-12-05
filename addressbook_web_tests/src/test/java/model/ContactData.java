package model;

public record ContactData (
        String firstName, String lastName, String middleName, String address, String email, String mobile
) {
    public ContactData() {
        this("", "", "", "", "", "");
    }

    public ContactData withFirstName(String firstName) {
        return new ContactData(firstName, this.lastName, this.middleName, this.address, this.email, this.mobile);
    }

    public ContactData withLastName(String lastName) {
        return new ContactData(this.firstName, lastName, this.middleName, this.address, this.email, this.mobile);
    }

    public ContactData withMiddleName(String middleName) {
        return new ContactData(this.firstName, this.lastName, middleName, this.address, this.email, this.mobile);
    }

    public ContactData withAddress(String address) {
        return new ContactData(this.firstName, this.lastName, this.middleName, address, this.email, this.mobile);
    }

    public ContactData withEmail(String email) {
        return new ContactData(this.firstName, this.lastName, this.middleName, this.address, email, this.mobile);
    }

    public ContactData withMobilePhone(String mobilePhone) {
        return new ContactData(this.firstName, this.lastName, this.middleName, this.address, this.email, mobilePhone);
    }
}