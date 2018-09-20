/**
 * Mocking test WITH @Annotations
 */

package ro.nttdata.tutorial.status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import ro.nttdata.tutorial.admin.boundary.PersonResource;
import ro.nttdata.tutorial.admin.controller.Controller;
import ro.nttdata.tutorial.admin.entity.Person;

import javax.ws.rs.core.Response;
import java.util.Iterator;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonResourceTest {


    @InjectMocks
    private PersonResource underTest;
    @Spy
    private Controller controller;
    private List<Person> personList;
    private Iterator<Person> iterator;
    private final String VAR = "Sava";


    @Test
    public void testWithMock() {
        Response response = underTest.getAllPersons();
        when(controller.findAll()).thenReturn(personList);

        Assert.assertNotNull("Response should not be null!", response);
        Assert.assertEquals("Response status should be equal to: ", Response.Status.OK, response.getStatusInfo());
        Assert.assertEquals(controller.findAll(), personList);
    }


    @Test
    public void testWithSpy() {
        personList = controller.findAll();
        iterator = personList.iterator();
        while (iterator.hasNext()) {
            assertNotNull(iterator.next().getName());
        }
        final Response response = underTest.getAllPersons();
        when(controller.findAll()).thenReturn(personList);
        Assert.assertNotNull("Response should not be null!", response);
        Assert.assertEquals("Response status should be equal to: ", Response.Status.OK, response.getStatusInfo());

    }

}
