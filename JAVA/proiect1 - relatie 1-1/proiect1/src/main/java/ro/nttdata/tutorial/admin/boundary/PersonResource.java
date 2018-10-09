package ro.nttdata.tutorial.admin.boundary;

import ro.nttdata.tutorial.admin.controller.AddressController;
import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Address;
import ro.nttdata.tutorial.admin.entity.Person;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/person")
public class PersonResource {

    @Inject
    private PersonController controller;

//    @Inject
//    private PersonController personController;

    @Inject
    private AddressController addressController;

    /**
     * Returns all Persons as a JSON Response
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/allpersons")
    public Response getAllPersons() {
        List<Person> personList = controller.getAllPersons();
        return Response.status(Response.Status.OK).entity(personList).build();
    }

    /**
     * Returns a Person with a given ID
     * @param id
     * @return
     */
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/withid/{id}")
    public Response getPersonById(@PathParam("id") Integer id) {
        Person person = controller.getPersonById(id);
        Response.ResponseBuilder builder = Response.status(Response.Status.OK).entity(person);
        return builder.build();
    }

    /**
     * It should work for person without address and person with address
     * Adds new Person
     * @param person
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new")
    public Response addNewPerson(Person person) {
        List<Person> personList = controller.getAllPersons();
        for (Person person1 : personList) {
            if (person1.getFullName().equals(person.getFullName())) {
                return Response.ok("Unable to create new person, person with given name already exists").build();
            }
        }
//        Address address = person.getAddress();
        controller.addPerson(person);
        Response.ResponseBuilder builder = Response.ok(person);
        return builder.build();
    }

    /**
     * Updates a Person
     * @param person
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updatePerson(Person person) {
        Person existingPerson = controller.getPersonById(person.getIdperson());
        Address existingAddress = existingPerson.getAddress();
        Address newAddress = person.getAddress();

        if (person.getAddress() == null) {
            person.setAddress(existingPerson.getAddress());
        } else if (person.getAddress().getIdaddress() != existingPerson.getAddress().getIdaddress()) {
            newAddress.setIdaddress(existingAddress.getIdaddress());
            person.setAddress(newAddress);
        }

        controller.updatePerson(person);
        return Response.ok(person).build();
    }

    /**
     * Updates the address of a person with givena ID
     * @param id
     * @param address
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/updateaddress")
    public Response updateAddressOfPerson(@PathParam("id") int id, Address address) {
        Person person = controller.getPersonById(id);
        Address addressToUpdate = person.getAddress();
        addressToUpdate.setCountry(address.getCountry());
        addressToUpdate.setCity(address.getCity());
        addressToUpdate.setNumber(address.getNumber());
        addressToUpdate.setStreet(address.getStreet());
        addressToUpdate.setPerson(person);
        person.setAddress(addressToUpdate);

        controller.updatePerson(person);
        addressController.updateAddress(address);  //??
        return Response.ok(person).build();
    }

    /**
     * Removes a Person By ID
     * @param id
     * @return
     */
    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("id") int id) {
        controller.deletePerson(id);
        return Response.ok("Person with id= " + id + " deleted").build();
    }

    /**
     * Remove Person from company by ID
     * @param id
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/fire/{id}")
    public Response removeFromCompany(@PathParam("id") int id) {
        Person person = controller.getPersonById(id);
        if (person != null){
            person.setCompany(null);
            controller.updatePerson(person);
            return Response.ok(person).build();
        } else {
            return Response.ok("Person with given id can't be found").build();
        }
    }
}
