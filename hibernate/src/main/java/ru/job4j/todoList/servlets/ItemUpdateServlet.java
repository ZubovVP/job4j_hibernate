package ru.job4j.todoList.servlets;

import ru.job4j.todoList.models.Item;
import ru.job4j.todoList.storage.ValidateService;
import ru.job4j.todoList.storage.operations.Actions;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 26.04.2020.
 */
public class ItemUpdateServlet extends HttpServlet {
    private final static Actions<Item> VS = new ValidateService();

    /**
     * Redirect on ItemGetServlet.
     * Get all items or all items with done is false.
     *
     * @param req  - request.
     * @param resp - response.
     * @throws ServletException - ServletException.
     * @throws IOException      - IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getNamedDispatcher("ItemGetServlet").forward(req, resp);
    }

    /**
     * Get id of an element and change done from DB.
     *
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
            Item item = VS.find(Integer.parseInt(id));
            item.setDone(!item.isDone());
            VS.update(item);
        doGet(req, resp);
    }

    /**
     * Close ValidateService adn destroy ItemGetServlet.
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
