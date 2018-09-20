package com.example.jersey;

import com.example.jersey.exceptionHandler.MyApplicationException;

import javax.ws.rs.core.Response;

public interface SubscriptionService {

    Response getSubscriptionById(String id) throws MyApplicationException;
    Response saveSubscription(Subscription subscription) throws MyApplicationException;
    Response deleteSubscription(String id) throws MyApplicationException;
    Response assignSubscription(String subscriptionId, String username, String email) throws MyApplicationException;
}
