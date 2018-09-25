package ro.nttdata.tutorial.admin.boundary;

import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Person;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * The PersonResource class
 */
@Path("/person")
public class PersonResource {

    @Inject
    private PersonController controller;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/allpersons")
    public Response getAllPersons() {
        List<Person> personList = controller.getAllPersons();
        return Response.status(Response.Status.OK).entity(personList).build();
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/withid/{id}")
    public Response getPersonById(@PathParam("id") Integer id) {
        Person person = controller.getPersonById(id);
        Response.ResponseBuilder builder = Response.status(Response.Status.OK).entity(person);
        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new")
    public Response addNewPerson(Person person) {
        controller.addPerson(person);
        Response.ResponseBuilder builder = Response.ok(person);
        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updatePerson(Person person) {
        controller.updatePerson(person);
        return Response.ok(person).build();
    }

    @GET
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePerson(Person person) {
        controller.deletePerson(person);
        return Response.ok("Person with id= " + person.getIdPerson() + " deleted").build();
    }


}
