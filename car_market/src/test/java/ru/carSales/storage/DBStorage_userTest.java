package ru.carSales.storage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.carSales.models.UserForSales;


import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 16.08.2020.
 */
public class DBStorage_userTest {
    private UserForSales user = new UserForSales();
    private DBStorage_user<UserForSales> db_users = DBStorage_user.getInstance();

    @Before
    public void start() {
        this.user = fillUser(user);
    }

    @After
    public void finish() {
        List<UserForSales> users = this.db_users.getAllElements();
        for (UserForSales user : users) {
            this.db_users.delete(user.getId());
        }
    }

    @Test
    public void getInstance() {
        this.db_users = null;
        this.db_users = DBStorage_user.getInstance();
        assertNotNull(this.db_users);
    }

    @Test
    public void add() {
        Assert.assertThat(this.db_users.getAllElements().size(), is(0));
        this.db_users.add(this.user);
        Assert.assertThat(this.db_users.getAllElements().size(), is(1));
    }

    @Test
    public void getAllElements() {
        Assert.assertThat(this.db_users.getAllElements().size(), is(0));
        this.db_users.add(this.user);
        Assert.assertThat(this.db_users.getAllElements().size(), is(1));
    }

    @Test
    public void delete() {
        this.user = this.db_users.add(this.user);
        Assert.assertThat(this.db_users.getAllElements().size(), is(1));
        this.db_users.delete(this.user.getId());
        Assert.assertThat(this.db_users.getAllElements().size(), is(0));
    }

    @Test
    public void update() {
        this.user = this.db_users.add(this.user);
        this.user.setName("TestName2");
        this.db_users.update(this.user);
        Assert.assertThat(this.db_users.getAllElements().get(0).getName(), is(this.user.getName()));
    }

    @Test
    public void find() {
        this.user = this.db_users.add(this.user);
        Assert.assertThat(this.db_users.getAllElements().size(), is(1));
        Assert.assertThat( this.db_users.find(this.user.getId()).getName(), is(this.user.getName()));
    }

    @Test
    public void findUser() {
        this.user = this.db_users.add(this.user);
        Assert.assertThat(this.db_users.getAllElements().size(), is(1));
        Assert.assertThat(this.db_users.findUser(this.user.getEmail(), this.user.getPassword()).getId(), is(this.user.getId()));
    }

    private UserForSales fillUser(UserForSales user) {
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setEmail("test@yandex.ru");
        user.setPassword("123");
        user.setTelephone("12344");
        return user;
    }
}