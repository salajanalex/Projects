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
import ro.nttdata.tutorial.admin.entity.Company;
import ro.nttdata.tutorial.admin.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;
    @Mock
    private EntityManager entityManager;
    @Captor
    private ArgumentCaptor<Person> captor;
    @Captor
    private ArgumentCaptor<Integer> intCaptor;
    @Captor
    private ArgumentCaptor<Class> captorClass;
    @Captor
    private ArgumentCaptor<String> captorString;

    private Person person;

    @Before
    public void init() {
        person = new Person();
    }

    @Test
    public void testAddPerson() {
        Address address = new Address();
        Company company = new Company();
        //for setters coverage
        person.setAddress(address);
        person.setAge(23);
        person.setName("maria");
        person.setSurname("ioana");
        person.setCompany(company);
        person.setIdperson(person.getIdperson());

        personController.addPerson(person);
        verify(entityManager).persist(captor.capture());
        verify(entityManager, times(1)).persist(person);
        final Person capturedPerson = captor.getValue();
        assertEquals(capturedPerson, person);
        assertThat(person.toString(), containsString("maria"));
        //for getters coverage
        assertEquals(capturedPerson.getName(), person.getName());
        assertEquals(capturedPerson.getAge(), person.getAge());
        assertEquals(capturedPerson.getAddress(), person.getAddress());
        assertEquals(capturedPerson.getFullName(), person.getFullName());
        assertEquals(capturedPerson.getSurname(), person.getSurname());
        assertEquals(capturedPerson.getIdperson(), person.getIdperson());
        assertEquals(capturedPerson.getCompany(), person.getCompany());
        assertEquals(capturedPerson.toString(), person.toString());
    }

    /**
     * Done
     */
    @Test
    public void testDeletePerson() {
        final TypedQuery<Person> query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        personController.deletePerson(1);
        verify(entityManager).createNamedQuery(captorString.capture(), captorClass.capture());
        final String capturedQuery = captorString.getValue();
        final Class capturedClass = captorClass.getValue();
        verify(query, times(1)).executeUpdate();
        assertEquals( Person.DELETE_PERSON_QUERY, capturedQuery);
        assertEquals(capturedClass, Person.class);

    }

    @Test
    public void testGetPersonById() {
        when(entityManager.find(any(Class.class), anyInt())).thenReturn(person);
        Person mockPerson = personController.getPersonById(person.getIdperson());
        assertEquals(person, mockPerson);
        verify(entityManager).find(captorClass.capture(), intCaptor.capture());
        final Class capturedClass = captorClass.getValue();
        final int capturedInt = intCaptor.getValue();
        assertEquals(capturedInt, person.getIdperson());
        assertEquals(capturedClass, Person.class);
    }

    @Test
    public void testGetAllPersons() {
        final List<Person> personList = new ArrayList<>();
        personList.add(person);
        TypedQuery<Person> query = mock(TypedQuery.class);
        when(entityManager.createNamedQuery(anyString(), any(Class.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(personList);
        final List<Person> result = personController.getAllPersons();
        verify(entityManager).createNamedQuery(captorString.capture(), captorClass.capture());
        final Class capturedClass = captorClass.getValue();
        final String capturedString = captorString.getValue();
        assertEquals(result, personList);
        assertEquals(capturedClass, Person.class);
        assertEquals(Person.SELECT_PERSONS_QUERY, capturedString);

    }


    @Test
    public void testUpdatePerson() {
        personController.updatePerson(person);
        verify(entityManager).merge(captor.capture());
        Person capturedPerson = captor.getValue();
        assertEquals(capturedPerson, person);
        verify(entityManager, times(1)).merge(person);

    }
}