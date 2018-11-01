package ro.nttdata.tutorial.admin.boundary;

import ro.nttdata.tutorial.admin.controller.AddressController;
import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Address;
import ro.nttdata.tutorial.admin.entity.Person;
import ro.nttdata.tutorial.admin.entity.PersonAddressDTO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/person")
public class PersonResource {

    @Inject
    private PersonController controller;

    @Inject
    private AddressController addressController;

    /**
     * Returns all Persons as a JSON Response
     *
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
     *
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
     *
     * @param personAndAddress
     * @return Returns a "Transaction marked for rollback." message if person already exists in DB
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new")
    public Response addNewPerson(PersonAddressDTO personAndAddress) {
        boolean addressExists = false;
        Person person = personAndAddress.getPerson();
        Address address = personAndAddress.getAddress();
        controller.addPerson(person);
        /**
         * We add only the person withoud an address
         */
        if (address == null) {
            Response.ResponseBuilder builder = Response.ok(personAndAddress);
            return builder.build();
        }
        /**
         * checking if address exists or not. If yes, setting it.
         */
        List<Address> addressList = addressController.getAllAddresses();
        for (Address eachAddress : addressList) {
            if ((eachAddress.getNumber() == address.getNumber()) && eachAddress.getStreet().equals(address.getStreet())
                    && eachAddress.getCity().equals(address.getCity())) {
                address = eachAddress;
                addressExists = true;
                break;
            }
        }
        if (addressExists) {
            person.setAddress(address);
            address.addPrsonToAddress(person);
            addressController.updateAddress(address);
            Response.ResponseBuilder builder = Response.ok(personAndAddress);
            return builder.build();
        }
        person.setAddress(address);
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        address.setPersonList(personList);
        addressController.addAddress(address);
        addressController.updateAddress(address);
        Response.ResponseBuilder builder = Response.ok(personAndAddress);
        return builder.build();
    }


    /**
     * Updates a Person
     *
     * @param person
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updatePerson(@Valid Person person) {
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
     *
     * @param id
     * @param address
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/updateaddress")
    public Response updateAddressOfPerson(@PathParam("id") int id, @Valid Address address) {
        Person personToUpdate = controller.getPersonById(id);
        if (personToUpdate == null) {
            return Response.status(403).entity("Person with given ID not fond").build();
        }
        boolean addressExists = false;
        List<Address> addressList = addressController.getAllAddresses();
        for (Address eachAddress : addressList) {
            if ((eachAddress.getNumber() == address.getNumber()) && eachAddress.getStreet().equals(address.getStreet())
                    && eachAddress.getCity().equals(address.getCity())) {
                address = eachAddress;
                addressExists = true;
                break;
            }
        }
        if (addressExists) {
            personToUpdate.setAddress(address);
            controller.updatePerson(personToUpdate);
            addressController.updateAddress(address);
            return Response.ok(address).build() ;
        } else {
            addressController.addAddress(address);
            address.addPrsonToAddress(personToUpdate);
            personToUpdate.setAddress(address);
            controller.updatePerson(personToUpdate);
            addressController.updateAddress(address);
            return Response.ok(address).build() ;
        }










//        Person person = controller.getPersonById(id);
//        Address addressToUpdate = person.getAddress();
//        addressToUpdate.setCountry(address.getCountry());
//        addressToUpdate.setCity(address.getCity());
//        addressToUpdate.setNumber(address.getNumber());
//        addressToUpdate.setStreet(address.getStreet());
//        List<Person> personList = address.getPersonList();
//        personList.add(person);
//        addressToUpdate.setPersonList(personList);
//        person.setAddress(addressToUpdate);
//
//        controller.updatePerson(person);
//        addressController.updateAddress(address);  //??
//        return Response.ok(person).build();
    }

    /**
     * Removes a Person By ID
     *
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
     *
     * @param id
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/fire/{id}")
    public Response removeFromCompany(@PathParam("id") int id) {
        Person person = controller.getPersonById(id);
        if (person != null) {
            person.setCompany(null);
            controller.updatePerson(person);
            return Response.ok(person).build();
        } else {
            return Response.status(403).entity("Person with given id can't be found").build();
        }
    }
}
