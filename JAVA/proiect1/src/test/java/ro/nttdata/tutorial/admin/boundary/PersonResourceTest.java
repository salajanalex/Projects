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
import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Person;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonResourceTest {

    @InjectMocks
    private PersonResource personResource;
    @Mock
    private PersonController personController;
    private Person person;

    @Before
    public void init() {
        person = new Person(1, "Sava", "Cristin", 23);
    }

    @Test
    public void testAddNewPerson() {
        Assert.assertThat(personResource.addNewPerson(person).getEntity().toString(), containsString(person.getName()));
    }

    @Test
    public void testUpdatePerson() {
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


}
