package ro.nttdata.tutorial.admin.entity;

import javax.persistence.*;

@Entity
@Table(name = "adress")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idaddress")
    private int idAddress;
    @Column(name = "street")
    private String street;
    @Column(name = "number")
    private int number;
    @Column(name = "city")
    private String city;
    @Column(name = "country")
    private String country;
    @OneToOne(mappedBy = "adress", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    private Person person;

    public Address() {
    }

    public Address(int idAddress, String street, int number, String city, String country) {
        this.idAddress = idAddress;
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Address{" +
                "idAddress=" + idAddress +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
