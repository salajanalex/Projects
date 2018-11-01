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

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;
    @Mock
    private EntityManager entityManager;
    @Captor
    private ArgumentCaptor<Company> captor;
    @Captor
    private ArgumentCaptor<String> captorString;
    @Captor
    private ArgumentCaptor<Class> captorClass;
    private Company company;

    @Before
    public void init() {
        company = new Company();
    }

    @Test
    public void testAddCompany() {
        companyController.addCompany(company);
        verify(entityManager).persist(captor.capture());
        final Company captureCompany = captor.getValue();
        assertEquals(captureCompany, company);
        verify(entityManager, times(1)).persist(company);
    }

    @Test
    public void testDeleteCompany() {
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        companyController.deleteCompany(1);
        verify(entityManager).createNamedQuery(captorString.capture(), captorClass.capture());
        final String capturedQuery = captorString.getValue();
        final Class capturedClass = captorClass.getValue();
        verify(query, times(1)).executeUpdate();
        assertEquals( Company.DELETE_COMPANY_QUERY, capturedQuery);
        assertEquals(capturedClass, Company.class);
    }


    @Test
    public void testUpdateCompany() {
        companyController.updateCompany(company);
        verify(entityManager).merge(captor.capture());
        final Company captureCompany = captor.getValue();
        assertEquals(captureCompany, company);
        verify(entityManager, times(1)).merge(company);
    }

    @Test
    public void testGetCompanyById() {
        Company newComp = new Company("a", "s");
        when(entityManager.find(any(Class.class), anyInt())).thenReturn(newComp);
        Company acutlaC = companyController.getCompanyById(1);
        assertEquals(newComp, acutlaC);

        //astea pt a testa getterii si setterii
        Company comp = new Company();
        comp.setIdcompany(acutlaC.getIdcompany());
        comp.setPersonList(acutlaC.getPersonList());
        comp.setDomain(acutlaC.getDomain());
        comp.setName(acutlaC.getName());
        assertEquals(newComp.getIdcompany(), comp.getIdcompany());
        assertEquals(newComp.getPersonList(), comp.getPersonList());
        assertEquals(newComp.getDomain(), comp.getDomain());
        assertEquals(newComp.getName(), comp.getName());
    }

    @Test
    public void testGetAllCompanies() throws Exception {
        final List<Company> compList = new ArrayList<>();
        compList.add(company);
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(compList);
        final List<Company> controllerCompanies = companyController.getAllCompanies();
        verify(entityManager).createNamedQuery(captorString.capture(), captorClass.capture());
        final String capturedQuery = captorString.getValue();
        final Class capturedClass = captorClass.getValue();
        assertEquals(controllerCompanies, compList);
        assertEquals( Company.GET_ALL_COMPANIES_QUERY, capturedQuery);
        assertEquals(capturedClass, Company.class);
    }

    @Test(expected = Exception.class)
    public void testGetAllCompaniesAsEmpty() throws Exception {
        final List<Company> compList = new ArrayList<>();
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(compList);
        companyController.getAllCompanies();
    }

    @Test
    public void testGetAllCompaniesAsEmpty2() {
        final List<Company> compList = new ArrayList<>();
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(compList);
        try {
            final List<Company> controllerCompanies = companyController.getAllCompanies();
        } catch (Exception e) {
            assertEquals(e.getMessage(), Company.EMPTY_LIST_EXCEPTION_MESSAGE);
        } finally {
            verify(entityManager).createNamedQuery(captorString.capture(), captorClass.capture());
            final String capturedQuery = captorString.getValue();
            final Class capturedClass = captorClass.getValue();
            assertEquals(Company.GET_ALL_COMPANIES_QUERY, capturedQuery);
            assertEquals(capturedClass, Company.class);
        }
    }

    @Test
    public void testGetAllCompaniesAsNull() throws Exception {
        List<Company> compList = null;
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(compList);
        try {
            companyController.getAllCompanies();
        } catch (NullPointerException e) {
            assertEquals(Company.NULL_LIST_EXCEPTION_MESSAGE, e.getMessage());
        }
    }

}

