package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.util.Iterator;
import java.util.function.Function;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 26.08.2020.
 */
public class CandidateTest {
    private Candidate first;
    private Candidate second;
    private Candidate third;
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();


    @Before
    public void start() {
        this.first = Candidate.of("name1", 1, 100);
        this.second = Candidate.of("name2", 2, 200);
        this.third = Candidate.of("name3", 3, 300);
        this.tx(
                session -> session.save(this.first), this.factory
        );
        this.tx(
                session -> session.save(this.second), this.factory
        );
        this.tx(
                session -> session.save(this.third), this.factory
        );
    }

    @After
    public void finish() {
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Candidate where id = :id");
                    query.setParameter("id", this.first.getId());
                    return query.executeUpdate();
                }
                , this.factory);
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Candidate where id = :id");
                    query.setParameter("id", this.second.getId());
                    return query.executeUpdate();
                }
                , this.factory);
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Candidate where id = :id");
                    query.setParameter("id", this.third.getId());
                    return query.executeUpdate();
                }
                , this.factory);

    }

    @Test
    public void TestGetAllCandidatesFromDb() {
        Iterator itr = this.tx(
                session -> {
                    final Query query = session.createQuery("from Candidate");
                    return query.list().iterator();
                }
                , this.factory);
        assertThat(itr.next(), is(this.first));
        assertThat(itr.next(), is(this.second));
        assertThat(itr.next(), is(this.third));
    }

    @Test
    public void TestGetCandidateUseIdFromDb() {
        Iterator itr = this.tx(
                session -> {
                    final Query query = session.createQuery("from Candidate c where c.id = :cId");
                    query.setParameter("cId", this.second.getId());
                    return query.list().iterator();
                }
                , this.factory);
        assertThat(itr.next(), is(this.second));
    }

    @Test
    public void TestGetCandidateUseNameFromDb() {
        Iterator itr = this.tx(
                session -> {
                    final Query query = session.createQuery("from Candidate c where c.name = :cName");
                    query.setParameter("cName", this.second.getName());
                    return query.list().iterator();
                }
                , this.factory);
        assertThat(itr.next(), is(this.second));
    }

    @Test
    public void TestUpdateCandidateAndCheckFromDb() {
        this.tx(
                session -> {
                    final Query query = session.createQuery("update Candidate c set c.name = :cName, c.salary = :cSalary where id = :id");
                    query.setParameter("cName", "Test");
                    query.setParameter("cSalary", 1.00);
                    query.setParameter("id", this.second.getId());
                    return query.executeUpdate();
                }
                , this.factory);
        Iterator itr = this.tx(
                session -> {
                    final Query query = session.createQuery("from Candidate c where c.id = :cId");
                    query.setParameter("cId", this.second.getId());
                    return query.list().iterator();
                }
                , this.factory);
        Candidate result = (Candidate) itr.next();
        assertThat(result.getName(), is("Test"));
        assertThat(result.getSalary(), is(1d));
        assertThat(result.getId(), is(this.second.getId()));
    }

    @Test
    public void TestDeleteCandidateFromDb() {
        Iterator itr = this.tx(
                session -> {
                    final Query query = session.createQuery("from Candidate");
                    return query.list().iterator();
                }
                , this.factory);
        assertThat(itr.next(), is(this.first));
        assertThat(itr.next(), is(this.second));
        assertThat(itr.next(), is(this.third));
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Candidate where id = :id");
                    query.setParameter("id", this.second.getId());
                    return query.executeUpdate();
                }
                , this.factory);
        itr = this.tx(
                session -> {
                    final Query query = session.createQuery("from Candidate");
                    return query.list().iterator();
                }
                , this.factory);
        assertThat(itr.next(), is(this.first));
        assertThat(itr.next(), is(this.third));
    }

    private <E> E tx(final Function<Session, E> command, SessionFactory factory) {
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
}