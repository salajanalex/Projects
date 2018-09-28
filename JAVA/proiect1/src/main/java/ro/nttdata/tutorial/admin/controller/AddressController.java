package ro.nttdata.tutorial.admin.controller;

import ro.nttdata.tutorial.admin.entity.Address;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;

@Stateful
public class AddressController {

    @PersistenceContext(unitName = "testsProject")
    private EntityManager entityManager;

    public void addAddress(Address address) {
        entityManager.persist(address);
    }

    public void deleteAddress(int id) {
        Query query = entityManager.createQuery("DELETE a from Address a WHERE a.idaddress = :id");
        query.setParameter("id",id);
        query.executeUpdate();
    }

    public Address getAddressById(int id) {
        return entityManager.find(Address.class, id);
    }

    public List<Address> getAllAddresses() {
        Query query = entityManager.createQuery("SELECT a FROM Address a");
        return query.getResultList();
    }

    public void updateAddress(Address address) {
        entityManager.merge(address);
    }
}
