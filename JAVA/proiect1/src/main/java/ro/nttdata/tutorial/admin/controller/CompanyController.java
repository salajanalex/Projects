package ro.nttdata.tutorial.admin.controller;


import ro.nttdata.tutorial.admin.entity.Company;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

@Stateful
public class CompanyController {

    @PersistenceContext(unitName = "testProject", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void addCompany(Company company) {
        entityManager.persist(company);
    }


    public void deleteCompany(Company company) {
        entityManager.remove(company);
    }

    public Company getCompanyById(int id) {
       return entityManager.find(Company.class, id);
    }

    public List<Company> getAllCompanies() {
        Query query = entityManager.createQuery("SELECT c FROM Company c");
        return query.getResultList();
    }

    public void updateCompany(Company company) {
        entityManager.merge(company);
    }


}
