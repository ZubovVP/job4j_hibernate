package ru.job4j.todoList.servlets;

import ru.job4j.todoList.models.Item;
import ru.job4j.todoList.storage.ValidateService;
import ru.job4j.todoList.storage.exceptions.IncorrectDateException;
import ru.job4j.todoList.storage.operations.Actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.GregorianCalendar;

/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov
 * Email: Zubov.VP@yandex.ru
 * Version: $Id$
 * Date: 14.12.2018
 */
public class ItemCreateServlet extends HttpServlet {
    private final static Actions<Item> VS = new ValidateService();


    /**
     * Redirect on the ItemGetServlet.
     * Get all items if parameter is true or all items with done is false if parameter is false.
     *
     * @param req  - HttpServletRequest.
     * @param resp = HttpServletResponse.
     * @throws ServletException - ServletException.
     * @throws IOException      - IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getNamedDispatcher("ItemGetServlet").forward(req, resp);
    }

    /**
     * Create Item and add the item in the DB. Redirect on the ItemGetServlet.
     * Get all items if parameter is true or all items with done is false if parameter is false.
     *
     * @param req  - HttpServletRequest.
     * @param resp - HttpServletResponse.
     * @throws IOException - IOException.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Item item = new Item();
        item.setDescription(req.getParameter("desc"));
        item.setCreated(new GregorianCalendar());
        try {
            VS.add(item);
        } catch (IncorrectDateException e) {
            e.getMessage();
            resp.sendError(406, e.getMessage());
        }
        doGet(req, resp);
    }

    /**
     * Close ValidateService and destroy ItemCreateServlet.
     */
    @Override
    public void destroy() {
        try {
            VS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.destroy();
    }
}
