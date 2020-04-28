package ru.job4j.todoList.storage;

import ru.job4j.todoList.models.Item;
import ru.job4j.todoList.storage.exceptions.IncorrectDateException;
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
public class ValidateService implements Actions<Item>, SpecialActions<Item> {
    private final static DBStorage<Item> STORAGE = DBStorage.getInstance();

    /**
     * Check element if correct, then call DB.add().
     *
     * @param element - element.
     * @return - result.
     */
    @Override
    public boolean add(Item element) {
        boolean result;
        if (element.getDescription() != null && !element.getDescription().equals("")) {
            result = STORAGE.add(element);
        } else {
            throw new IncorrectDateException("Please, fill the description");
        }
        return result;
    }

    /**
     * Call STORAGE.getAllElements().
     *
     * @return - List of elements.
     */
    @Override
    public List<Item> getAllElements() {
        return STORAGE.getAllElements();
    }

    /**
     * Call STORAGE.delete(id).
     *
     * @param id - id of an element.
     * @return - result.
     */
    @Override
    public boolean delete(int id) {
        return STORAGE.delete(id);
    }

    /**
     * Call STORAGE.getAllIsNotDoneElements().
     *
     * @return - list of elements with done is false.
     */
    @Override
    public List<Item> getAllIsNotDoneElements() {
        return STORAGE.getAllIsNotDoneElements();
    }

    /**
     * Update an element from DB.
     *
     * @param element - item.
     * @return - result.
     */
    @Override
    public boolean update(Item element) {
        if (element.getId() != 0 && element.getDescription() != null && !element.getDescription().equals("") && element.getCreated() != null) {
            STORAGE.update(element);
        } else {
            throw new IncorrectDateException("Wrong element for update");
        }
        return true;
    }

    /**
     * Find element by id.
     *
     * @param id - id of an item.
     * @return- Item
     */
    @Override
    public Item find(int id) {
        Item result;
        if (id == 0) {
            throw new IncorrectDateException("Wrong id for find element");
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
