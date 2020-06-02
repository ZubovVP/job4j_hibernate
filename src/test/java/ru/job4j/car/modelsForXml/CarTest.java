package ru.job4j.car.modelsForXml;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 28.05.2020.
 */
public class CarTest {
    private SessionFactory factory;
    private Session session = null;
    private Transaction tx = null;
    private Car car = new Car();
    private Driver driver = new Driver();
    private Engine engine = new Engine();

    @Before
    public void start() {
        this.factory = new Configuration().configure().buildSessionFactory();
        this.engine.setType("testType");
        this.engine.setName("testName");
        this.tx(
                session -> session.save(this.engine)
        );

        this.engine = this.tx(
                session -> {
                    final Query query = session.createQuery("from Engine where name = :paramName AND type = :paramType");
                    query.setParameter("paramName", this.engine.getName());
                    query.setParameter("paramType", this.engine.getType());
                    return (List<Engine>) query.list();
                }
        ).get(0);
        this.car.setEngine(this.engine);
        this.driver.setName("testName");
        this.driver.setSurname("testSurname");
        this.tx(
                session -> session.save(this.driver)
        );

       this.driver= this.tx(
                session -> {
                    final Query query = session.createQuery("from Driver where name = :paramName AND surname = :paramSurname");
                    query.setParameter("paramName", driver.getName());
                    query.setParameter("paramSurname", driver.getSurname());
                    return (List<Driver>) query.list();
                }
        ).get(0);


        this.car.setName("testName");
        Set<Driver> drivers = new HashSet<>();
        drivers.add(this.driver);
        this.car.setDrivers(drivers);
    }

    @After
    public void finish() {
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Engine where id = :id");
                    query.setParameter("id", this.engine.getId());
                    return query.executeUpdate();
                }
        );
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Driver where id = :id");
                    query.setParameter("id", this.driver.getId());
                    return query.executeUpdate();
                }
        );
        if (tx != null) {
            session.close();
        }
    }

    @Test
    public void testAdd() {
        addElement(this.car);
        Car fromDBCar = getElementWithoutId(this.car.getName()).get(0);
        assertThat(fromDBCar.getName(), is(this.car.getName()));
        assertTrue(fromDBCar.getId() != 0);
        deleteElement(fromDBCar.getId());
    }


    @Test
    public void testDelete() {
        addElement(this.car);
        List<Car> fromDBCar = getElementWithoutId(this.car.getName());
        assertThat(fromDBCar.size(), is(1));
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Car where name = :name");
                    query.setParameter("name", this.car.getName());
                    return query.executeUpdate();
                }
        );
        fromDBCar = getElementWithoutId(this.car.getName());
        assertThat(fromDBCar.size(), is(0));
    }

    @Test
    public void testUpdate(){
        addElement(this.car);
        List<Car> list = getElementWithoutId(this.car.getName());
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getName(), is(this.car.getName()));
        this.car.setId(list.get(0).getId());
        this.car.setName("testName2");
        this.tx(
                session -> {
                    final Query query = session.createQuery("update Car set name = :name where id = :id");
                    query.setParameter("name", this.car.getName());
                    query.setParameter("id", this.car.getId());
                    return query.executeUpdate();
                }
        );
        list = getElementWithoutId(this.car.getName());
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getName(), is(this.car.getName()));
        assertThat(list.get(0).getId(), is(this.car.getId()));
        deleteElement(this.car.getId());
    }


    private <E> E tx(final Function<Session, E> command) {
        final Session session = factory.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            E rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    private void deleteElement(int id) {
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Car where id = :id");
                    query.setParameter("id", id);
                    return query.executeUpdate();
                }
        );
    }

    private void addElement(Car car) {
        this.tx(
                session -> session.save(car)
        );
    }

    private List<Car> getElementWithoutId(String name) {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("from Car where name = :paramName");
                    query.setParameter("paramName", name);
                    return (List<Car>) query.list();
                }
        );
    }
}