package ru.job4j.todoList.storage.operations;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 09.04.2020.
 */
public interface UpdateAble<T> {
    /**
     * Update an element.
     *
     * @param element - element.
     * @return - result.
     */
    boolean update(T element);
}
