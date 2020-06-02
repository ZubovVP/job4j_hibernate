package ru.job4j.car.modelsForXml;

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
 * Date: 27.05.2020.
 */
public class EngineTest {
    private SessionFactory factory;
    private Session session = null;
    private Transaction tx = null;
    private Engine engine;

    @Before
    public void start() {
        this.factory = new Configuration().configure().buildSessionFactory();
        this.engine = new Engine();
        this.engine.setName("testName");
        this.engine.setType("testType");
    }

    @After
    public void finish() {
        if (tx != null) {
            session.close();
        }
    }

    @Test
    public void testAdd() {
        List<Engine> result = getElementWithoutId(this.engine.getName(), this.engine.getType());
        assertThat(result.size(), is(0));
        addElement(this.engine);
        result = getElementWithoutId(this.engine.getName(), this.engine.getType());
        assertThat(result.get(0).getName(), is(this.engine.getName()));
        assertThat(result.get(0).getType(), is(this.engine.getType()));
        deleteElement(result.get(0).getId());
    }


    @Test
    public void testUpdate() {
        addElement(this.engine);
        List<Engine> list = getElementWithoutId(this.engine.getName(), this.engine.getType());
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getName(), is(this.engine.getName()));
        assertThat(list.get(0).getType(), is(this.engine.getType()));
        this.engine.setId(list.get(0).getId());
        this.engine.setName("testName2");
        this.engine.setType("testType2");
        this.tx(
                session -> {
                    final Query query = session.createQuery("update Engine set name = :name, type = :type where id = :id");
                    query.setParameter("name", this.engine.getName());
                    query.setParameter("type", this.engine.getType());
                    query.setParameter("id", this.engine.getId());
                    return query.executeUpdate();
                }
        );
        list = getElementWithoutId(this.engine.getName(), this.engine.getType());
        assertThat(list.size(), is(1));
        assertThat(list.get(0).getName(), is(this.engine.getName()));
        assertThat(list.get(0).getType(), is(this.engine.getType()));
        assertThat(list.get(0).getId(), is(this.engine.getId()));
        deleteElement(this.engine.getId());
    }

    @Test
    public void testDelete() {
        List<Engine> result = getElementWithoutId(this.engine.getName(), this.engine.getType());
        assertThat(result.size(), is(0));
        addElement(this.engine);
        result = getElementWithoutId(this.engine.getName(), this.engine.getType());
        assertThat(result.get(0).getName(), is(this.engine.getName()));
        assertThat(result.get(0).getType(), is(this.engine.getType()));
        deleteElement(result.get(0).getId());
        result = getElementWithoutId(engine.getName(), engine.getType());
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
                    final Query query = session.createQuery("delete Engine where id = :id");
                    query.setParameter("id", id);
                    return query.executeUpdate();
                }
        );
    }

    private void addElement(Engine element) {
        this.tx(
                session -> session.save(element)
        );
    }

    private List<Engine> getElementWithoutId(String name, String type) {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("from Engine where name = :paramName AND type = :paramType");
                    query.setParameter("paramName", name);
                    query.setParameter("paramType", type);
                    return (List<Engine>) query.list();
                }
        );
    }
}