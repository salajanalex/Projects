package ro.nttdata.tutorial.admin.entity;

import javax.persistence.*;


@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idperson")
    private int idPerson;
    private String name;
    private String surname;
    private int age;
    @Transient
    private String completeName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcompany")
    public Company company;
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL,  //in mappedBy -> numele campului de care depinde.
            fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idaddress")
    private Address address;

    public Person() {
    }

    public Person(int idPerson, String name, String surname, int age) {
        this.idPerson = idPerson;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
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

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String name, String surname) {
        this.completeName = name + " " + surname;
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
                "idPerson=" + idPerson +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}
