package ru.carSales.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.carSales.models.UserForSales;
import ru.carSales.storage.operations.Actions;
import ru.carSales.storage.operations.FindUserAble;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 10.08.2020.
 */
public class DBStorage_user<I> implements Actions<UserForSales>, FindUserAble {
    private static final Logger LOGGER = LogManager.getLogger(DBStorage_car.class.getName());
    private static DBStorage_user<UserForSales> ourInstance = new DBStorage_user<>();
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();

    public static DBStorage_user<UserForSales> getInstance() {
        return ourInstance;
    }

    private DBStorage_user() {
    }

    /**
     * Add an element into DB.
     *
     * @param element - element.
     * @return - result.
     */
    public UserForSales add(UserForSales element) {
        try {
            this.tx(
                    session -> session.save(element)
                    , this.factory);
        } catch (Exception e) {
            LOGGER.error("Failed to add element to the Database. Element = {}.", element);
        }
        return element;
    }

    /**
     * Get all elements.
     *
     * @return - list of elements.
     */
    @Override
    public List<UserForSales> getAllElements() {
        return this.tx(
                session -> session.createQuery("from UserForSales").list()
                , this.factory);
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
                        final Query query = session.createQuery("delete UserForSales where id = :id");
                        query.setParameter("id", id);
                        return query.executeUpdate();
                    }
                    , this.factory);
            result = true;
        } catch (Exception e) {
            LOGGER.error("Failed to delete element to the Database. Id = {}.", id);
        }
        return result;
    }

    /**
     * Update an element from DB.
     *
     * @param element - element.
     * @return - result.
     */
    @Override
    public boolean update(UserForSales element) {
        boolean result = false;
        try {
            this.tx(
                    session -> {
                        final Query query = session.createQuery("update UserForSales set name = :name, surname = :surname, email = :email, telephone = :telephone, password = :password where id = :id");
                        query.setParameter("name", element.getName());
                        query.setParameter("surname", element.getSurname());
                        query.setParameter("email", element.getEmail());
                        query.setParameter("telephone", element.getTelephone());
                        query.setParameter("password", element.getPassword());
                        query.setParameter("id", element.getId());
                        return query.executeUpdate();
                    }
                    , this.factory);
            result = true;
        } catch (Exception e) {
            LOGGER.error("Failed to update element to the Database. Element = {}.", element);
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
    public UserForSales find(int id) {
        List<UserForSales> list = this.tx(
                session -> session.createQuery("from UserForSales i where i.id = " + id).list()
                , this.factory);
        return list != null ? list.get(0) : null;
    }

    /**
     * Find user.
     *
     * @param email    - e-mail.
     * @param password - password.
     * @return
     */
    @Override
    public UserForSales findUser(String email, String password) {
        List<UserForSales> list = this.tx(
                session -> {
                    final Query query = session.createQuery("FROM UserForSales WHERE email = :email AND password = :password");
                    query.setParameter("email", email);
                    query.setParameter("password", password);
                    return query.list();
                }
                , this.factory);
        return list.size() != 0 ? list.get(0) : null;
    }

    /**
     * Close SessionFactory.
     */
    @Override
    public void close() {
        this.factory.close();
    }

}
