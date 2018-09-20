package ro.nttdata.tutorial.admin.boundary;

import ro.nttdata.tutorial.admin.controller.Controller;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * The PersonResource class
 */
@Path("/person")
public class PersonResource {

    /**
     * @return all persons
     */
    @Inject
    private Controller controller;




    @GET
    @Path("/persons")
    public Response getAllPersons() {
        return Response.status(Response.Status.OK).entity(controller.findAll()).build();
    }
}
