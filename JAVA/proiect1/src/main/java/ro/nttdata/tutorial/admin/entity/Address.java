package ro.nttdata.tutorial.admin.entity;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
//@Table(name = "address", schema = "proiect1")
@NamedQueries({
        @NamedQuery(name = Address.SELECT_ADDRESSES_QUERY, query = "SELECT a FROM Address a",
                hints={@QueryHint(name= QueryHints.REFRESH, value= HintValues.TRUE)}),
        @NamedQuery(name = Address.DELETE_ADDRESS_QUERY, query = "DELETE from Address a WHERE a.idaddress = :id")
})
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idaddress;

    @Size(min = 2, max = 100, message = "Street lengths must be between 2 and 100 characters")
    private String street;

    @Min(value = 1, message = "Nr cant be less than 1")
    @Max(value = 9999, message = "Nr cant be greater than 9999")
    private int number;

    @Size(min = 2, max = 100, message = "city length must be between 2 and 100 characters")
    private String city;

    @Size(min = 2, max = 100, message = "country length must be between 2 and 100 characters")
    private String country;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "idperson")
//    @JsonIgnore  //ca sa nu mearga lainfinit din adresa in persoana si invers.
//    @XmlInverseReference(mappedBy = "address")
//    private Person person;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "address", cascade = CascadeType.MERGE)
    @NotNull(message = "personList cant be null")
    private List<Person> personList;

    public final static String DELETE_ADDRESS_QUERY = "Address.delete";
    public final static String SELECT_ADDRESSES_QUERY = "Address.findAll";
    public final static String EMPTY_LIST_EXCEPTION_MESSAGE = "List is empty";
    public final static String NULL_LIST_EXCEPTION_MESSAGE = "The list is NULL";

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

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public void addPrsonToAddress(Person person){
        this.personList.add(person);
    }


    @Override
    public String toString() {
        return "Address{" +
                "idaddress=" + idaddress +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", personList=" + personList +
                '}';
    }
}
