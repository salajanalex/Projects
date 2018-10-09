package ro.nttdata.tutorial.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import javax.persistence.*;


@Entity
//@Table(name = "person", schema = "proiect1")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idperson;
    private String name;
    private String surname;
    private int age;
    @Transient
    private String fullName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcompany", nullable = true)
    @JsonIgnore    // pt varianta de Jersey (Marshall)
    @XmlInverseReference(mappedBy = "person")   // pt varianta de moxy (Unmarshall)
    public Company company;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL,  //in mappedBy -> numele campului de care depinde.
            fetch = FetchType.LAZY, optional = false)
    private Address address;

    public final static String DELETE_PERSON_QUERY = "DELETE FROM Person p WHERE p.idperson = :id";
    public static final String SELECT_PERSONS_QUERY = "SELECT p FROM Person as p";

    public Person() {
    }

    public Person(int idPerson, String name, String surname, int age) {
        this.idperson = idPerson;
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

    public void setFullName(String name, String surname) {
        this.fullName = name + " " + surname;
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
