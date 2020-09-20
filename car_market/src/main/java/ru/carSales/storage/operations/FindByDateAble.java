package ru.carSales.storage.operations;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 13.09.2020.
 */
public interface FindByDateAble<E> {

    /**
     * Find elements after or equals date.
     *
     * @param date - date .
     * @return - list of offers.
     */
    List<E> findByDate(LocalDate date);
}