package ru.carSales.storage;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 16.08.2020.
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
