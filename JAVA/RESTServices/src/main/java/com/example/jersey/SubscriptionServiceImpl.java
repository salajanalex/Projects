package com.example.jersey;

import com.example.jersey.exceptionHandler.MyApplicationException;
import org.glassfish.jersey.client.ClientConfig;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

@Path("/subscriptions")
public class SubscriptionServiceImpl implements SubscriptionService {

    public SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//    Connection conn = DriverManagerager.g

    @GET
    @Path("/get/{id}")
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSubscriptionById(@PathParam("id") String id) throws MyApplicationException {
        try {
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new MyApplicationException("Id can contain only numbers", e);
        }
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            String sql = "SELECT * FROM subscriptions WHERE subscriptionid = :id";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(Subscription.class);
            query.setParameter("id", Long.parseLong(id));
            List<Subscription> results = query.list();
            if (results.size() == 0) {
                throw new MyApplicationException("No subscriptiom with id=" + Integer.parseInt(id) + " found");
            }
            session.close();
            Subscription subscription = results.get(0);
            Response.ResponseBuilder builder = Response.ok(subscription);
            builder.header("Subscription found", subscription);
            return builder.build();
        } catch (Exception e) {
            throw new MyApplicationException(e.getMessage(), e);
        }
    }

    @Override
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveSubscription(Subscription subscription) throws MyApplicationException {
        try {
            Long id = subscription.getSubscriptionid();
            int price = subscription.getPrice();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            if (subscription.getName().isEmpty() || subscription.getDescription().isEmpty()
                    || subscription.getPrice() < 1)
                throw new MyApplicationException("Invalid data for Subscription to save.");
            session.saveOrUpdate(subscription);
            session.getTransaction().commit();
            session.close();
            Response.ResponseBuilder builder;
            if (id == null) {
                builder = Response.ok("New subscription created");
            } else {
                builder = Response.ok("Subscription updated");
            }
            builder.header("saved subscription", subscription);
            return builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyApplicationException(e.getMessage(), e);
        }
    }

    @Override
    @GET
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteSubscription(@PathParam("id") String id) throws MyApplicationException {
        try {
            Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new MyApplicationException("ID can contain only numbers", e);
        }
        try {
            Session session = sessionFactory.getCurrentSession(); //se poate si cu openSession() -> atunci trebe session.close();
            session.beginTransaction();
            Long ID = Long.parseLong(id);
            //native SQL Query
            SQLQuery query = session.createSQLQuery("DELETE FROM subscriptions WHERE subscriptionid = :id");
            query.setParameter("id", ID);
            int res = query.executeUpdate();
            session.getTransaction().commit();
            if (res == 0) {
                Response.ResponseBuilder builder = Response.ok("Subscription with id=" + ID + " not found. Try again");
                return builder.build();
            } else {
                Response.ResponseBuilder builder = Response.ok("Subscription deleted");
                builder.header("Subscription deleted with ID", ID);
                return builder.build();
            }
        } catch (Exception e) {
            throw new MyApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Face legatura intre 2 aplicatii, aplicatia RESTService apeleaza un serviciu din aplicatia REST2.
     *
     * @return
     * @throws MyApplicationException
     */
    @Override
    @POST
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignSubscription(@PathParam("id") String subscriptionId, @QueryParam("username") String username,
                                       @QueryParam("email") String email) throws MyApplicationException {
        try {
            Long subId = Long.parseLong(subscriptionId);
        } catch (Exception e) {
            throw new MyApplicationException("Subscription ID must be a valid Number!", e);
        }
        try {
            ClientConfig config = new ClientConfig();
            Client client = ClientBuilder.newClient(config);
            WebTarget target = client.target(getBaseURI());
            String result = target.path(username).path(email).request().accept(MediaType.TEXT_PLAIN).get(String.class);
            Long subId = Long.parseLong(subscriptionId);
            Long userId = Long.parseLong(result);
            int updateSuccess = subscribe(subId, userId);
            if (updateSuccess == 1) {
                Response.ResponseBuilder response = Response.ok("The subscription for userID=" +userId +
                        " and subscriptionId=" +subscriptionId +" was SUCCESSFULLY created");
                return response.build();
            } else {
                Response.ResponseBuilder response = Response.ok("Subscription creation FAILED, please check input Data and try again");
                return response.build();
            }
        } catch (
                Exception e) {
            throw new MyApplicationException(e.getMessage(), e);
        }

    }




    /**
     * Method for path parameter.
     *
     * @return
     */
    private static URI getBaseURI() {
        return UriBuilder.fromUri(
                "http://localhost:8080/REST2/users/getid").build();
    }

    /**
     * method for queryParamether, varianta asta nu e implementata in proiectul REST2.
     *
     * @param username
     * @param email
     * @return
     */
    private static URI getBaseURI(String username, String email) {
        return UriBuilder.fromUri(
                "http://localhost:8080/REST2/users/getid?username=" + username + "&email=" + email).build();
    }


    /**
     * Returns 1 if user subscribed with succes to Subscription; 0 if fail;
     *
     * @param subscriptionid
     * @param userid
     * @return
     * @throws MyApplicationException
     */
    public int subscribe(Long subscriptionid, Long userid) throws MyApplicationException {
        try {
            Session session = sessionFactory.getCurrentSession(); //se poate si cu openSession() -> atunci trebe session.close();
            session.beginTransaction();
            SQLQuery query = session.createSQLQuery("INSERT INTO usersubscription (subscriptionid, userid) " +
                    " values (:id1,:id2)");
            query.setParameter("id1", subscriptionid);
            query.setParameter("id2", userid);
            int res = query.executeUpdate();
            session.getTransaction().commit();
            return res;
        } catch (Exception e) {
            throw new MyApplicationException(e.getMessage(), e);
        }
    }

}
