package ro.nttdata.tutorial.admin.controller;

import ro.nttdata.tutorial.admin.entity.Person;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

@Stateful
public class PersonController {

    @Inject
    @PersistenceContext(unitName = "testProject", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public void addPerson(Person person) {
        entityManager.persist(person);
    }


    public void deletePerson(Person person) {
        entityManager.remove(person);
    }

    public Person getPersonById(int id) {
        return entityManager.find(Person.class, id);
    }

    public List<Person> getAllPersons() {
        Query query = entityManager.createQuery("SELECT p FROM Person p");
        return query.getResultList();
    }

    public void updatePerson(Person person) {
        entityManager.merge(person);
    }

}
