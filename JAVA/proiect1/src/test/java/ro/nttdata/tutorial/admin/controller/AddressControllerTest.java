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
import ro.nttdata.tutorial.admin.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;
    @Mock
    private EntityManager entityManager;
    private List<Address> addressList;
    @Captor
    private ArgumentCaptor<Address> captor;
    @Captor
    private ArgumentCaptor<Integer> intCaptor;
    @Captor
    private ArgumentCaptor<Class> captorClass;
    @Captor
    private ArgumentCaptor<String> captorString;

    private Address address;

    @Before
    public void init() {
        address = new Address();
    }

    @Test
    public void testAddAddress() {
        addressController.addAddress(address);
        verify(entityManager).persist(captor.capture());
        final Address capturedAddress = captor.getValue();
        assertEquals(capturedAddress, address);
        verify(entityManager, times(1)).persist(address);
    }

    @Test
    public void testDeleteAddress() {
        TypedQuery query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        addressController.deleteAddress(1);
        verify(entityManager, times(1)).createNamedQuery(captorString.capture(), captorClass.capture());
        final String capturedQuery = captorString.getValue();
        final Class capturedClass = captorClass.getValue();
        verify(query, times(1)).executeUpdate();
        assertEquals(capturedQuery, Address.DELETE_ADDRESS_QUERY);
        assertEquals(capturedClass, Address.class);
    }

    @Test
    public void testGetAllAddresses() throws Exception {
        final List<Address> addressList = new ArrayList<>();
        addressList.add(address);
        final TypedQuery<Address> query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(addressList);
        final List<Address> controllerAddresses = addressController.getAllAddresses();
        verify(entityManager).createNamedQuery(captorString.capture(), captorClass.capture());
        final String capturedQuery = captorString.getValue();
        final Class capturedClass = captorClass.getValue();
        assertEquals(capturedQuery, Address.SELECT_ADDRESSES_QUERY);
        assertEquals(capturedClass, Address.class);
        assertEquals(controllerAddresses, addressList);
    }

    @Test
    public void testGetAddressById() {
        List<Person> personList = new ArrayList<>();
        Address newAdd = new Address();
        newAdd.setPersonList(personList);
        when(entityManager.find(any(Class.class), anyInt())).thenReturn(newAdd);
        Address mockAddress = addressController.getAddressById(1);
        assertEquals(newAdd, mockAddress);

        //astea pt a testa getterii si setterii
        Address add = new Address();
        add.setPersonList(personList);
        add.setNumber(mockAddress.getNumber());
        add.setStreet(mockAddress.getStreet());
        add.setCity(mockAddress.getCity());
        add.setCountry(mockAddress.getCountry());
        add.setIdaddress(mockAddress.getIdaddress());
        assertEquals(add.toString(), newAdd.toString());
    }


    @Test
    public void testUdpateAddress() {
        addressController.updateAddress(address);
        verify(entityManager, times(1)).merge(address);
        verify(entityManager).merge(captor.capture());
        final Address captured = captor.getValue();
        assertEquals(captured, address);
    }

}