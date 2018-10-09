package ro.nttdata.tutorial.admin.entity;

import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
//@Table(name = "company", schema = "proiect1")
@NamedQueries({
        @NamedQuery(name = Company.GET_ALL_COMPANIES_QUERY, query = "SELECT c FROM Company c",
                hints={@QueryHint(name= QueryHints.REFRESH, value= HintValues.TRUE)}),
        @NamedQuery(name = Company.DELETE_COMPANY_QUERY, query = "DELETE from Company c WHERE c.idcompany = :id")
})
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idcompany;
    private String name;
    private String domain;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company", cascade = CascadeType.MERGE)
    private List<Person> personList;

    public final static String GET_ALL_COMPANIES_QUERY = "Company.findAll";
    public final static String DELETE_COMPANY_QUERY = "Company.delete";
    public final static String EMPTY_LIST_EXCEPTION_MESSAGE = "List is empty";
    public final static String NULL_LIST_EXCEPTION_MESSAGE = "The list is NULL";

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
