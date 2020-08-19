package ru.job4j.todoList.models;

import java.util.Calendar;
import java.util.Objects;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 21.04.2020.
 */
public class Item {
    private int id;
    private String description;
    private Calendar created;
    private boolean done;

    /**
     * Constructor.
     */
    public Item() {
    }

    /**
     * Get id.
     * @return id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get description.
     *
     * @return - description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get created date.
     *
     * @return - date.
     */
    public Calendar getCreated() {
        return created;
    }

    /**
     * Get done.
     *
     * @return - done.
     */
    public boolean isDone() {
        return this.done;
    }

    /**
     * Set id.
     *
     * @param id - id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set description.
     *
     * @param description - description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Set created date.
     *
     * @param created - date.
     */
    public void setCreated(Calendar created) {
        this.created = created;
    }

    /**
     * Set done.
     *
     * @param done - done.
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                done == item.done &&
                Objects.equals(description, item.description) &&
                Objects.equals(created, item.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, done);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", done=" + done +
                '}';
    }
}
