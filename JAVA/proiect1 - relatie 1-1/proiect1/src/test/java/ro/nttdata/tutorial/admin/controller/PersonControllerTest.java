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
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;
    @Mock
    private EntityManager entityManager;
    @Captor
    private ArgumentCaptor<Person> captor;
    private Person person;

    @Before
    public void init() {
        person = new Person();
    }

    @Test
    public void testAddPerson() {
        personController.addPerson(person);
        verify(entityManager, times(1)).persist(person);
    }

    @Test
    public void testDeletePerson() {
        final Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        personController.deletePerson(anyInt());
        verify(query, times(1)).executeUpdate();
    }

    @Test
    public void testGetPersonById() {
        Person person = new Person();
        when(entityManager.find(any(Class.class), anyInt())).thenReturn(person);
        Person mockPerson = personController.getPersonById(1);
        assertEquals(person, mockPerson);
    }

    @Test
    public void testGetAllPersons() {
        final List<Person> personList = new ArrayList<>();
        final Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(personList);
        assertEquals(personController.getAllPersons(), personList);
    }

    @Test
    public void testUpdatePerson() {
        personController.updatePerson(person);
        verify(entityManager, times(1)).merge(person);
    }
}