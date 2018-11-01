/**
 * Using namedQueries
 */
package ro.nttdata.tutorial.admin.controller;

import ro.nttdata.tutorial.admin.entity.Company;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Stateful
public class CompanyController {

    @PersistenceContext(unitName = "testsProject")
    private EntityManager entityManager;

    /**
     * Adding new Company with the persist method.
     *
     * @param company
     */
    public void addCompany(Company company) {
        entityManager.persist(company);
    }

    /**
     * Deleting by id an existing company
     *
     * @param id
     */
    public void deleteCompany(int id) {
        TypedQuery<Company> query = entityManager.createNamedQuery(Company.DELETE_COMPANY_QUERY, Company.class);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * Finding and returning of a company by ID
     *
     * @param id
     * @return Company
     */
    public Company getCompanyById(int id) {
        return entityManager.find(Company.class, id);
    }

    /**
     * Finding and returning all companies
     *
     * @return
     * @throws Exception
     */
    public List<Company> getAllCompanies() {
        TypedQuery<Company> query = entityManager.createNamedQuery(Company.GET_ALL_COMPANIES_QUERY, Company.class);
        if (query.getResultList() == null) {
            throw new NullPointerException(Company.NULL_LIST_EXCEPTION_MESSAGE);
        } else if (query.getResultList().isEmpty()) {
            throw new IllegalStateException(Company.EMPTY_LIST_EXCEPTION_MESSAGE);
        } else return query.getResultList();
    }

    /**
     * updating an existing company. Id must be given in the JSON.
     *
     * @param company
     */
    public void updateCompany(Company company) {
        entityManager.merge(company);
    }


}
