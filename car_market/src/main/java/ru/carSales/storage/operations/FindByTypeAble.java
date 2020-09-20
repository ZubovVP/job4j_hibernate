package ru.carSales.storage.operations;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 13.09.2020.
 */
public interface FindByTypeAble<E> {
        /**
         * Find element use type of element.
         *
         * @param type - type of an element.
         * @return - element.
         */
        List<E> findByType(String type);
    }
