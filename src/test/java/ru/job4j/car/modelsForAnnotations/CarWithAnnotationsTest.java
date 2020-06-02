package ru.job4j.car.modelsForAnnotations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 24.05.2020.
 */
public class CarWithAnnotationsTest {
    private SessionFactory factory;
    private Session session = null;
    private Transaction tx = null;
    private CarWithAnnotations car = new CarWithAnnotations();
    private EngineForAnnotations engine = new EngineForAnnotations();

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
                    final Query query = session.createQuery("from EngineForAnnotations where name = :paramName AND type = :paramType");
                    query.setParameter("paramName", this.engine.getName());
                    query.setParameter("paramType", this.engine.getType());
                    return (List<EngineForAnnotations>) query.list();
                }
        ).get(0);
        this.car.setEngine(this.engine);
        this.car.setName("CarName");
    }

    @After
    public void finish() {
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete EngineForAnnotations where id = :id");
                    query.setParameter("id", this.engine.getId());
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
        CarWithAnnotations fromDBCar = getElementWithoutId(this.car.getName()).get(0);
        assertThat(fromDBCar.getName(), is(this.car.getName()));
        assertTrue(fromDBCar.getId() != 0);
        deleteElement(fromDBCar.getId());
    }


    @Test
    public void testDelete() {
        addElement(this.car);
        List<CarWithAnnotations> fromDBCar = getElementWithoutId(this.car.getName());
        assertThat(fromDBCar.size(), is(1));
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete CarWithAnnotations where name = :name");
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
        List<CarWithAnnotations> list = getElementWithoutId(this.car.getName());
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getName(), is(this.car.getName()));
        this.car.setId(list.get(0).getId());
        this.car.setName("testName2");
        this.tx(
                session -> {
                    final Query query = session.createQuery("update CarWithAnnotations set name = :name where id = :id");
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
                    final Query query = session.createQuery("delete CarWithAnnotations where id = :id");
                    query.setParameter("id", id);
                    return query.executeUpdate();
                }
        );
    }

    private void addElement(CarWithAnnotations car) {
        this.tx(
                session -> session.save(car)
        );
    }

    private List<CarWithAnnotations> getElementWithoutId(String name) {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("from CarWithAnnotations where name = :paramName");
                    query.setParameter("paramName", name);
                    return (List<CarWithAnnotations>) query.list();
                }
        );
    }
}