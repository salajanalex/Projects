package com.example.jersey;

import com.example.jersey.exceptionHandler.MyApplicationException;

import javax.ws.rs.core.Response;

public interface UserService {

    Response getUserById(String id) throws MyApplicationException;
    Response getUserByUsername(String username) throws MyApplicationException;
    Response saveUser(User user) throws MyApplicationException;
    Response deleteUser(String id) throws MyApplicationException;
    Response getAllUsers() throws MyApplicationException;
    Response getUserByUsernameAndEmail(String username, String email) throws MyApplicationException;
}
