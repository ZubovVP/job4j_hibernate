package ru.carSales.storage.operations;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 29.06.2020.
 */
public interface FindUserAble<E> {

    /**
     * Find user.
     *
     * @param email - e-mail.
     * @param password - password.
     * @return - element.
     */
    E findUser(String email, String password);
}
