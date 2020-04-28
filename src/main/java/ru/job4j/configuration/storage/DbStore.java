package ru.job4j.configuration.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import ru.job4j.configuration.models.User;
import ru.job4j.configuration.storage.operations.*;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 09.04.2020.
 */
public class DbStore implements AddAble<User>, FindAble<User>, UpdateAble<User>, DeleteAble, FindAllAble<User>, AutoCloseable {
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private static final Logger LOGGER = LogManager.getLogger(DbStore.class.getName());


    /**
     * Add element it the Db.
     *
     * @param element - element.
     * @return - result.
     */
    @Override
    public boolean add(User element) {
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
     * Delete element from DB.
     *
     * @param id - id of element.
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
            session.createQuery("delete User where id =" + id).executeUpdate();
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
     * Find element by id.
     *
     * @param id - id of element.
     * @return - element.
     */
    @Override
    public User find(int id) {
        Session session = null;
        Transaction tx = null;
        List<User> list = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            list = session.createQuery("from User i where i.id = " + id).list();
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
        return list != null ? list.get(0) : null;
    }

    /**
     * Update element.
     *
     * @param element - element.
     * @return - result.
     */
    @Override
    public boolean update(User element) {
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
            LOGGER.error("Failed to update an element to Database. Element = {}.", element);
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
     * Return all elements from DB.
     *
     * @return - list of elements.
     */
    @Override
    public List<User> findAll() {
        Session session = null;
        Transaction tx = null;
        List list = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            list = session.createQuery("from User i").list();
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
     * Close DbStore of resources.
     */
    @Override
    public void close() {
        this.factory.close();
    }
}
