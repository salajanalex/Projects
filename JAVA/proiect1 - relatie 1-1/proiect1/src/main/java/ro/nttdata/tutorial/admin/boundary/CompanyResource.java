package ro.nttdata.tutorial.admin.boundary;

import ro.nttdata.tutorial.admin.controller.CompanyController;
import ro.nttdata.tutorial.admin.controller.PersonController;
import ro.nttdata.tutorial.admin.entity.Company;
import ro.nttdata.tutorial.admin.entity.Person;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/company")
public class CompanyResource {

    @Inject
    private CompanyController controller;
    @Inject
    private PersonController personController;

    /**
     * Returning all companies as a JSON Response
     *
     * @return
     * @throws Exception
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/allcompanies")
    public Response getAllCompanies() throws Exception {
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
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/withid/{id}")
    public Response getCompanyById(@PathParam("id") Integer id) {
        Company company = controller.getCompanyById(id);
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new")
    public Response addNewCompany(Company company) throws Exception {
        List<Company> companyList = controller.getAllCompanies();
        for (Company comp : companyList) {
            if (comp.getName().equals(company.getName())){
                return Response.ok("Company Already Exists").build();
            }
        }
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
    public Response updateCompany(Company company) {
        Company oldCompany = controller.getCompanyById(company.getIdcompany());
        List<Person> personList = oldCompany.getPersonList();
        company.setPersonList(personList);
        controller.updateCompany(company);
        return Response.ok(company).build();
    }

    /**
     * Deleting existing company by id
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCompany(@PathParam("id") int id) {
        Company company = controller.getCompanyById(id);
        if (company == null) {
            return Response.ok("Company does not exist").build();
        }
        for (Person person : company.getPersonList()) {
            if (person.getCompany().getIdcompany() == id) {
                person.setCompany(null);
                personController.updatePerson(person);
            }
        }

        controller.deleteCompany(id);
        return Response.ok("Company with id = " + id + " deleted").build();
    }


    @DELETE
    @Path("/delete2/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCompany2(@PathParam("id") int id) {
        Company company = controller.getCompanyById(id);
        controller.deleteCompany2(company);
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
     * @param idCompany
     * @param person
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idCompany}/addnewperson")
    public Response addNewPersonToCompany(@PathParam("idCompany") int idCompany, Person person) {
        List<Person> personList = personController.getAllPersons();
        for (Person person1 : personList) {
            if (person1.getFullName().equals(person.getFullName())) {
                return Response.ok("Unable to create new person, person with given name already exists").build();
            }
        }
        Company company = controller.getCompanyById(idCompany);
        if (company != null) {
            person.setCompany(company);
            personController.addPerson(person);
            controller.updateCompany(company);
            company = controller.getCompanyById(company.getIdcompany());
            Response.ResponseBuilder builder = Response.ok(company);
            return builder.build();
        } else {
            Response.ResponseBuilder builder = Response.ok("Person could not be created because Company not found");
            return builder.build();
        }
    }

}