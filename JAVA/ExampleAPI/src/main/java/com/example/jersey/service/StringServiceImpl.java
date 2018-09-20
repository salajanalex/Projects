package com.example.jersey.service;

import com.example.jersey.model.StringInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/string")
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class StringServiceImpl implements StringService {

    @Override
    @GET
    @Path("/{input}/length")
    public StringInfo stringCount(@PathParam("input") String input) {

        StringInfo si = new StringInfo();
        si.setString(input);
        si.setLength(input.length());
        return si;
    }

    @Override
    @GET
    @Path("/{input}/len")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)

    public String simpleCount(@PathParam("input") String input) { //fa string ret type
        String s = (String) input;
        return "The String" + " \"" + s + "\" " + "has " + s.length() + " characters";
    }

}
