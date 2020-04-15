package ru.job4j.storage.operations;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 09.04.2020.
 */
public interface FindAllAble<T> {
    /**
     * Find all elements and return those elements.
     *
     * @return - list of elements.
     */
    List<T> findAll();
}
