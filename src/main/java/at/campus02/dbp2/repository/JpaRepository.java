package at.campus02.dbp2.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaRepository implements CustomerRepository{

    private EntityManager manager;
    public JpaRepository() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");
        manager = factory.createEntityManager();
    }
    //kein try catch weil Persistance macht das für uns

    @Override
    public void create(Customer customer) {
        manager.getTransaction().begin();
        manager.persist(customer);
        manager.getTransaction().commit();
    }

    @Override
    public Customer read(String email) {
        //"leert" Persistance Context (Cache) aus, keine managed entities mehr
        //wollen den Datensatz aus der DB und nicht irgendeinen der zuvor gecacht wurde
        manager.clear();
        return manager.find(Customer.class, email);
    }

    @Override
    public void update(Customer customer) {

        manager.getTransaction().begin();
        manager.merge(customer);
        manager.getTransaction().commit();
    }

    @Override
    public void delete(Customer customer) {

        manager.getTransaction().begin();
        manager.remove(customer);
        manager.getTransaction().commit();
    }
}
