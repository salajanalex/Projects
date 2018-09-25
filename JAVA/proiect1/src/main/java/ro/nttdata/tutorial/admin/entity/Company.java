package ro.nttdata.tutorial.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "company")
public class Company implements Serializable {

    @Id
    @Column(name = "idcompany")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCompany;
    private String name;
    private String domain;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.MERGE)
    private List<Person> personList;

    public Company() {
    }

    public Company(int id, String name, String domain) {
        this.idCompany = id;
        this.name = name;
        this.domain = domain;
    }

    public Company(String name, String domain) {
        this.name = name;
        this.domain = domain;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
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
                "idCompany=" + idCompany +
                ", name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                '}';
    }
}
