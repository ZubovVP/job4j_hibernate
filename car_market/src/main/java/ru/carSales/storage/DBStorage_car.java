package ru.carSales.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ru.carSales.models.Offer;
import ru.carSales.models.UserForSales;
import ru.carSales.storage.operations.Actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 10.08.2020.
 */
public class DBStorage_car<I> implements Actions<Offer> {
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
        return getOffer(element);
    }

    /**
     * Get all elements.
     *
     * @return - list of elements.
     */
    @Override
    public List<Offer> getAllElements() {
        List list = this.tx(
                session -> session.createQuery("SELECT car.id, car.mark, car.yearOfIssue, car.category, car.transmission, car.typeBody,car.dir_photos, car.price, user.id, user.name, user.telephone, car.status from Offer car left join car.user user").list(),
                this.factory
        );
        List<Offer> result = new ArrayList<>();
        for (Object o : list) {
            Offer car = new Offer();
            Object[] rows = (Object[]) o;
            car.setId((Integer) rows[0]);
            car.setMark((String) rows[1]);
            car.setYearOfIssue((Integer) rows[2]);
            car.setCategory((String) rows[3]);
            car.setTransmission((String) rows[4]);
            car.setTypeBody((String) rows[5]);
            car.setDir_photos(String.format("%s%s", "images/", rows[6]));
            car.setPrice((Integer) rows[7]);
            UserForSales user = new UserForSales();
            user.setId((Integer) rows[8]);
            user.setName((String) rows[9]);
            user.setTelephone((String) rows[10]);
            car.setUser(user);
            car.setStatus((Boolean) rows[11]);
            result.add(car);
        }
        return result;
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
     * Return an offer.
     *
     * @param element - element.
     * @return - offer
     */
    private Offer getOffer(Offer element) {
        List<Offer> list = this.tx(
                session -> {
                    final Query query = session.createQuery("FROM Offer WHERE mark = :mark AND category = :category AND typeBody = :typeBody AND transmission = :transmission AND yearOfIssue = :yearOfIssue");
                    query.setParameter("mark", element.getMark());
                    query.setParameter("category", element.getCategory());
                    query.setParameter("typeBody", element.getTypeBody());
                    query.setParameter("transmission", element.getTransmission());
                    query.setParameter("yearOfIssue", element.getYearOfIssue());
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
