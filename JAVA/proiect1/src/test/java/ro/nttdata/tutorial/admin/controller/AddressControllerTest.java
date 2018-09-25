package ro.nttdata.tutorial.admin.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ro.nttdata.tutorial.admin.entity.Address;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;
    @Mock
    private EntityManager entityManager;
    private List<Address> addressList;
    @Captor
    private ArgumentCaptor<Address> captor;
    private Address address;

    @Before
    public void init() {
        address = new Address();
    }

    @Test
    public void testAddAddress() {
        addressController.addAddress(address);
        verify(entityManager, times(1)).persist(address);
    }

    @Test
    public void testDeleteAddress() {
        addressController.deleteAddress(address);
        verify(entityManager, times(1)).remove(address);
    }

    @Test
    public void testGetAllAddresses() {
        final List<Address> addressList = new ArrayList<>();
        final Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(addressList);
        final List<Address> controllerAddresses = addressController.getAllAddresses();
        assertEquals(controllerAddresses, addressList);
    }

    @Test
    public void testGetAddressById() {
        Address newAdd = addressController.getAddressById(address.getIdAddress());
        when(entityManager.find(Address.class, address.getIdAddress())).thenReturn(newAdd);
    }

    @Test
    public void testUdpateAddress() {
        addressController.udpateAddress(address);
        verify(entityManager, times(1)).merge(address);
    }

    @Test
    public void testUpdateAddress2() {
        addressController.udpateAddress(address);
        verify(entityManager).merge(captor.capture());
        assertEquals(captor.getValue(), address);
    }
}