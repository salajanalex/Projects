package ro.nttdata.tutorial.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import javax.persistence.*;

@Entity
//@Table(name = "address", schema = "proiect1")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idaddress;
    private String street;
    private int number;
    private String city;
    private String country;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idperson")
    @JsonIgnore  //ca sa nu mearga lainfinit din adresa in persoana si invers.
    @XmlInverseReference(mappedBy = "address")
    private Person person;

    public final static String DELETE_ADDRESS_QUERY = "DELETE from Address a WHERE a.idaddress = :id";
    public final static String SELECT_ADDRESSES_QUERY = "SELECT a FROM Address a";

    public Address() {
    }

    public Address(int idaddress, String street, int number, String city, String country) {
        this.idaddress = idaddress;
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }

    public Address(String street, int number, String city, String country) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }


    public int getIdaddress() {
        return idaddress;
    }

    public void setIdaddress(int idaddress) {
        this.idaddress = idaddress;
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
                "idaddress=" + idaddress +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
