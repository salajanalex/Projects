package ro.nttdata.tutorial.admin.controller;

import ro.nttdata.tutorial.admin.entity.Person;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateful
public class PersonController {

    @PersistenceContext(unitName = "testsProject")
    private EntityManager entityManager;

    /**
     * Adding a persons to DB
     *
     * @param person
     */
    public void addPerson(Person person) {
        entityManager.persist(person);
    }

    /**
     * deleting a person by id
     *
     * @param id
     */
    public void deletePerson(int id) {
        Query query = entityManager.createQuery(Person.DELETE_PERSON_QUERY);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * finding a person by id
     *
     * @param id
     * @return
     */
    public Person getPersonById(int id) {
        return entityManager.find(Person.class, id);
    }

    /**
     * finding and returning all persons from DB
     * @return
     */
//    @Transactional
    public List<Person> getAllPersons() {
        Query query = entityManager.createQuery(Person.SELECT_PERSONS_QUERY);
        List<Person> updatedPersons = query.getResultList();
        for (Person person : updatedPersons) {
            entityManager.refresh(person);
        }
        return updatedPersons;
    }

    /**
     * Updating a person
     * @param person
     */
    public void updatePerson(Person person) {
        entityManager.merge(person);
    }

}
