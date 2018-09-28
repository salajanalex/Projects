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
    public void testAddNewAddress() {
        Assert.assertThat(addressResource.addNewAddress(address).getEntity().toString(),
                containsString(Integer.toString(address.getIdaddress())));
    }

    @Test
    public void testUpdateAddress() {
        Person person = new Person();
        when(personController.getPersonById(anyInt())).thenReturn(person);
        Assert.assertThat(addressResource.updateAddress(anyInt(),address).getEntity().toString(),
                containsString(Integer.toString(address.getIdaddress())));
    }

    @Test
    public void testDeleteAddress() {
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
    public void testGetAllAddresses() {
        final List<Address> addressList = new ArrayList<>();
        when(addressController.getAllAddresses()).thenReturn(addressList);
        Response response = addressResource.getAllAddresss();
        Assert.assertEquals(response.getEntity(), addressList);
    }
}