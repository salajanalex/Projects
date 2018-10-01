package ro.nttdata.tutorial.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
//@Table(name = "company", schema = "proiect1")
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcompany;
    private String name;
    private String domain;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.MERGE)
    private List<Person> personList;

    public Company() {
    }

    public Company(int id, String name, String domain) {
        this.idcompany = id;
        this.name = name;
        this.domain = domain;
    }

    public Company(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }

    public int getIdcompany() {
        return idcompany;
    }

    public void setIdcompany(int idcompany) {
        this.idcompany = idcompany;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public String toString() {
        return "Company{" +
                "idcompany=" + idcompany +
                ", name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
