/**
 * TEst class with NO Mockito Annotations.
 */

package ro.nttdata.tutorial.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import ro.nttdata.tutorial.admin.boundary.PersonResource;
import ro.nttdata.tutorial.admin.controller.Controller;
import ro.nttdata.tutorial.admin.entity.Person;

import javax.ws.rs.core.Response;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class PersonResourcetestNoAnnTest {

    private PersonResource personResource;
    private Controller controller;
    private List<Person> personList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        controller = spy(Controller.class);
        personResource = mock(PersonResource.class);
        // nu merge, trebe facut setter de controller in clasa si initializat normal.
    }

    @Test
    public void testSpy() {
        Response response = personResource.getAllPersons();
        personList = controller.findAll();
        assertEquals(3, personList.size());
        for (Person p : personList) {
            assertNotNull(p.getName());
        }
    }
}
