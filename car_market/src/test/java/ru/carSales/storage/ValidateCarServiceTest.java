package ru.carSales.storage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.carSales.models.Offer;
import ru.carSales.models.UserForSales;
import ru.carSales.storage.operations.Actions;

import java.time.LocalDate;
import java.time.Period;
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
public class ValidateCarServiceTest {
    private Actions<Offer> db_offer = DBStorage_car.getInstance();
    private Actions<UserForSales> db_user = DBStorage_user.getInstance();
    private Offer offer = new Offer();
    private UserForSales user = new UserForSales();
    private ValidateCarService validate = ValidateCarService.getInstance();


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
        this.validate = null;
        this.validate = ValidateCarService.getInstance();
        assertNotNull(this.validate);
    }

    @Test
    public void addElementShouldNewIdFromDB() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.validate.add(this.offer);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
    }

    @Test(expected = IncorrectDateException.class)
    public void addElementWithIdUserIsZeroShouldException() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.offer.getUser().setId(0);
        this.validate.add(this.offer);
    }

    @Test(expected = IncorrectDateException.class)
    public void addElementWithIdUserIsSubZeroShouldException() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.offer.getUser().setId(-1);
        this.validate.add(this.offer);
    }

    @Test
    public void getAllElementsFromDb() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.validate.add(this.offer);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
    }

    @Test
    public void deleteElementFromDb() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.validate.add(this.offer);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        this.validate.delete(this.offer.getId());
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
    }

    @Test(expected = IncorrectDateException.class)
    public void deleteElementFromDbWithIdOfferIsZeroShouldException() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.validate.add(this.offer);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        this.offer.setId(0);
        this.validate.delete(this.offer.getId());
    }

    @Test(expected = IncorrectDateException.class)
    public void deleteElementFromDbWithIdOfferIsSubZeroShouldException() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.validate.add(this.offer);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        this.offer.setId(-1);
        this.validate.delete(this.offer.getId());
    }

    @Test
    public void updateElement() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.offer = this.validate.add(this.offer);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        Assert.assertThat(this.validate.getAllElements().get(0).getMark(), is(this.offer.getMark()));
        this.offer.setMark("testMark2");
        this.validate.update(this.offer);
        Assert.assertThat(this.validate.getAllElements().get(0).getMark(), is(this.offer.getMark()));
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithUserIsNullShouldException() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.offer = this.validate.add(this.offer);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        Assert.assertThat(this.validate.getAllElements().get(0).getMark(), is(this.offer.getMark()));
        this.offer.setUser(null);
        this.validate.update(this.offer);
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithIdUserIsZeroShouldException() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.offer = this.validate.add(this.offer);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        Assert.assertThat(this.validate.getAllElements().get(0).getMark(), is(this.offer.getMark()));
        this.offer.getUser().setId(0);
        this.validate.update(this.offer);
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithIdIsZeroShouldException() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.offer = this.validate.add(this.offer);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        Assert.assertThat(this.validate.getAllElements().get(0).getMark(), is(this.offer.getMark()));
        this.offer.setId(0);
        this.validate.update(this.offer);
    }

    @Test(expected = IncorrectDateException.class)
    public void updateElementWithIdSubZeroShouldException() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.offer = this.validate.add(this.offer);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        Assert.assertThat(this.validate.getAllElements().get(0).getMark(), is(this.offer.getMark()));
        this.offer.setId(-1);
        this.validate.update(this.offer);
    }

    @Test
    public void findElement() {
        Assert.assertThat(this.validate.getAllElements().size(), is(0));
        this.offer = this.validate.add(this.offer);
        Assert.assertThat(this.validate.getAllElements().size(), is(1));
        Assert.assertThat(this.validate.find(this.offer.getId()).getMark(), is(this.offer.getMark()));
    }

    @Test(expected = IncorrectDateException.class)
    public void fineElementWithIdIsZeroShouldException() {
        this.validate.find(0);
    }

    @Test(expected = IncorrectDateException.class)
    public void fineElementWithIdSubZeroShouldException() {
        this.validate.find(-1);
    }

    @Test
    public void findElementByDateNow() {
        this.offer = this.validate.add(this.offer);
        List<Offer> result = this.validate.findByDate(LocalDate.now().minus(Period.ofDays(5)), LocalDate.now());
        Assert.assertThat(result.size(), is(1));
        Assert.assertThat(result.get(0), is(this.offer));
    }

    @Test(expected = IncorrectDateException.class)
    public void findElementByDateFutureShouldException() {
        this.offer = this.validate.add(this.offer);
        Assert.assertThat(this.validate.findByDate(LocalDate.now().minus(Period.ofDays(5)), LocalDate.now()).size(), is(1));
        this.validate.findByDate(LocalDate.now().minus(Period.ofDays(5)), LocalDate.now().plus(Period.ofDays(100)));
    }

    @Test(expected = IncorrectDateException.class)
    public void findElementByDateStartIsFutureShouldException() {
        this.offer = this.validate.add(this.offer);
        this.validate.findByDate(LocalDate.now().plus(Period.ofDays(5)), LocalDate.now());
    }

    @Test(expected = IncorrectDateException.class)
    public void findElementByDateFinishIsFutureShouldException() {
        this.offer = this.validate.add(this.offer);
        this.validate.findByDate(LocalDate.now(), LocalDate.now().plus(Period.ofDays(5)));
    }


    @Test
    public void findElementByType() {
        this.offer = this.validate.add(this.offer);
        Assert.assertThat(this.db_offer.getAllElements().size(), is(1));
        Assert.assertThat(this.validate.findByType("TestType2").size(), is(0));
        Assert.assertThat(this.validate.findByType(this.offer.getTypeBody()).size(), is(1));
        Assert.assertThat(this.validate.findByType(this.offer.getTypeBody()).get(0), is(this.offer));
    }

    @Test
    public void findElementByPictureWithoutPictureShouldNull() {
        this.offer = this.validate.add(this.offer);
        Assert.assertThat(this.db_offer.getAllElements().size(), is(1));
        Assert.assertThat(this.validate.findWithPicture().size(), is(0));
    }

    @Test
    public void findElementByPictureWithPicture() {
        this.offer.setDir_photos("test");
        this.offer = this.validate.add(this.offer);
        Assert.assertThat(this.db_offer.getAllElements().size(), is(1));
        Assert.assertThat(this.validate.findWithPicture().size(), is(1));
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
        offer.setDate(LocalDate.now());
        return offer;
    }
}