package ro.nttdata.tutorial.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
//@Table(name = "person", schema = "proiect1")
@NamedQueries({
        @NamedQuery(name = Person.SELECT_PERSONS_QUERY, query = "SELECT p FROM Person as p",
                hints = {@QueryHint(name = QueryHints.REFRESH, value = HintValues.TRUE)}),
        @NamedQuery(name = Person.DELETE_PERSON_QUERY, query = "DELETE from Person p WHERE p.idperson = :id")
})
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idperson;

    @Size(min = 2, max = 100, message = "name length must be between 2 and 100 characters")
    @NotEmpty(message = "name cannot be null")
    private String name;

    @Size(min = 2, max = 100, message = "surname length must be between 2 and 100 characters")
    private String surname;

    @Min(value = 16, message = "Age should not be less than 16")
    @Max(value = 150, message = "Age should not be greater than 150")
    private int age;


    @Transient
    private String fullName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcompany")
    @JsonIgnore    // pt varianta de Jersey (Marshall)
    @XmlInverseReference(mappedBy = "person")   // pt varianta de moxy (Unmarshall)
    public Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idaddress")
    @JsonIgnore
    @XmlInverseReference(mappedBy = "person")
//    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL,  //in mappedBy -> numele campului de care depinde.
//            fetch = FetchType.LAZY, optional = false)
    private Address address;


    public final static String DELETE_PERSON_QUERY = "Person.delete";
    public final static String SELECT_PERSONS_QUERY = "Person.findAll";

    public Person() {
    }

    public Person(int idPerson, String name, String surname, int age) {
        this.idperson = idPerson;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public int getIdperson() {
        return idperson;
    }

    public void setIdperson(int idperson) {
        this.idperson = idperson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullName() {
        return this.name + " " + this.surname;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "idperson=" + idperson +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}
