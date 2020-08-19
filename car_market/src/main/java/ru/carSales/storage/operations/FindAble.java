package ru.carSales.storage.operations;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 09.04.2020.
 */
public interface FindAble<E> {
    /**
     * Find element use id.
     *
     * @param id - id of an element.
     * @return - element.
     */
    E find(int id);
}
