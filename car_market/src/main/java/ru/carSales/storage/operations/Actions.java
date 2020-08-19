package ru.carSales.storage.operations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Closeable;
import java.util.function.Function;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 25.04.2020.
 */
public interface Actions<E> extends AddAble<E>, GetAllElementsAble<E>, UpdateAble<E>, FindAble<E>, DeleteAble, Closeable {
    /**
     * Execute operation.
     *
     * @param command - command.
     * @param <E>     - element.
     * @return - result.
     */
    default <E> E tx(final Function<Session, E> command, SessionFactory factory) {
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
