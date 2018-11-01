package ro.nttdata.tutorial.admin.controller;

import ro.nttdata.tutorial.admin.entity.Person;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
        TypedQuery query = entityManager.createNamedQuery(Person.DELETE_PERSON_QUERY, Person.class);
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
     *
     * @return
     */
    public List<Person> getAllPersons() {
        TypedQuery query = entityManager.createNamedQuery(Person.SELECT_PERSONS_QUERY, Person.class);
        if (query.getResultList() == null) {
            throw new NullPointerException(Person.NULL_LIST_EXCEPTION_MESSAGE);
        } else if (query.getResultList().isEmpty()){
            throw new IllegalStateException(Person.EMPTY_LIST_EXCEPTION_MESSAGE);
        } else return query.getResultList();
    }

    /**
     * Updating a person
     *
     * @param person
     */
    public void updatePerson(Person person) {
        entityManager.merge(person);
    }
}
