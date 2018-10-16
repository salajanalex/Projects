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

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddressResourceTest {

    @InjectMocks
    private AddressResource addressResource;
    @Mock
    private AddressController addressController;
    @Mock
    private PersonController personController;
    private Address address;

    @Before
    public void init() {
        address = new Address(1, "Strada", 23, "Detalii", "Multe");
    }

    @Test
    public void testAddNewAddress() throws Exception {
        Assert.assertThat(addressResource.addNewAddress(address).getEntity().toString(),
                containsString(Integer.toString(address.getIdaddress())));
    }

    @Test
    public void testUpdateAddressOfPerson() {
        Person person = new Person();
        when(personController.getPersonById(anyInt())).thenReturn(person);
        final Response response = addressResource.updateAddressOfPerson(anyInt(), address);
        Assert.assertThat(response.getEntity().toString(),
                containsString(Integer.toString(address.getIdaddress())));
    }

    @Test
    public void testUpdateNullAddressOfPerson() {
        Person person = null;
        when(personController.getPersonById(anyInt())).thenReturn(person);
        final Response response = addressResource.updateAddressOfPerson(anyInt(), address);
        Assert.assertThat(response.getEntity().toString(),
                containsString("person with given id not found"));
    }

    /**
     * Test for Address with existing address to update
     */
    @Test
    public void testUpdateAddress() {
        when(addressController.getAddressById(anyInt())).thenReturn(address);
        final Response response = addressResource.updateAddress(address);
        Assert.assertThat(response.getEntity().toString(),
                containsString(Integer.toString(address.getIdaddress())));
    }

    /**
     * test for UpdateAddress with wrong idAddress. Address to update not found (null)
     */
    @Test
    public void testUpdateAddressNotFound() {
        when(addressController.getAddressById(anyInt())).thenReturn(null);
        final Response response = addressResource.updateAddress(address);
        Assert.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    public void testDeleteAddress() throws Exception {

        Assert.assertThat(addressResource.deleteAddress(address.getIdaddress()).getEntity().toString(),
                containsString(Integer.toString(address.getIdaddress())));
    }

    @Test
    public void testGetAddressById() {
        final Address address2 = new Address();
        when(addressController.getAddressById(anyInt())).thenReturn(address2);
        final Response response = addressResource.getAddressById(anyInt());
        Assert.assertEquals(response.getEntity(), address2);
    }

    @Test
    public void testGetAllAddresses() throws Exception {
        final List<Address> addressList = new ArrayList<>();
        when(addressController.getAllAddresses()).thenReturn(addressList);
        Response response = addressResource.getAllAddresss();
        Assert.assertEquals(response.getEntity(), addressList);
    }

    @Test
    public void testAddExistingPersonToAddress() {
        final Person person = new Person();
        when(addressController.getAddressById(anyInt())).thenReturn(address);
        when(personController.getPersonById(anyInt())).thenReturn(person);
        final Response response = addressResource.addExistingPersonToAddress(address.getIdaddress(), person.getIdperson());
        Assert.assertEquals(response.getEntity().toString(), address.toString());
    }

    @Test
    public void testAddNullPersonToAddress() {
        final Person person = new Person();
        final Address address1 = null;
        when(addressController.getAddressById(anyInt())).thenReturn(address1);
        when(personController.getPersonById(anyInt())).thenReturn(person);
        final Response response = addressResource.addExistingPersonToAddress(address.getIdaddress(), person.getIdperson());
        Assert.assertEquals(response.getEntity().toString(), "Unsuccessfully, Person or Addres with given id do not exist");
    }

    @Test
    public void testAddNewPersonToAddress() {
        final Person person = new Person();
        final List<Person> personList = new ArrayList<>();
        when(personController.getAllPersons()).thenReturn(personList);
        when(addressController.getAddressById(anyInt())).thenReturn(address);
        final Response response = addressResource.addNewPersonToAddress(1, person);
        Assert.assertEquals(response.getEntity().toString(), address.toString());
    }

    @Test
    public void testAddNewPersonToNullAddress() {
        final Address address1 = null;
        final Person person = new Person();
        final List<Person> personList = new ArrayList<>();
        when(personController.getAllPersons()).thenReturn(personList);
        when(addressController.getAddressById(anyInt())).thenReturn(address1);
        final Response response = addressResource.addNewPersonToAddress(1, person);
        Assert.assertEquals(response.getEntity().toString(), "Person could not be created because Address not found");
    }

    /**
     * person exists so new one cant be created
     */
    @Test
    public void testAddNewPersonExistingToAddress() {
        final Person person = new Person();
        final List<Person> personList = new ArrayList<>();
        personList.add(person);
        when(personController.getAllPersons()).thenReturn(personList);
        final Response response = addressResource.addNewPersonToAddress(1, person);
        Assert.assertEquals(response.getEntity().toString(), "Unable to create new person, person with given name already exists");
    }



}