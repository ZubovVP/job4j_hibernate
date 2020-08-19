package ru.job4j.todoList.storage.operations;

import java.io.Closeable;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 25.04.2020.
 */
public interface Actions<E> extends AddAble<E>, GetAllElementsAble,UpdateAble<E>, FindAble<E>, DeleteAble, Closeable {

}
