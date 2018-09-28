package ro.nttdata.tutorial.admin.boundary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ro.nttdata.tutorial.admin.controller.CompanyController;
import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Company;
import ro.nttdata.tutorial.admin.entity.Person;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompanyResourceTest {

    @InjectMocks
    private CompanyResource companyResource;
    @Mock
    private CompanyController companyController;
    @Mock
    private PersonController personController;
    @Mock
    EntityManager entityManager;
    private Company company;

    @Before
    public void init() {
        company = new Company(1, "GOOGLE", "world domination");
    }

    @Test
    public void testGetAllCompanies() {
        final List<Company> companyList = new ArrayList<>();
        when(companyController.getAllCompanies()).thenReturn(companyList);    //fara linia asta dac ala sf verify in loc de assert
        assertEquals(companyResource.getAllCompanies().getEntity(), companyList);
    }

    @Test
    public void testGetCompanymergeById() {
        final Company company = new Company();
        when(companyController.getCompanyById(anyInt())).thenReturn(company);
        assertEquals(companyResource.getCompanyById(anyInt()).getEntity(), company);
    }

    @Test
    public void testAddNewCompany() {
        Assert.assertThat(companyResource.addNewCompany(company).getEntity().toString(),
                containsString(Integer.toString(company.getIdcompany())));
    }

    @Test
    public void testUpdateCompany() {
        Assert.assertThat(companyResource.updateCompany(company).getEntity().toString(),
                containsString(Integer.toString(company.getIdcompany())));
    }

    @Test
    public void testDeleteCompany() {
        Assert.assertThat(companyResource.deleteCompany(company.getIdcompany()).getEntity().toString(),
                containsString(Integer.toString(company.getIdcompany())));

    }

    @Test
    public void testAddExistingPersonToCompany() {
        Company company = new Company();
        Person person = new Person();
        //  asta treeb? merge si fara.
//        person.setCompany(company);
        when(companyController.getCompanyById(anyInt())).thenReturn(company);
        when(personController.getPersonById(anyInt())).thenReturn(person);
//        when(entityManager.merge(Person.class)).thenReturn(Person.class);
        Response response = companyResource.addExistingPersonToCompany(company.getIdcompany(), person.getIdperson());
        Assert.assertEquals(response.getEntity().toString(), person.toString());
        verify(personController, times(1)).updatePerson(person);    //asta de ce nu merge?
    }

    @Test
    public void testAddNewPersonToCompany() {
        Company company = new Company();
        Person person = new Person();
        when(companyController.getCompanyById(anyInt())).thenReturn(company);
        Response response = companyResource.addNewPersonToCompany(anyInt(), person);
        Assert.assertEquals(response.getEntity().toString(), person.toString());
        verify(personController, times(1)).updatePerson(person);
    }
}