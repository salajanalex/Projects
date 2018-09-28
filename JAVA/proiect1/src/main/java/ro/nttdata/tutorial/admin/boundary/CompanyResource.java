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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/allcompanies")
    public Response getAllCompanies() {
        List<Company> companyList = controller.getAllCompanies();
        return Response.status(Response.Status.OK).entity(companyList).build();
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/withid/{id}")
    public Response getCompanyById(@PathParam("id") Integer id) {
        Company company = controller.getCompanyById(id);
        Response.ResponseBuilder builder = Response.status(Response.Status.OK).entity(company);
        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/new")
    public Response addNewCompany(Company company) {
        controller.addCompany(company);
        Response.ResponseBuilder builder = Response.ok(company);
        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response updateCompany(Company company) {
        controller.updateCompany(company);
        return Response.ok(company).build();
    }

    @GET
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCompany(@PathParam("id") int id) {
        controller.deleteCompany(id);
        return Response.ok("Company with id= " + id + " deleted").build();
    }

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
            personController.updatePerson(person);
        }
        Response.ResponseBuilder builder = Response.ok(person);
        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idCompany}/addnewperson")
    public Response addNewPersonToCompany(@PathParam("idCompany") int idCompany, Person person) {
        Company company = controller.getCompanyById(idCompany);
        if (company != null) {
            person.setCompany(company);
            personController.updatePerson(person);
            Response.ResponseBuilder builder = Response.ok(person);
            return builder.build();
        } else {
            Response.ResponseBuilder builder = Response.ok("Person could not be created because Company not found");
            return builder.build();
        }
    }

}