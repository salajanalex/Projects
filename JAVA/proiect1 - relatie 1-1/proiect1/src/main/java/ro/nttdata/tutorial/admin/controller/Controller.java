package ro.nttdata.tutorial.admin.controller;

import ro.nttdata.tutorial.admin.entity.Person;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    public List<Person> findAll() {
        List<Person> persons = new ArrayList();
        Person person1 = new Person(1, "Sava", "Cristin", 22);
        Person person2 = new Person(2, "Salajan", "Alex", 22);
        Person person3 = new Person(3, "Pirpidel", "Sebastian", 22);
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        return persons;
    }

}
