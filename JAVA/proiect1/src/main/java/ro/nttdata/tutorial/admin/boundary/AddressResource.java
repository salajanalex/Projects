package ro.nttdata.tutorial.admin.boundary;

import ro.nttdata.tutorial.admin.controller.AddressController;
import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Address;
import ro.nttdata.tutorial.admin.entity.Person;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/address")
public class AddressResource {

    @Inject
    private AddressController controller;

    @Inject
    private PersonController personController;

    /**
     * Returns All Addresses as a JSON Response
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/alladdresses")
    public Response getAllAddresss() throws Exception {
        List<Address> addressList = controller.getAllAddresses();
        return Response.status(Response.Status.OK).entity(addressList).build();
    }

    /**
     * Return an Address as a JSON Response
     *
     * @param id
     * @return
     */
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/withid")
    public Response getAddressById(@QueryParam("id") Integer id) {
        Address address = controller.getAddressById(id);
        Response.ResponseBuilder builder = Response.status(Response.Status.OK).entity(address);
        return builder.build();
    }

    /**
     * Adds new Address to DB
     *
     * @param address
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new")
    public Response addNewAddress( @Valid Address address) throws Exception {
        List<Address> addressList = controller.getAllAddresses();
        controller.addAddress(address);
        Response.ResponseBuilder builder = Response.ok(address);
        return builder.build();
    }

    /**
     * Updates the Address of a given Person
     *
     * @param id
     * @param address
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/addressid")
    public Response updateAddressOfPerson(@QueryParam("id") int id,  @Valid Address address) {
        Person person = personController.getPersonById(id);
        if (person != null) {
            person.setAddress(address);
            personController.updatePerson(person);
            return Response.ok(address).build();
        } else
            return Response.status(204).entity("person with given id not found").build();
    }

    /**
     * Delets a person by ID
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAddress(@QueryParam("id") int id) throws Exception {
        controller.deleteAddress(id);
        return Response.ok("Address with id= " + id + " deleted").build();
    }

    /**
     * Updates the address
     *
     * @param address
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateAddress(@Valid Address address) {
        Address oldAddress = controller.getAddressById(address.getIdaddress());
        if (oldAddress == null) {
            return Response.status(404).entity("No address with id = " + address.getIdaddress() + "found").build();
        }
        List<Person> personList = oldAddress.getPersonList();
        address.setPersonList(personList);
        controller.updateAddress(address);
        return Response.ok(address).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addpersonbyid")
    public Response addExistingPersonToAddress(@QueryParam("idAddress") int idAddress,
                                               @QueryParam("idPerson") int idPerson) {
        Address address = controller.getAddressById(idAddress);
        Person person = personController.getPersonById(idPerson);
        if (person != null && address != null) {
            person.setAddress(address);
            controller.updateAddress(address);
            personController.updatePerson(person);
            return Response.ok(address).build();
        } else {
            return Response.ok("Unsuccessfully, Person or Addres with given id do not exist").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addnewperson")
    public Response addNewPersonToAddress(@QueryParam("idAddress") int idAddress,  @Valid Person person) {
        List<Person> personList = personController.getAllPersons();
        for (Person person1 : personList) {
            if (person1.getFullName().equals(person.getFullName())) {
                return Response.ok("Unable to create new person, person with given name already exists").build();
            }
        }
        Address address = controller.getAddressById(idAddress);
        if (address != null) {
            person.setAddress(address);
            personController.addPerson(person);
            controller.updateAddress(address);
            address = controller.getAddressById(address.getIdaddress());
            Response.ResponseBuilder builder = Response.ok(address);
            return builder.build();
        } else {
            Response.ResponseBuilder builder = Response.ok("Person could not be created because Address not found");
            return builder.build();
        }
    }


}
