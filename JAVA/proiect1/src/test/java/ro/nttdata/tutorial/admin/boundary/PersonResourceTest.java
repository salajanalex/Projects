/**
 * Mocking test WITH @Annotations
 */

package ro.nttdata.tutorial.admin.boundary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ro.nttdata.tutorial.admin.controller.AddressController;
import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Address;
import ro.nttdata.tutorial.admin.entity.Person;
import ro.nttdata.tutorial.admin.entity.PersonAddressDTO;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

//@Ignore
@RunWith(MockitoJUnitRunner.class)
public class PersonResourceTest {

    @InjectMocks
    private PersonResource personResource;
    @Mock
    private PersonController personController;
    @Mock
    private AddressController addressController;
    private Person person;

    @Before
    public void init() {
        person = new Person(1, "Sava", "Cristin", 23);
    }

    /**
     * 2 Tests for AddNewPerson
     *
     * @throws Exception
     */

    @Test
    public void testAddNewPerson() throws Exception {
        Address address = new Address(1, "s", 2, "a", "s");
        List<Person> personList = new ArrayList<>();
        address.setPersonList(personList);   //altfel da null Pointer cand vrea sa adauge persoana la lista din adresa.
        //next 3 lines so that the test covers the whole "for" loop
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        when(addressController.getAllAddresses()).thenReturn(addressList);
        final PersonAddressDTO personAddressDTO = new PersonAddressDTO(person, address);
        final Response response = personResource.addNewPerson(personAddressDTO);
        final PersonAddressDTO DTO = (PersonAddressDTO) response.getEntity();
        final Address address1 = DTO.getAddress();
        final Person person1 = DTO.getPerson();
        Assert.assertThat(address1.toString(), containsString(address.getStreet()));
        Assert.assertThat(person1.toString(), containsString(person.getName()));
    }

    @Test
    public void testAddNewPersonWithNewAddress() throws Exception {
        Address address = new Address(1, "s", 2, "a", "s");
        List<Person> personList = new ArrayList<>();
        address.setPersonList(personList);   //altfel da null Pointer cand vrea sa adauge persoana la lista din adresa.
        final PersonAddressDTO personAddressDTO = new PersonAddressDTO(person, address);
        final Response response = personResource.addNewPerson(personAddressDTO);
        final PersonAddressDTO DTO = (PersonAddressDTO) response.getEntity();
        final Address address1 = DTO.getAddress();
        final Person person1 = DTO.getPerson();
        Assert.assertThat(address1.toString(), containsString(address.getStreet()));
        Assert.assertThat(person1.toString(), containsString(person.getName()));
    }

    @Test
    public void testAddNewPersonWithoutAddress() throws Exception {
        Address address = null;
        final PersonAddressDTO personAddressDTO = new PersonAddressDTO(person, address);
        final Response response = personResource.addNewPerson(personAddressDTO);
        final PersonAddressDTO DTO = (PersonAddressDTO) response.getEntity();
        final Person person1 = DTO.getPerson();
        Assert.assertThat(person1.toString(), containsString(person.getName()));
    }

    /**
     * 2 tests for update
     */

    @Test
    public void testUpdatePerson() {
        Person person = new Person();
        Address address = new Address();
        Person person1 = new Person();
        Address address1 = new Address(1,"",2,"","");
        person.setAddress(address);
        person1.setAddress(address1);
        when(personController.getPersonById(anyInt())).thenReturn(person);
        Assert.assertEquals(personResource.updatePerson(person1).getEntity().toString(),
                person.toString());
    }


    @Test
    public void testUpdatePersonWithNullAddress() {
        Address address = null;
        person.setAddress(address);
        when(personController.getPersonById(anyInt())).thenReturn(person);
        Assert.assertEquals(personResource.updatePerson(person).getEntity().toString(),
                person.toString());
    }

    @Test
    public void testDeletePerson() {
        String id = Integer.toString(person.getIdperson());
        Assert.assertThat(personResource.deletePerson(person.getIdperson()).getEntity().toString(), containsString(id));
    }

    @Test
    public void testGetPersonById() {
        final Person person = new Person();
        when(personController.getPersonById(anyInt())).thenReturn(person);
        final Response response = personResource.getPersonById(anyInt());
        assertEquals(response.getEntity(), person);
    }

    @Test
    public void testGetAllPersons() {
        final List<Person> personList = new ArrayList<>();
        when(personController.getAllPersons()).thenReturn(personList);
        final Response response = personResource.getAllPersons();
        assertEquals(response.getEntity(), personList);
    }

    /**
     * 2 Tests for removeFromCompany (fire)
     */

    @Test
    public void testRemoveFromCompany() {
        when(personController.getPersonById(anyInt())).thenReturn(person);
        final Response response = personResource.removeFromCompany(anyInt());
        assertEquals(response.getEntity(), person);
    }

    @Test
    public void testRemoveNullPersonFromCompany() {
        Person person = null;
        when(personController.getPersonById(anyInt())).thenReturn(person);
        final Response response = personResource.removeFromCompany(anyInt());
        assertEquals(response.getEntity().toString(), "Person with given id can't be found");
    }

    @Test
    public void testUpdateAddressOfPerson() {
        final Address address = new Address();
        final List<Person> personList = new ArrayList<>();
        address.setPersonList(personList);
        person.setAddress(address);
        when(personController.getPersonById(anyInt())).thenReturn(person);
        final Response response = personResource.updateAddressOfPerson(1, address);
        assertEquals(response.getEntity(), person);
    }


}
