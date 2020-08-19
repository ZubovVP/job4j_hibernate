package ru.carSales.storage.operations;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 25.04.2020.
 */
public interface GetAllElementsAble<E> {

    /**
     * Return list.
     *
     * @return list.
     */
    List <E>getAllElements();
}
