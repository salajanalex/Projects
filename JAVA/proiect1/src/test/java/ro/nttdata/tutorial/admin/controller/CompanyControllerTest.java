package ro.nttdata.tutorial.admin.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ro.nttdata.tutorial.admin.entity.Company;
import ro.nttdata.tutorial.consumer.producer.MyApplicationException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.Silent.class)
public class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;
    @Mock
    private EntityManager entityManager;
    @Captor
    private ArgumentCaptor<Company> captor;
    private Company company;

    @Before
    public void init(){
        company = new Company();
    }

    @Test
    public void testAddCompany() {
        companyController.addCompany(company);
        verify(entityManager, times(1)).persist(company);
    }

    @Test
    public void testAddCompany2() {
        companyController.addCompany(company);
        verify(entityManager).persist(captor.capture());
        final Company captureCompany = captor.getValue();
        assertEquals(captureCompany, company);
    }

    @Test
    public void testDeleteCompany() throws MyApplicationException {
        companyController.deleteCompany(company);
        verify(entityManager, times(1)).remove(company);
    }

    @Test
    public void testUpdateCompany() {
        companyController.updateCompany(company);
        verify(entityManager, times(1)).merge(company);
    }

    @Test
    public void testGetCompanyById() {
        Company newComp = companyController.getCompanyById(company.getIdCompany());
        when(entityManager.find(Company.class, company.getIdCompany())).thenReturn(newComp);
    }

    @Test
    public void testGetAllCompanies() {
        final List<Company> compList = new ArrayList<>();
        final Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(compList);
        final List<Company> controllerCompanies = companyController.getAllCompanies();
        assertEquals(controllerCompanies, compList);
    }

}

