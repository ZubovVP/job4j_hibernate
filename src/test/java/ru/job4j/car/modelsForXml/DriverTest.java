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
 * Date: 27.05.2020.
 */
public class DriverTest {
    private SessionFactory factory;
    private Session session = null;
    private Transaction tx = null;
    private Driver driver;

    @Before
    public void start() {
        this.factory = new Configuration().configure().buildSessionFactory();
        this.driver = new Driver();

        this.driver.setName("testName");
        this.driver.setSurname("testSurname");
    }

    @After
    public void finish() {
        if (tx != null) {
            session.close();
        }
    }

    @Test
    public void testAdd() {
        List<Driver> result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.size(), is(0));
        addElement(this.driver);
        result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.get(0).getName(), is(this.driver.getName()));
        assertThat(result.get(0).getSurname(), is(this.driver.getSurname()));
        deleteElement(result.get(0).getId());
    }

    @Test
    public void testAddDriverWithCars() {
        List<Driver> result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.size(), is(0));
        Engine engine = new Engine();
        engine.setName("testName");
        engine.setType("testType");
        this.tx(
                session -> session.save(engine)
        );

        Car car = new Car();
        car.setName("car");
        car.setEngine(engine);
        Set<Driver> drivers = new HashSet<>();
        drivers.add(this.driver);
        car.setDrivers(drivers);
        this.tx(
                session -> session.save(car)
        );
        Car carFromDB = this.tx(
                session -> {
                    final Query query = session.createQuery("from Car where name = :paramName");
                    query.setParameter("paramName", car.getName());
                    return (List<Car>) query.list();
                }
        ).get(0);
        Set<Car> cars = new HashSet<>();
        cars.add(car);
        this.driver.setCars(cars);
        addElement(this.driver);
        result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.get(0).getName(), is(this.driver.getName()));
        assertThat(result.get(0).getSurname(), is(this.driver.getSurname()));
        deleteAllElements(carFromDB.getId(), carFromDB.getEngine().getId());
        deleteElement(result.get(0).getId());
    }


    @Test
    public void testUpdate() {
        addElement(this.driver);
        List<Driver> list = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getName(), is(this.driver.getName()));
        assertThat(list.get(0).getSurname(), is(this.driver.getSurname()));
        this.driver.setId(list.get(0).getId());
        this.driver.setName("testName2");
        this.driver.setSurname("testSurname2");
        this.tx(
                session -> {
                    final Query query = session.createQuery("update DriverWithAnnotations set name = :name, surname = :surname where id = :id");
                    query.setParameter("name", this.driver.getName());
                    query.setParameter("surname", this.driver.getSurname());
                    query.setParameter("id", this.driver.getId());
                    return query.executeUpdate();
                }
        );
        list = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getName(), is(this.driver.getName()));
        assertThat(list.get(0).getSurname(), is(this.driver.getSurname()));
        assertThat(list.get(0).getId(), is(this.driver.getId()));
        deleteElement(this.driver.getId());
    }

    @Test
    public void testDelete() {
        List<Driver> result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.size(), is(0));
        addElement(this.driver);
        result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.get(0).getName(), is(this.driver.getName()));
        assertThat(result.get(0).getSurname(), is(this.driver.getSurname()));
        deleteElement(result.get(0).getId());
        result = getElementWithoutId(driver.getName(), driver.getSurname());
        assertThat(result.size(), is(0));
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
                    final Query query = session.createQuery("delete Driver where id = :id");
                    query.setParameter("id", id);
                    return query.executeUpdate();
                }
        );
    }

    private void addElement(Driver driver) {
        this.tx(
                session -> session.save(driver)
        );
    }

    private List<Driver> getElementWithoutId(String name, String surname) {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("from Driver where name = :paramName AND surname = :paramType");
                    query.setParameter("paramName", name);
                    query.setParameter("paramType", surname);
                    return (List<Driver>) query.list();
                }
        );
    }

    private void deleteAllElements(int car_id, int engine_id) {
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Car where id = :id");
                    query.setParameter("id", car_id);
                    return query.executeUpdate();
                }
        );
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Engine where id = :id");
                    query.setParameter("id", engine_id);
                    return query.executeUpdate();
                }
        );
    }
}