package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;

import static org.hamcrest.core.Is.is;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 02.09.2020.
 */
public class BaseOfVacancyTest {
    private Vacancy vacancy1;
    private Vacancy vacancy2;
    private BaseOfVacancy baseOfVacancy;
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    @Before
    public void start() {
        this.vacancy1 = Vacancy.of("descTest1", 100.00);
        this.vacancy2 = Vacancy.of("descTest2", 200.00);
        this.baseOfVacancy = BaseOfVacancy.of();
        this.baseOfVacancy.addVacancy(this.vacancy1);
        this.baseOfVacancy.addVacancy(this.vacancy2);
        this.tx(
                session -> session.save(this.baseOfVacancy), this.factory
        );
    }

    @After
    public void finish() {
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete BaseOfVacancy ");
                    return query.executeUpdate();
                }
                , this.factory);
        this.tx(
                session -> {
                    final Query query = session.createQuery("delete Vacancy");
                    return query.executeUpdate();
                }
                , this.factory);
    }

    @Test
    public void saveBaseInDbAndCheckVacancy(){
        this.tx(
                session -> session.save(this.baseOfVacancy), this.factory
        );
        List<BaseOfVacancy> result = this.tx(
                session -> {
                    final Query query = session.createQuery("SELECT DISTINCT b FROM BaseOfVacancy b " +
                            "JOIN FETCH b.listOfVacancies l " +
                            "WHERE b.id =:baseId", BaseOfVacancy.class);
                    query.setParameter("baseId", this.baseOfVacancy.getId());
                    return query.list();
                }
                , this.factory);
        Assert.assertThat(result.size(), is(1));
        Assert.assertThat(result.get(0).getListOfVacancies().get(0), is(this.vacancy1));
        Assert.assertThat(result.get(0).getListOfVacancies().get(1), is(this.vacancy2));
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