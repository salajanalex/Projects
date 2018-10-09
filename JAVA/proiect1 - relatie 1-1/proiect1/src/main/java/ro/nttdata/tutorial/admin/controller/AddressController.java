package ro.nttdata.tutorial.admin.controller;

import ro.nttdata.tutorial.admin.entity.Address;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateful
public class AddressController {

    @PersistenceContext(unitName = "testsProject")
    private EntityManager entityManager;

    /**
     * adding new Address to DB
     * @param address
     */
    public void addAddress(Address address) {
        entityManager.persist(address);
    }

    /**
     * Remove address by id
     * @param id
     */
    public void deleteAddress(int id) {
        Query query = entityManager.createQuery(Address.DELETE_ADDRESS_QUERY);
        query.setParameter("id",id);
        query.executeUpdate();
    }

    /**
     * find Address by ID
     * @param id
     * @return
     */
    public Address getAddressById(int id) {
        return entityManager.find(Address.class, id);
    }

    /**
     * finds all Addresses from DB
     * @return
     */
    public List<Address> getAllAddresses() {
        Query query = entityManager.createQuery(Address.SELECT_ADDRESSES_QUERY);
        return query.getResultList();
    }

    /**
     * Update Address
     * @param address
     */
    public void updateAddress(Address address) {
        entityManager.merge(address);
    }
}
