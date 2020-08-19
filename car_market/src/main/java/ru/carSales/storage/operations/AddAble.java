package ru.carSales.storage.operations;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 25.04.2020.
 */
public interface AddAble<E> {

    /**
     * Add an element.
     *
     * @param element - elemnt.
     * @return - element.
     */
    E add(E element);
}
