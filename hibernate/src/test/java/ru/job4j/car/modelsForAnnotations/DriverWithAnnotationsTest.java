package ru.job4j.car.modelsForAnnotations;

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
public class DriverWithAnnotationsTest {
    private SessionFactory factory;
    private Session session = null;
    private Transaction tx = null;
    private DriverWithAnnotations driver;

    @Before
    public void start() {
        this.factory = new Configuration().configure().buildSessionFactory();
        this.driver = new DriverWithAnnotations();

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
        List<DriverWithAnnotations> result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.size(), is(0));
        addElement(this.driver);
        result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.get(0).getName(), is(this.driver.getName()));
        assertThat(result.get(0).getSurname(), is(this.driver.getSurname()));
        deleteElement(result.get(0).getId());
    }


    @Test
    public void testUpdate() {
        addElement(this.driver);
        List<DriverWithAnnotations> list = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
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
        List<DriverWithAnnotations> result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.size(), is(0));
        addElement(this.driver);
        result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.get(0).getName(), is(this.driver.getName()));
        assertThat(result.get(0).getSurname(), is(this.driver.getSurname()));
        deleteElement(result.get(0).getId());
        result = getElementWithoutId(driver.getName(), driver.getSurname());
        assertThat(result.size(), is(0));
    }

    @Test
    public void testAddDriverWithCars() {
        List<DriverWithAnnotations> result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.size(), is(0));
        EngineForAnnotations engine = new EngineForAnnotations();
        engine.setName("testName");
        engine.setType("testType");
        this.tx(
                session -> session.save(engine)
        );

        CarWithAnnotations car = new CarWithAnnotations();
        car.setName("car");
        car.setEngine(engine);
        Set<DriverWithAnnotations> drivers = new HashSet<>();
        drivers.add(this.driver);
        car.setDrivers(drivers);
        this.tx(
                session -> session.save(car)
        );
        CarWithAnnotations carFromDB = this.tx(
                session -> {
                    final Query query = session.createQuery("from CarWithAnnotations where name = :paramName");
                    query.setParameter("paramName", car.getName());
                    return (List<CarWithAnnotations>) query.list();
                }
        ).get(0);
        Set<CarWithAnnotations> cars = new HashSet<>();
        cars.add(car);
        this.driver.setCars(cars);
        result = getElementWithoutId(this.driver.getName(), this.driver.getSurname());
        assertThat(result.get(0).getName(), is(this.driver.getName()));
        assertThat(result.get(0).getSurname(), is(this.driver.getSurname()));
        deleteAllElements(carFromDB.getId(), carFromDB.getEngine().getId());
        deleteElement(result.get(0).getId());
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
                    final Query query = session.createQuery("delete DriverWithAnnotations where id = :id");
                    query.setParameter("id", id);
                    return query.executeUpdate();
                }
        );
    }

    private void addElement(DriverWithAnnotations driver) {
        this.tx(
                session -> session.save(driver)
        );
    }

    private List<DriverWithAnnotations> getElementWithoutId(String name, String surname) {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("from DriverWithAnnotations where name = :paramName AND surname = :paramType");
                    query.setParameter("paramName", name);
                    query.setParameter("paramType", surname);
                    return (List<DriverWithAnnotations>) query.list();
                }
        );
    }

    private void  deleteAllElements(int car_id, int engine_id) {
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete CarWithAnnotations where id = :id");
                    query.setParameter("id", car_id);
                    return query.executeUpdate();
                }
        );
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete EngineForAnnotations where id = :id");
                    query.setParameter("id", engine_id);
                    return query.executeUpdate();
                }
        );
    }
}