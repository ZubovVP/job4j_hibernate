package ru.carSales.storage.exceptions;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov
 * Email: Zubov.VP@yandex.ru
 * Version: $Id$
 * Date: 26.04.2020
 */
public class IncorrectDateException extends RuntimeException {
    /**
     * Constructor.
     *
     * @param description - description.
     */
    public IncorrectDateException(String description) {
        super(description);
    }

    /**
     * Constructor.
     */
    public IncorrectDateException() {
        super();
    }
}
