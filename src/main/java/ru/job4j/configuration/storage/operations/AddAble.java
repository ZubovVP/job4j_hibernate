package ru.job4j.configuration.storage.operations;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 09.04.2020.
 */
public interface AddAble<T> {
    /**
     * Add an element.
     *
     * @param element - element.
     * @return - result.
     */
    boolean add(T element);
}
