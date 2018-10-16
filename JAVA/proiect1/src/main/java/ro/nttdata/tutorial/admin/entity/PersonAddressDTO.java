package ro.nttdata.tutorial.admin.entity;

public class PersonAddressDTO {
    public Person person;
    public Address address;

    public PersonAddressDTO() {}

    public PersonAddressDTO(Person person, Address address) {
        this.person = person;
        this.address = address;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "PersonAddressDTO{" +
                "person=" + person +
                ", address=" + address +
                '}';
    }
}
