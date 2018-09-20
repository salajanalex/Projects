package ro.nttdata.tutorial.status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import ro.nttdata.tutorial.status.boundery.StatusResource;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(MockitoJUnitRunner.class)
public class StatusResourceTest {
    private final String VAR = "1234";

    @InjectMocks
    private StatusResource underTest;


    @Test
    public void testPing(){
        //prepare

        //call
        Response response = underTest.ping(null);
        //verify and assert
        Assert.assertNotNull("Response should not be null!",response);
        Assert.assertEquals("Response status should be equal to: ",Response.Status.OK, response.getStatusInfo());
        Assert.assertEquals("Response should be equal to: ", "OK", response.getEntity());
    }

     @Test
    public void testPingWithEcho(){
        //prepare

        //call
        Response response = underTest.ping(VAR);
        //verify and assert
         Assert.assertNotNull("Response should not be null!",response);
         Assert.assertThat(response.getEntity().toString(), containsString(VAR));
    }
}
