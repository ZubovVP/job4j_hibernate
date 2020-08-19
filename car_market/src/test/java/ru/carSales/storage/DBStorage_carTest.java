package ru.carSales.storage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.carSales.models.Offer;
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
public class DBStorage_carTest {
    private DBStorage_car db_offer = DBStorage_car.getInstance();
    private DBStorage_user db_user = DBStorage_user.getInstance();
    private Offer offer = new Offer();
    private UserForSales user = new UserForSales();


    @Before
    public void start() {
        this.user = fillUser(user);
        this.offer = fillOffer(this.user, this.offer);
    }

    @After
    public void finish() {
        List<Offer> offers = this.db_offer.getAllElements();
        for (Offer offer : offers) {
            this.db_offer.delete(offer.getId());
        }
        List<UserForSales> users = this.db_user.getAllElements();
        for (UserForSales user : users) {
            this.db_user.delete(user.getId());
        }
    }

    @Test
    public void getInstance() {
        this.db_offer = null;
        this.db_offer = DBStorage_car.getInstance();
        assertNotNull(this.db_offer);
    }

    @Test
    public void add() {
        Assert.assertThat(this.db_offer.getAllElements().size(), is(0));
        this.db_offer.add(this.offer);
        Assert.assertThat(this.db_offer.getAllElements().size(), is(1));
    }

    @Test
    public void getAllElements() {
        Assert.assertThat(this.db_offer.getAllElements().size(), is(0));
        this.db_offer.add(this.offer);
        Assert.assertThat(this.db_offer.getAllElements().size(), is(1));
    }

    @Test
    public void delete() {
        this.offer = this.db_offer.add(this.offer);
        Assert.assertThat(this.db_offer.getAllElements().size(), is(1));
        this.db_offer.delete(this.offer.getId());
        Assert.assertThat(this.db_offer.getAllElements().size(), is(0));
    }

    @Test
    public void update() {
        this.offer = this.db_offer.add(this.offer);
        Assert.assertThat(this.db_offer.getAllElements().size(), is(1));
        this.offer.setMark("TestMark2");
        this.db_offer.update(this.offer);
        Assert.assertThat(this.db_offer.find(this.offer.getId()).getMark(), is(this.offer.getMark()));
    }

    @Test
    public void find() {
        this.offer = this.db_offer.add(this.offer);
        Assert.assertThat(this.db_offer.getAllElements().size(), is(1));
        Assert.assertThat(this.db_offer.find(this.offer.getId()).getMark(), is(this.offer.getMark()));
    }

    private UserForSales fillUser(UserForSales user) {
        user.setName("TestName");
        user.setSurname("TestSurname");
        user.setEmail("test@yandex.ru");
        user.setPassword("123");
        user.setTelephone("12344");
        return this.db_user.add(user);
    }

    private Offer fillOffer(UserForSales user, Offer offer) {
        offer.setUser(user);
        offer.setCategory("testCategory");
        offer.setMark("testMark");
        offer.setPrice(10000);
        offer.setStatus(true);
        offer.setTransmission("testTransmission");
        offer.setTypeBody("testType");
        offer.setYearOfIssue(1900);
        return offer;
    }
}