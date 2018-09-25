package ro.nttdata.tutorial.admin.boundary;

import ro.nttdata.tutorial.admin.controller.CompanyController;
import ro.nttdata.tutorial.admin.entity.Company;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/company")
public class CompanyResource {

    @Inject
    private CompanyController controller;

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
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCompany(Company company) {
        controller.deleteCompany(company);
        return Response.ok("Company with id= " + company.getIdCompany() + " deleted").build();
    }
}