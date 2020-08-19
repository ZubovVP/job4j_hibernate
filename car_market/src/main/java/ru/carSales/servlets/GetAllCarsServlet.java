package ru.carSales.servlets;

import lombok.SneakyThrows;
import org.codehaus.jackson.map.ObjectMapper;
import ru.carSales.models.Offer;
import ru.carSales.storage.ValidateCarService;
import ru.carSales.storage.operations.Actions;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 24.06.2020.
 */
public class GetAllCarsServlet extends HttpServlet {
    private final static Actions<Offer> VS = ValidateCarService.getInstance();

    /**
     * Get all offer from DB.
     *
     * @param req - req.
     * @param resp - resp.
     * @throws IOException - IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*"); // Cros
        List<Offer> cars = VS.getAllElements();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cars);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.print(json);
        writer.flush();
    }

    /**
     * Close ValidateCarService and destroy GetAllCarsServlet.
     */
    @SneakyThrows
    @Override
    public void destroy() {
        VS.close();
        super.destroy();
    }
}
