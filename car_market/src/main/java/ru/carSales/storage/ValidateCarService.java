package ru.carSales.storage;

import ru.carSales.storage.operations.Actions;
import ru.carSales.models.Offer;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 22.04.2020.
 */
public class ValidateCarService implements Actions<Offer> {
    private final static DBStorage_car<Offer> STORAGE = DBStorage_car.getInstance();
    private static ValidateCarService ourInstance = new ValidateCarService();


    public static ValidateCarService getInstance() {
        return ourInstance;
    }

    private ValidateCarService() {
    }

    /**
     * Check element if correct, then call DB.add().
     *
     * @param element - element.
     * @return - result.
     */
    @Override
    public Offer add(Offer element) {
        if (element.getUser().getId() > 0) {
            if (element.getStatus() == null) {
                element.setStatus(true);
            }
            return STORAGE.add(element);
        } else {
            throw new IncorrectDateException("Wrong for add offer");
        }
    }

    /**
     * Call STORAGE.getAllElements().
     *
     * @return - List of elements.
     */
    @Override
    public List<Offer> getAllElements() {
        List<Offer> result = STORAGE.getAllElements();
        for (Offer offer : result) {
            if (!offer.getStatus()) {
                result.remove(offer);
            }
        }
        return result;
    }

    /**
     * Call STORAGE.delete(id).
     *
     * @param id - id of an element.
     * @return - result.
     */
    @Override
    public boolean delete(int id) {
        boolean result;
        if (id > 0) {
            result = STORAGE.delete(id);
        } else {
            throw new IncorrectDateException("Wrong id for delete offer");
        }
        return result;
    }

    /**
     * Update an element from DB.
     *
     * @param element - item.
     * @return - result.
     */
    @Override
    public boolean update(Offer element) {
        if (element.getId() > 0 && element.getPrice() > 0 && element.getUser() != null && element.getUser().getId() > 0) {
            return STORAGE.update(element);
        } else {
            throw new IncorrectDateException("Wrong id of offer for update");
        }
    }

    /**
     * Find element by id.
     *
     * @param id - id of an Offer.
     * @return- Offer
     */
    @Override
    public Offer find(int id) {
        Offer result;
        if (id <= 0) {
            throw new IncorrectDateException("Wrong id for find offer");
        } else {
            result = STORAGE.find(id);
        }
        return result;
    }

    /**
     * Close DB.
     */
    @Override
    public void close() {
        STORAGE.close();
    }
}
