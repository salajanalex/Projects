package com.example.jersey.service;

import com.example.jersey.model.StringInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/json")
public class JSONService {

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public StringInfo getStringIngoInJSON() {

        StringInfo si = new StringInfo();
        si.setString("Enter Sandman");
        si.setLength(si.getString().length());

        return si;

    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public StringInfo createTrackInJSON(StringInfo si) {

//        String result = "StringInfo saved : " + si;
//        return Response.status(201).entity(result).build();
        String newString = si.getString() + " long";
        si.setString(newString);
        si.setLength(newString.length());
        return si;
    }

}