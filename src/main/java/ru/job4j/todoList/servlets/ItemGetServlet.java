package ru.job4j.todoList.servlets;

import org.codehaus.jackson.map.ObjectMapper;
import ru.job4j.todoList.models.Item;
import ru.job4j.todoList.storage.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 23.04.2020.
 */
public class ItemGetServlet extends HttpServlet {
    private final static ValidateService VS = new ValidateService();

    /**
     * Get all items if parameter is true or all items with done is false if parameter is false.
     *
     * @param req  - request.
     * @param resp - response.
     * @throws ServletException - ServletException.
     * @throws IOException      - IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*"); // Cros
        String allItems = req.getParameter("allItems");
        List<Item> items = allItems.equals("true") ? VS.getAllElements() : VS.getAllIsNotDoneElements();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(items);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.print(json);
        writer.flush();
    }

    /**
     * Close ValidateService adn destroy ItemGetServlet.
     */
    @Override
    public void destroy() {
        VS.close();
        super.destroy();
    }
}
