package ro.nttdata.tutorial.status.boundery;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 *The status resource class
 * With the path ro/nttdata/status
 */
@Path("/status")
public class StatusResource {

    /**
     * Simple ping endpoint (response 200)
     * @return the String OK if server is running
     */
    @GET
    @Path("/ping")
    public Response ping(
            @Size(min = 1, max = 10, message = "Echo Length should be between 1 and 10!!")
            @Pattern(message = "Invalid Characters!!",
                    regexp = "^[0-9]*$")
            @QueryParam ("echo")
                   @Valid String echo) {
        if (echo == null){
            return Response.ok("OK").build();
        }
        return Response.status(200).entity("Now the new echo will be displayed : " + echo).build();

    }


}
