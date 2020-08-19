package ru.job4j.todoList.storage;

import org.junit.Test;
import ru.job4j.todoList.models.Item;
import ru.job4j.todoList.storage.exceptions.IncorrectDateException;
import ru.job4j.todoList.storage.operations.Actions;

import java.util.GregorianCalendar;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 28.04.2020.
 */
public class ValidateServiceTest {
    private Actions<Item> validate = new ValidateService();
    private Item item = new Item();


    @Test(expected = IncorrectDateException.class)
    public void checkExceptionForFindByIdWriteZero() {
        this.validate.find(0);
    }

    @Test(expected = IncorrectDateException.class)
    public void checkExceptionAddElementWithNullDescription() {
        this.validate.add(this.item);
    }

    @Test(expected = IncorrectDateException.class)
    public void checkExceptionAddElementWithEmptyDescription() {
        this.item.setDescription("");
        this.validate.add(this.item);
    }

    @Test(expected = IncorrectDateException.class)
    public void checkExceptionUpdateElementWithEmptyDescription() {
        this.item.setCreated(new GregorianCalendar());
        this.item.setId(12);
        this.item.setDescription("");
        this.validate.update(this.item);
    }

    @Test(expected = IncorrectDateException.class)
    public void checkExceptionUpdateElementWithNullDescription() {
        this.item.setCreated(new GregorianCalendar());
        this.item.setId(12);
        this.item.setDescription(null);
        this.validate.update(this.item);
    }

    @Test(expected = IncorrectDateException.class)
    public void checkExceptionUpdateElementWithIdZero() {
        this.item.setCreated(new GregorianCalendar());
        this.item.setId(0);
        this.item.setDescription("test");
        this.validate.update(this.item);
    }

    @Test(expected = IncorrectDateException.class)
    public void checkExceptionUpdateElementWithCreatedIsNull() {
        this.item.setId(1);
        this.item.setDescription("test");
        this.validate.update(this.item);
    }
}