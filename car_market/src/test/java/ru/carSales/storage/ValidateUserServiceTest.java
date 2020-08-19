package ru.carSales.storage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.carSales.models.UserForSales;
import ru.carSales.storage.operations.Actions;

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
public class ValidateUserServiceTest {
    private UserForSales user = new UserForSales();
    private Actions<UserForSales> db_users = DBStorage_user.getInstance();
    private ValidateUserService validate = ValidateUserService.getInstance();

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
        this.validate = null;
        this.validate = ValidateUserService.getInstance();
        assertNotNull(this.validate);
    }

    @Test
    public void addElementInDb() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
    }

    @Test(expected = IncorrectDateException.class)
    public void addElementWithPasswordIsNullShouldException() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.user.setPassword("");
        this.validate.add(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void addElementWithPasswordIsNothingShouldException1() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.user.setPassword(null);
        this.validate.add(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void addElementWithEmailIsNullShouldException() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.user.setEmail("");
        this.validate.add(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void addElementWithEmailIsNothingShouldException1() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.user.setEmail(null);
        this.validate.add(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void addElementWithTelephoneIsNullShouldException() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.user.setTelephone("");
        this.validate.add(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void addElementWithTelephoneIsNothingShouldException1() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.user.setTelephone(null);
        this.validate.add(this.user);
    }

    @Test
    public void getAllElements() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
    }

    @Test
    public void deleteElement() {
        this.user = this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        this.validate.delete(this.user.getId());
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
    }

    @Test(expected = IncorrectDateException.class)
    public void deleteElement1WithIdIsZero() {
        this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        this.validate.delete(0);
    }

    @Test(expected = IncorrectDateException.class)
    public void deleteElement1WithIdIsSubZero() {
        this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        this.validate.delete(-1);
    }

    @Test
    public void updateElement() {
        this.user = this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().get(0).getName(), is(this.user.getName()));
        this.user.setName("Test2");
        this.validate.update(this.user);
        Assert.assertThat(this.validate.getAllElements().get(0).getName(), is(this.user.getName()));
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithIdIsZero() {
        this.user = this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().get(0).getName(), is(this.user.getName()));
        this.user.setId(0);
        this.validate.update(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithIdSubZero() {
        this.user = this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().get(0).getName(), is(this.user.getName()));
        this.user.setId(-1);
        this.validate.update(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithPasswordIsNoting() {
        this.user = this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().get(0).getName(), is(this.user.getName()));
        this.user.setPassword("");
        this.validate.update(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithPasswordIsNull() {
        this.user = this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().get(0).getName(), is(this.user.getName()));
        this.user.setPassword(null);
        this.validate.update(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithTelephoneIsNoting() {
        this.user = this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().get(0).getName(), is(this.user.getName()));
        this.user.setTelephone("");
        this.validate.update(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithTelephoneIsNull() {
        this.user = this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().get(0).getName(), is(this.user.getName()));
        this.user.setTelephone(null);
        this.validate.update(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithEmailIsNoting() {
        this.user = this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().get(0).getName(), is(this.user.getName()));
        this.user.setEmail("");
        this.validate.update(this.user);
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithEmailIsNull() {
        this.user = this.validate.add(this.user);
        Assert.assertThat(this.validate.getAllElements().get(0).getName(), is(this.user.getName()));
        this.user.setEmail(null);
        this.validate.update(this.user);
    }

    @Test
    public void findElement() {
        this.user = this.validate.add(this.user);
        Assert.assertThat(this.validate.find(this.user.getId()).getName(), is(this.user.getName()));
    }

    @Test(expected = IncorrectDateException.class)
    public void findElementWithIdIsZero() {
        this.user = this.validate.add(this.user);
        this.validate.find(0);
    }

    @Test(expected = IncorrectDateException.class)
    public void findElementWithIdIsSubZero() {
        this.user = this.validate.add(this.user);
        this.validate.find(0);
    }

    @Test
    public void findUser() {
        String email = this.user.getEmail();
        String password = this.user.getPassword();
        this.validate.add(this.user);
        Assert.assertThat(this.validate.findUser(email, password).getName(), is(this.user.getName()));

    }

    @Test(expected = IncorrectDateException.class)
    public void findUserWithPasswordIsNoting() {
        String email = this.user.getEmail();
        String password = "";
        this.validate.add(this.user);
        this.validate.findUser(email, password);

    }

    @Test(expected = IncorrectDateException.class)
    public void findUserWithPasswordIsNull() {
        String email = this.user.getEmail();
        String password = null;
        this.validate.add(this.user);
        this.validate.findUser(email, password);
    }

    @Test(expected = IncorrectDateException.class)
    public void findUserWithEmailIsNoting() {
        String email = this.user.getEmail();
        String password = "";
        this.validate.add(this.user);
        this.validate.findUser(email, password);
    }

    @Test(expected = IncorrectDateException.class)
    public void findUserWithEmailIsNull() {
        String email = this.user.getEmail();
        this.validate.add(this.user);
        this.validate.findUser(email, null);
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