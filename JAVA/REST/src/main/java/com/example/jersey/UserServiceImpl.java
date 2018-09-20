package com.example.jersey;

import com.example.jersey.exceptionHandler.MyApplicationException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/users")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class UserServiceImpl implements UserService {

    static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") String id) throws MyApplicationException {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new MyApplicationException("Id can contain only numbers", e);
        }
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM User WHERE userid = :id");
            query.setInteger("id", Integer.parseInt(id));
            List<User> users = (List<User>) query.list();
            if (users.size() == 0) {
                throw new MyApplicationException("No user with id=" + Integer.parseInt(id) + " found");
            }
            User user = users.get(0);
            session.close();
            Response.ResponseBuilder builder = Response.ok(user);
            builder.header("username", user.getUsername());
            builder.header("user email ", user.getEmail());
            return builder.build();
        } catch (Exception e) {
            throw new MyApplicationException(e.getMessage(), e);
        }
    }


    @Override
    @GET
    @Path("/getall")
    @Produces(MediaType.APPLICATION_JSON)
//    @ValidateRequest
    public Response getAllUsers() throws MyApplicationException {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM User");
            List<User> users = (List<User>) query.list();
            session.close();
            if (users.size() == 0) {
                throw new MyApplicationException("no available data in DB");
            }
            Response.ResponseBuilder builder = Response.ok(users);
            builder.language("en")
                    .header("number of books", users.size());
            return builder.build();
        } catch (Exception e) {
            throw new MyApplicationException(e.getMessage(), e);
        }
    }

    @Override
    public Response getUserByUsername(String username) {
        return null;
    }


    /**
     * If user exists -> update, else -> creates new one
     *
     * @param user
     */
    @Override
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveUser(User user) throws MyApplicationException {
        try {
            Long id = user.getUserid();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            if (user.getUsername().equals("") || user.getPassword().equals("")
                    || user.getUsername() == null || user.getPassword() == null
                    || user.getUsername().equals("null") || user.getPassword().equals("null")) {
                throw new MyApplicationException("username and password cannot be null");
            }
            session.saveOrUpdate(user);
            session.getTransaction().commit();
            session.close();
            Response.ResponseBuilder builder;
            if (id == null) {
                builder = Response.ok("User created");
            } else {
                builder = Response.ok("User updated");
            }
            builder.header("modified user", user);
            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyApplicationException(e.getMessage() + "/ user not found", e);
        }
    }


    @Override
    @GET
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") String id) throws MyApplicationException {
        try {
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new MyApplicationException("Id can contain only numbers", e);
        }
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Long ID = Long.parseLong(id);
            Query query = session.createQuery("DELETE FROM User WHERE userid = :userid");
            query.setLong("userid", ID);
            int res = query.executeUpdate();
            session.getTransaction().commit();
            session.close();
            if (res == 0) { //daca nu se sterge nimic
                Response.ResponseBuilder builder = Response.ok("User with ID=" + ID + " not found.");
                builder.header("Not found user for id", ID);
                return builder.build();
            } else {
                Response.ResponseBuilder builder = Response.ok("user Deleted");
                builder.header("ID of deleted user", ID);
                return builder.build();
            }
        } catch (Exception e) {
            throw new MyApplicationException(e.getMessage(), e);
        }
    }


    @Override
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/getid/{username}/{email}")
    public Response getUserByUsernameAndEmail(@PathParam("username") String username,
                                              @PathParam("email") String email) throws MyApplicationException {
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM User WHERE username = :username AND email = :email");
            query.setString("username", username);
            query.setString("email", email);
            List<User> users = (List<User>) query.list();
            if (users.size() == 0) {
                throw new MyApplicationException("No user with given data found");
            } else if (users.size() > 1) {
                throw new MyApplicationException("More than 1 user with sama data found. Operation can't proceed");
            } else {
                Response.ResponseBuilder builder = Response.ok(users.get(0).getUserid().toString());
                builder.header("ID user",users.get(0).getUserid().toString());
                return builder.build();
            }
        } catch (Exception e) {
            throw new MyApplicationException(e.getMessage(), e);
        }
    }


}
