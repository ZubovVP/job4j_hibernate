package ru.job4j.configuration.storage;

import org.junit.Test;
import ru.job4j.configuration.models.User;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 12.04.2020.
 */
public class DbStoreTest {

    @Test
    public void testAddElementAndCheckElementFromDb() {
        try (DbStore dbStore = new DbStore()) {
            assertThat(dbStore.findAll().size(), is(0));
            User user = new User("testName");
            dbStore.add(user);
            List<User> list = dbStore.findAll();
            assertThat(list.get(0), is(user));
            dbStore.delete(list.get(0).getId());
        }
    }

    @Test
    public void addElementCheckDeleteElementCheck() {
        try (DbStore dbStore = new DbStore()) {
            assertThat(dbStore.findAll().size(), is(0));
            User user = new User("testName");
            user.setId(20);
            dbStore.add(user);
            assertThat(dbStore.findAll().size(), is(1));
            dbStore.delete(user.getId());
            assertThat(dbStore.findAll().size(), is(0));
        }
    }

    @Test
    public void addElementFindElementCheck() {
        try (DbStore dbStore = new DbStore()) {
            User user = new User("testName");
            user.setId(20);
            dbStore.add(user);
            assertThat(dbStore.find(user.getId()), is(user));
            dbStore.delete(user.getId());
        }
    }

    @Test
    public void addElementUpdateElementCheckElement() {
        try (DbStore dbStore = new DbStore()) {
            User user = new User("testName");
            user.setId(20);
            dbStore.add(user);
            assertThat(dbStore.find(user.getId()).getName(), is(user.getName()));
            user.setName("testName2");
            dbStore.update(user);
            assertThat(dbStore.find(user.getId()).getName(), is(user.getName()));
            dbStore.delete(user.getId());
        }
    }

    @Test
    public void findAllElements() {
        try (DbStore dbStore = new DbStore()) {
            assertThat(dbStore.findAll().size(), is(0));
            User user = new User("");
            user.setId(20);
            dbStore.add(user);
            assertThat(dbStore.findAll().size(), is(1));
            dbStore.delete(user.getId());
        }
    }
}