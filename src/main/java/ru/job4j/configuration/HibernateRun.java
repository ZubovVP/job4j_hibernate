package ru.job4j.configuration;

import ru.job4j.configuration.models.User;
import ru.job4j.configuration.storage.DbStore;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 06.04.2020.
 */
public class HibernateRun {
    public static void main(String[] args) {
        User user = new User("Test");
        try (DbStore store = new DbStore()) {
            user.setId(50);
            store.add(user);
            store.find(user.getId());
            user.setName("Test2");
            store.update(user);
            store.delete(user.getId());
            store.findAll();
        }
    }
}
