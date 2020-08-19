package ru.carSales.storage.operations;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 22.04.2020.
 */
public interface DeleteAble {

    /**
     * Delete an element.
     *
     * @param id - id.
     * @return - result.
     */
    boolean delete(int id);
}
