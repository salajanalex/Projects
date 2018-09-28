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

@Path("/address")
public class AddressResource {

    @Inject
    private AddressController controller;

    @Inject
    private PersonController personController;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/alladdresses")
    public Response getAllAddresss() {
        List<Address> addressList = controller.getAllAddresses();
        return Response.status(Response.Status.OK).entity(addressList).build();
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/withid/{id}")
    public Response getAddressById(@PathParam("id") Integer id) {
        Address address = controller.getAddressById(id);
        Response.ResponseBuilder builder = Response.status(Response.Status.OK).entity(address);
        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new")
    public Response addNewAddress(Address address) {
        controller.addAddress(address);
        Response.ResponseBuilder builder = Response.ok(address);
        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update/personid/{id}")
    public Response updateAddress(@PathParam("id") int id, Address address) {
        Person person = personController.getPersonById(id);
        if (person != null) {
            person.setAddress(address);
            personController.updatePerson(person);
            return Response.ok(address).build();
        } else
            return Response.ok("person with given id not found").build();
    }

    @GET
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAddress(@PathParam("id") int id) {
        controller.deleteAddress(id);
        return Response.ok("Address with id= " + id + " deleted").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateAddressOfPerson(Address address) {
        controller.updateAddress(address);
        return Response.ok(address).build();
    }


}
