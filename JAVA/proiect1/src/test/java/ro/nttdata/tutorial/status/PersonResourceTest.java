/**
 * Mocking test WITH @Annotations
 */

package ro.nttdata.tutorial.status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ro.nttdata.tutorial.admin.boundary.PersonResource;
import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonResourceTest {

    @InjectMocks
    private PersonResource personResource;
    @Mock
    private PersonController controller;
    @Mock
    private EntityManager entityManager;

    private List<Person> personList;
    private Iterator<Person> iterator;
    private final String VAR = "Sava";


    @Test
    public void testAddNewPerson() {
        Person person = new Person(1, "Sava", "Cristin", 23);
        Assert.assertThat(personResource.addNewPerson(person).getEntity().toString(), containsString(person.getName()));
    }

    @Test
    public void testUpdatePerson() {
        Person person = new Person(2, "Alex", "Salajan", 22);
        Assert.assertEquals(personResource.updatePerson(person).getEntity().toString(), person.toString());
    }

    @Test
    public void testDeletePerson() {
        Person person = new Person(2, "Alex", "Salajan", 22);
        String id = Integer.toString(person.getIdPerson());
        Assert.assertThat(personResource.deletePerson(person).getEntity().toString(), containsString(id));
    }

    @Test
    public void testGetPersonById() {
        final Person person = new Person();
        when(controller.getPersonById(anyInt())).thenReturn(person);
        final Response response = personResource.getPersonById(person.getIdPerson());
        assertEquals(response.getEntity(), person);
    }

    @Test
    public void testGetAllPersons() {
        final List<Person> personList = new ArrayList<>();
        final Query query = mock(Query.class);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(personList);
        final Response response = personResource.getAllPersons();
        assertEquals(response.getEntity(), personList);
    }


}
