package ru.job4j.todoList.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.todoList.models.Item;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 25.04.2020.
 */
public class DBStorageTest {
    private DBStorage<Item> store;
    private Item item = new Item();

    @Before
    public void start(){
        this.store = DBStorage.getInstance();
    }


    @After
    public void finish() {
        for(Item item : this.store.getAllElements()){
            this.store.delete(item.getId());
        }
    }

    @Test
    public void testGetInstance() {
        this.store = null;
        this.store = DBStorage.getInstance();
        assertNotNull(store);
    }

    @Test
    public void addElementAndCheckElementFromDB() {
        this.item.setDescription("TestDesc");
        assertThat(this.item.getDescription(), is("TestDesc"));
        assertThat(this.item.getId(), is(0));
        this.store.add(item);
        Item itemFromDb = this.store.getAllElements().get(0);
        assertThat(itemFromDb.getDescription(), is(item.getDescription()));
        assertNotEquals(0, itemFromDb.getId());
    }

    @Test
    public void addElementGetAllElementsFromDbCheck() {
        this.item.setDescription("TestDesc");
        assertThat(this.store.getAllElements().size(), is(0));
        this.store.add(this.item);
        assertThat(this.store.getAllElements().size(), is(1));
    }

    @Test
    public void addElementIntoDBCheckDeleteElementFromDBCheck() {
        this.item.setDescription("TestDesc");
        this.store.add(this.item);
        assertThat(this.store.getAllElements().size(), is(1));
        this.store.delete(this.item.getId());
        assertThat(this.store.getAllElements().size(), is(0));
    }

    @Test
    public void addElementsWithDoneAreTrueAndFalseGetAllIsNotDoneElementsCheck() {
        this.item.setDescription("TestDesc");
        this.store.add(this.item);
        Item item2 = new Item();
        item2.setDescription("TestDesc2");
        item2.setDone(true);
        this.store.add(item2);
        assertThat(this.store.getAllIsNotDoneElements().size(), is(1));
        Item itemFromDb = this.store.getAllIsNotDoneElements().get(0);
        assertThat(itemFromDb.getDescription(), is(this.item.getDescription()));
        assertThat(itemFromDb.isDone(), is(this.item.isDone()));
    }

    @Test
    public void addElementUpdateElementCheck(){
        this.item.setDescription("TestDesc");
        this.store.add(item);
       this.item = this.store.getAllElements().get(0);
        this.item.setDescription("TestDesc2");
        this.item.setDone(true);
        this.store.update(this.item);
        assertThat(this.store.getAllElements().get(0).getDescription(), is(this.item.getDescription()));
        assertThat(this.store.getAllElements().get(0).isDone(), is(this.item.isDone()));
    }

    @Test
    public void addElementCheckFindById() {
        this.item.setDescription("TestDesc");
        this.store.add(item);
        this.item = this.store.getAllElements().get(0);
        assertThat(this.store.find(this.item.getId()).getDescription(), is(this.item.getDescription()));
        assertThat(this.store.getAllElements().get(0).isDone(), is(this.item.isDone()));
    }
}