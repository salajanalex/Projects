package ro.nttdata.tutorial.admin.controller;

import ro.nttdata.tutorial.admin.entity.Person;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

@Stateful
public class PersonController {

    @PersistenceContext(unitName = "testsProject")
    private EntityManager entityManager;

    public void addPerson(Person person) {
        entityManager.persist(person);
    }

    public void deletePerson(int id) {
        Query query = entityManager.createQuery("DELETE p from Person p WHERE p.id = :id") ;
        query.setParameter("id", id);
        query.executeUpdate();
    }

    public Person getPersonById(int id) {
        return entityManager.find(Person.class, id);
    }

    public List<Person> getAllPersons() {
        Query query = entityManager.createQuery("SELECT p FROM Person as p");
        return query.getResultList();
    }

    public void updatePerson(Person person) {
        entityManager.merge(person);
    }

}
