package ro.nttdata.tutorial.admin.boundary;

import ro.nttdata.tutorial.admin.controller.AddressController;
import ro.nttdata.tutorial.admin.entity.Address;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/address")
public class AddressResource {

    @Inject
    private AddressController controller;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/alladdresss")
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
    @Path("/update")
    public Response updateAddress(Address address) {
        controller.updateAddress(address);
        return Response.ok(address).build();
    }

    @GET
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAddress(Address address) {
        controller.deleteAddress(address);
        return Response.ok("Address with id= " + address.getIdAddress() + " deleted").build();
    }


}
