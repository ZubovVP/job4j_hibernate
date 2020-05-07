package ru.job4j.todoList.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.job4j.todoList.models.Item;
import ru.job4j.todoList.storage.operations.Actions;
import ru.job4j.todoList.storage.operations.SpecialActions;

import java.util.List;
import java.util.function.Function;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 22.04.2020.
 */
public class DBStorage<I> implements Actions<Item>, SpecialActions<Item> {
    private static final Logger LOGGER = LogManager.getLogger(DBStorage.class.getName());
    private static DBStorage<Item> ourInstance = new DBStorage<>();
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();


    public static DBStorage<Item> getInstance() {
        return ourInstance;
    }

    private DBStorage() {
    }

    /**
     * Add an element into DB.
     *
     * @param element - element.
     * @return - result.
     */
    @Override
    public boolean add(Item element) {
        boolean result = false;
        try {
            this.tx(
                    session -> session.save(element)
            );
            result = true;
        } catch (Exception e) {
            LOGGER.error("Failed to add element to the Database. Element = {}.", element);
        }
        return result;
    }

    /**
     * Get all elements.
     *
     * @return - list of elements.
     */
    @Override
    public List<Item> getAllElements() {
        return this.tx(
                session -> session.createQuery("from Item").list()
        );
    }

    /**
     * Delete an element from DB.
     *
     * @param id - id of an element.
     * @return - result.
     */
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            this.tx(
                    session -> {
                        final Query query = session.createQuery("delete Item where id = :id");
                        query.setParameter("id", id);
                        return query.executeUpdate();
                    }
            );
            result = true;
        } catch (Exception e) {
            LOGGER.error("Failed to delete element to the Database. Id = {}.", id);
        }
        return result;
    }

    /**
     * Get all elements with done is false.
     *
     * @return - result.
     */
    @Override
    public List<Item> getAllIsNotDoneElements() {
        return this.tx(
                session -> session.createQuery("from Item i where i.done = FALSE").list());
    }

    /**
     * Update an element from DB.
     *
     * @param item - element.
     * @return - result.
     */
    @Override
    public boolean update(Item item) {
        boolean result = false;
        try {
            this.tx(
                    session -> {
                        final Query query = session.createQuery("update Item set description = :desc, done = :done where id = :id");
                        query.setParameter("desc", item.getDescription());
                        query.setParameter("done", item.isDone());
                        query.setParameter("id", item.getId());
                        return query.executeUpdate();
                    }
            );
            result = true;
        } catch (Exception e) {
            LOGGER.error("Failed to update element to the Database. Element = {}.", item);
        }
        return result;
    }

    /**
     * Find element by id.
     *
     * @param id - id of an element.
     * @return - element.
     */
    @Override
    public Item find(int id) {

        List<Item> list = this.tx(
                session -> session.createQuery("from Item i where i.id = " + id).list()
        );
        return list != null ? list.get(0) : null;
    }

    /**
     * Execute operation.
     *
     * @param command - command.
     * @param <E> - element.
     * @return - result.
     */
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

    /**
     * Close SessionFactory.
     */
    @Override
    public void close() {
        this.factory.close();
    }
}
