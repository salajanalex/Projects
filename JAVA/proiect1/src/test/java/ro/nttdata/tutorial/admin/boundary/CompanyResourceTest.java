package ro.nttdata.tutorial.admin.boundary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ro.nttdata.tutorial.admin.controller.AddressController;
import ro.nttdata.tutorial.admin.controller.CompanyController;
import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Address;
import ro.nttdata.tutorial.admin.entity.Company;
import ro.nttdata.tutorial.admin.entity.Person;
import ro.nttdata.tutorial.admin.entity.PersonAddressDTO;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
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
    private AddressController addressController;


    private Company company;

    @Before
    public void init() {
        company = new Company(1, "GOOGLE", "world domination");
    }

    @Test
    public void testGetAllCompanies() throws Exception {
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
    public void testAddNewCompany() throws Exception {
        Assert.assertThat(companyResource.addNewCompany(company).getEntity().toString(),
                containsString(Integer.toString(company.getIdcompany())));
    }

    @Test
    public void testUpdateCompany() {
        final Company company = new Company();
        when(companyController.getCompanyById(anyInt())).thenReturn(company);
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
        when(companyController.getCompanyById(anyInt())).thenReturn(company);
        when(personController.getPersonById(anyInt())).thenReturn(person);
        Response response = companyResource.addExistingPersonToCompany(company.getIdcompany(), person.getIdperson());
        Assert.assertEquals(response.getEntity().toString(), company.toString());
        verify(companyController, times(1)).updateCompany(company);    //asta de ce nu merge?
    }

    /**
     * 4 Tests for addNewPersonToComapny
     * Test 1: Test for null Person
     * @throws Exception
     */
    @Test
    public void testAddNewNullPersonToCompany() throws Exception {
        Company company = new Company();
        PersonAddressDTO personAddressDTO = new PersonAddressDTO();
        when(companyController.getCompanyById(anyInt())).thenReturn(company);
        Response response = companyResource.addNewPersonToCompany(company.getIdcompany(), personAddressDTO);
        Assert.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    /**
     * Test 2 for addNewPersonToComapny: Test for Null Company
     */
    @Test
    public void testAddNewPersonToNullCompany() throws Exception {
        Person person = new Person();
        PersonAddressDTO personAddressDTO = new PersonAddressDTO(person, new Address());
        when(companyController.getCompanyById(anyInt())).thenReturn(null);
        Response response = companyResource.addNewPersonToCompany(1, personAddressDTO);
        Assert.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
        Assert.assertEquals(response.getEntity().toString(), "Company with if = " + 1 + " not found");
    }

    /**
     * Test 3 for addNewPersonToComapny: For Person with null Address.
     * @throws Exception
     */
    @Test
    public void testAddNewPersonToCompanyWithNullAddress() throws Exception {
        Person person = new Person();
        PersonAddressDTO personAddressDTO = new PersonAddressDTO(person,null);
        when(companyController.getCompanyById(anyInt())).thenReturn(company);
        Response response = companyResource.addNewPersonToCompany(company.getIdcompany(), personAddressDTO);
        Assert.assertEquals(response.getEntity().toString(), company.toString());
    }

    /**
     * Test 4 for addNewPersonToComapny: Person with existing Address
     * @throws Exception
     */
    @Test
    public void testAddNewPersonToCompanyWithExistingAddress() throws Exception {
        Person person = new Person();
        Address address = new Address(1, "a" ,2,  "b","v");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        address.setPersonList(personList);
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        PersonAddressDTO personAddressDTO = new PersonAddressDTO(person,address);
        when(companyController.getCompanyById(anyInt())).thenReturn(company);
        when(addressController.getAllAddresses()).thenReturn(addressList);
        Response response = companyResource.addNewPersonToCompany(company.getIdcompany(), personAddressDTO);
        Assert.assertEquals(response.getEntity().toString(), personAddressDTO.toString());
    }

    /**
     * Test 5 for addNewPersonToComapny: Person with new Address
     * @throws Exception
     */
    @Test
    public void testAddNewPersonToCompanyWithNewAddress() throws Exception {
        Person person = new Person();
        Address address = new Address(1, "a" ,2,  "b","v");
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        address.setPersonList(personList);
        List<Address> addressList = new ArrayList<>();
        PersonAddressDTO personAddressDTO = new PersonAddressDTO(person,address);
        when(companyController.getCompanyById(anyInt())).thenReturn(company);
        when(addressController.getAllAddresses()).thenReturn(addressList);
        Response response = companyResource.addNewPersonToCompany(company.getIdcompany(), personAddressDTO);
        Assert.assertEquals(response.getEntity().toString(), personAddressDTO.toString());
    }



}