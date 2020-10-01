package ru.carSales.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.carSales.models.Offer;
import ru.carSales.storage.operations.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 10.08.2020.
 */
public class DBStorage_car<I> implements Actions<Offer>, OptionalActitions<Offer> {
    private static final Logger LOGGER = LogManager.getLogger(DBStorage_car.class.getName());
    private static DBStorage_car<Offer> ourInstance = new DBStorage_car<>();
    private final SessionFactory factory = new Configuration().configure().buildSessionFactory();


    public static DBStorage_car<Offer> getInstance() {
        return ourInstance;
    }

    private DBStorage_car() {
    }

    /**
     * Add an element into DB.
     *
     * @param element - element.
     * @return - result.
     */
    public Offer add(Offer element) {
        try {
            this.tx(
                    session -> session.save(element), this.factory
            );
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
    public List<Offer> getAllElements() {
        return this.tx(
                session -> session.createQuery("SELECT distinct ff from Offer ff join fetch  ff.user us").list(), this.factory
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
                        final Query query = session.createQuery("delete Offer where id = :id");
                        query.setParameter("id", id);
                        return query.executeUpdate();
                    }
                    , this.factory);
            result = true;
        } catch (Exception e) {
            LOGGER.error("Failed to delete element from the Database. id = {}.", id);
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
    public boolean update(Offer element) {
        boolean result = false;
        try {
            this.tx(
                    session -> {
                        final Query query = session.createQuery("update Offer set mark = :mark, category = :category, type_body = :type_body, transmission = :transmission, year_of_issue = :year_of_issue, user_id = :user_id, status =:status, price=:price where id = :id");
                        query.setParameter("mark", element.getMark());
                        query.setParameter("category", element.getCategory());
                        query.setParameter("type_body", element.getTypeBody());
                        query.setParameter("transmission", element.getTransmission());
                        query.setParameter("year_of_issue", element.getYearOfIssue());
                        query.setParameter("user_id", element.getUser().getId());
                        query.setParameter("status", element.getStatus());
                        query.setParameter("price", element.getPrice());
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
    public Offer find(int id) {
        List<Offer> list = this.tx(
                session -> session.createQuery("from Offer i where i.id = " + id).list(), this.factory
        );
        return list != null ? list.get(0) : null;
    }

    /**
     * Find element use date.
     *
     * @param start  - start date.
     * @param finish - finish date.
     * @return - list of offers.
     */
    @Override
    public List<Offer> findByDate(LocalDate start, LocalDate finish) {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("SELECT DISTINCT ff FROM Offer ff " +
                            "JOIN FETCH ff.user u " +
                            "WHERE ff.date >= :start AND ff.date <= :finish", Offer.class);
                    query.setParameter("start", start);
                    query.setParameter("finish", finish);
                    return query.list();
                }
                , this.factory);
    }

    /**
     * Find element with the this type.
     *
     * @param type - type of an element.
     * @return - list of offers.
     */
    @Override
    public List<Offer> findByType(String type) {
        return this.tx(
                session -> {
                    final Query query = session.createQuery("SELECT DISTINCT ff FROM Offer ff " +
                            "JOIN FETCH ff.user u " +
                            "WHERE ff.typeBody = :type", Offer.class);
                    query.setParameter("type", type);
                    return query.list();
                }
                , this.factory);
    }

    /**
     * Find elements with picture.
     *
     * @return - list of offers.
     */
    @Override
    public List<Offer> findWithPicture() {
        return this.tx(
                session -> session.createQuery("SELECT distinct ff from Offer ff join fetch  ff.user us where ff.dir_photos != NULL").list(), this.factory
        );
    }

    /**
     * Close SessionFactory.
     */
    @Override
    public void close() {
        this.factory.close();
    }
}
