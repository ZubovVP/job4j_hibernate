package ru.job4j.models;


import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 12.04.2020.
 */
public class UserTest {

    @Test
    public void testGetAndSetId() {
        User userTest = new User();
        assertThat(userTest.getId(), is(0));
        userTest.setId(1);
        assertThat(userTest.getId(), is(1));
    }

    @Test
    public void testGetAndSetName() {
        User userTest = new User();
        assertNull(userTest.getName());
        userTest.setName("test");
        assertThat(userTest.getName(), is("test"));
    }

    @Test
    public void getExpired() {
        User userTest = new User();
        assertNull(userTest.getExpired());
        Calendar calendar = new GregorianCalendar();
        userTest.setExpired(calendar);
        assertThat(userTest.getExpired().getTimeInMillis(), is(calendar.getTimeInMillis()));
    }

    @Test
    public void getEquals() {
        Calendar calendar = new GregorianCalendar();
        User userTest1 = new User();
        User userTest2 = new User();
        userTest1.setId(1);
        userTest1.setName("test");
        userTest1.setExpired(calendar);
        userTest2.setName("test");
        userTest2.setExpired(calendar);
        assertNotEquals(userTest1, userTest2);
        userTest2.setId(1);
        assertEquals(userTest1, userTest2);
    }
}