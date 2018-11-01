package ro.nttdata.tutorial.admin.boundary;

import io.swagger.annotations.*;
import ro.nttdata.tutorial.admin.controller.AddressController;
import ro.nttdata.tutorial.admin.controller.CompanyController;
import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Address;
import ro.nttdata.tutorial.admin.entity.Company;
import ro.nttdata.tutorial.admin.entity.Person;
import ro.nttdata.tutorial.admin.entity.PersonAddressDTO;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Dependent
@Api(value = "/company")
@Path("/company")
public class CompanyResource {

    @Inject
    private CompanyController controller;
    @Inject
    private PersonController personController;
    @Inject
    private AddressController addressController;

    /**
     * Returning all companies as a JSON Response
     *
     * @return
     */
    @GET
    @ApiOperation(
            value = "Lists all companies",
            notes = "Lists all companies"
    )
    @ApiResponses(value= {
            @ApiResponse(code = 200, message = "Successful retrieval of companies"),
            @ApiResponse(code = 404, message = "Company records not found"),
            @ApiResponse(code = 500, message = "Internal servererror")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/allcompanies")
    public Response getAllCompanies() {
        List<Company> companyList = controller.getAllCompanies();
        return Response.status(Response.Status.OK).entity(companyList).build();
    }

    /**
     * Returning a company by given id. JSON Response
     *
     * @param id
     * @return
     */
    @GET
    @ApiOperation(
            value = "Company",
            notes = "Returns the company with selected id"
    )
    @ApiResponses(value= {
            @ApiResponse(code = 200, message = "Successful retrieval of company"),
            @ApiResponse(code = 404, message = "Company record not found"),
            @ApiResponse(code = 500, message = "Internal servererror")
    })
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/withid/{id}")
    public Response getCompanyById(@PathParam("id") Integer id) {
        Company company = controller.getCompanyById(id);
        if (company == null) {
            return Response.status(404).entity("Company Record not found").build();
        }
        Response.ResponseBuilder builder = Response.status(Response.Status.OK).entity(company);
        return builder.build();
    }

    /**
     * Creating a new company and returning it as a JSON Response
     *
     * @param company
     * @return
     */
    @POST
    @ApiOperation(
            value = "Adds new Company",
            notes = "Adds a new Company"
    )
    @ApiResponses(value= {
            @ApiResponse(code = 200, message = "Successful added new Company"),
            @ApiResponse(code = 500, message = "Internal servererror")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new")
    public Response addNewCompany(@Valid @ApiParam Company company) {
        controller.addCompany(company);
        Response.ResponseBuilder builder = Response.ok(company);
        return builder.build();
    }

    /**
     * updating an existing company, Id must also be specified in the company JSON sent as body.
     *
     * @param company
     * @return
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateCompany(@Valid Company company) {
        Company oldCompany = controller.getCompanyById(company.getIdcompany());
        List<Person> personList = oldCompany.getPersonList();
        company.setPersonList(personList);
        controller.updateCompany(company);
        return Response.ok(company).build();
    }

    /**
     * Deleting existing company by id
     * @param id
     * @return
     */
    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCompany(@PathParam("id") int id) {
        controller.deleteCompany(id);
        return Response.ok("Company with id= " + id + " deleted").build();
    }


    /**
     * Adding an existing person to an existing company.
     * Id of company and id of person must be specidied in address.
     *
     * @param idCompany
     * @param idPerson
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idCompany}/addpersonbyid/{idPerson}")
    public Response addExistingPersonToCompany(@PathParam("idCompany") int idCompany,
                                               @PathParam("idPerson") int idPerson) {
        Company company = controller.getCompanyById(idCompany);
        Person person = personController.getPersonById(idPerson);
        if (company != null && person != null) {
            person.setCompany(company);
            controller.updateCompany(company);
            personController.updatePerson(person); //nu cred ca trebe

        }
        Response.ResponseBuilder builder = Response.ok(company);
        return builder.build();
    }

    /**
     * Creating a new person and adding it to an existing company
     *
     * @param
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idCompany}/addnewperson")
    public Response addNewPersonToCompany(@PathParam("idCompany") int idCompany, @Valid PersonAddressDTO personAddressDTO) {
        final Person person = personAddressDTO.getPerson();
        Address address = personAddressDTO.getAddress();
        Company company = controller.getCompanyById(idCompany);
        boolean addressExists = false;
        if (person == null) {
            return Response.status(404).entity("Invalid person body, person appears to be null").build();
        }
        if (company == null) {
            return Response.status(404).entity("Company with if = " + idCompany + " not found").build();
        }
        if (address == null) {
            person.setCompany(company);
            personController.addPerson(person);
            controller.updateCompany(company);
            company = controller.getCompanyById(company.getIdcompany());
            Response.ResponseBuilder builder = Response.ok(company);
            return builder.build();
        }
        //if address != null
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
            person.setCompany(company);
            personController.addPerson(person);
            controller.updateCompany(company);
            person.setAddress(address);
            address.addPrsonToAddress(person);
            addressController.updateAddress(address);
            Response.ResponseBuilder builder = Response.ok(personAddressDTO);
            return builder.build();
        }
        // else: if addressExists = false
        person.setCompany(company);
        personController.addPerson(person);
        controller.updateCompany(company);
        person.setAddress(address);
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        address.setPersonList(personList);
        addressController.addAddress(address);
        addressController.updateAddress(address);
        Response.ResponseBuilder builder = Response.ok(personAddressDTO);
        return builder.build();
    }

}