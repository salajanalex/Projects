package com.example.jersey.service;

import com.example.jersey.model.Person;
import com.example.jersey.model.Response;

public interface PersonService {

    public Response addPerson(Person p);

    public Response deletePerson(int id);

    public Person getPerson(int id);

    public Person[] getAllPersons();

}
