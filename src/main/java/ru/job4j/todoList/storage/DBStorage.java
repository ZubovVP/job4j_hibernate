package ru.job4j.todoList.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.todoList.models.Item;
import ru.job4j.todoList.storage.operations.Actions;
import ru.job4j.todoList.storage.operations.SpecialActions;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 22.04.2020.
 */
public class DBStorage<E> implements Actions<E>, SpecialActions<E> {
    private static final Logger LOGGER = LogManager.getLogger(DBStorage.class.getName());
    private static DBStorage ourInstance = new DBStorage();
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();


    public static DBStorage getInstance() {
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
    public boolean add(E element) {
        Session session = null;
        Transaction tx = null;
        boolean result = false;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.save(element);
            result = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOGGER.error("Failed to add element to the Database. Element = {}.", element);
        } finally {
            if (tx != null) {
                tx.commit();
            }
            if (tx != null) {
                session.close();
            }
        }
        return result;
    }

    /**
     * Get all elements.
     *
     * @return - list of elements.
     */
    @Override
    public List<E> getAllElements() {
        Session session = null;
        Transaction tx = null;
        List list = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Item i").list();
        } catch (Exception e) {
            LOGGER.error("Failed to findAll elements in the Database.");
        } finally {
            if (tx != null) {
                tx.commit();
            }
            if (tx != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Delete an element from DB.
     *
     * @param id - id of an element.
     * @return - result.
     */
    @Override
    public boolean delete(int id) {
        Session session = null;
        Transaction tx = null;
        boolean result = false;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.createQuery("delete Item where id =" + id).executeUpdate();
            result = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOGGER.error("Failed to delete element from the Database. Id = {}.", id);
        } finally {
            if (tx != null) {
                tx.commit();
            }
            if (tx != null) {
                session.close();
            }
        }
        return result;
    }

    /**
     * Get all elements with done is false.
     *
     * @return - result.
     */
    @Override
    public List<E> getAllIsNotDoneElements() {
        Session session = null;
        Transaction tx = null;
        List<E> list = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Item i where i.done = FALSE").list();
        } catch (Exception e) {
            LOGGER.error("Failed to find elements where done = false.");
        } finally {
            if (tx != null) {
                tx.commit();
            }
            if (tx != null) {
                session.close();
            }
        }
        return list;
    }

    /**
     * Update an element from DB.
     *
     * @param element - element.
     * @return - result.
     */
    @Override
    public boolean update(E element) {
        Session session = null;
        Transaction tx = null;
        boolean result = false;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.update(element);
            result = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOGGER.error("Failed to update an element into Database. Element = {}.", element);
        } finally {
            if (tx != null) {
                tx.commit();
            }
            if (tx != null) {
                session.close();
            }
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
    public E find(int id) {
        Session session = null;
        Transaction tx = null;
        List<Item> list = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            list = session.createQuery("from Item i where i.id = " + id).list();
        } catch (Exception e) {
            LOGGER.error("Failed to find element. Id = {}.", id);
        } finally {
            if (tx != null) {
                tx.commit();
            }
            if (tx != null) {
                session.close();
            }
        }
        return list != null ? (E) list.get(0) : null;
    }

    /**
     * Close SessionFactory.
     */
    @Override
    public void close() {
        this.factory.close();
    }
}
