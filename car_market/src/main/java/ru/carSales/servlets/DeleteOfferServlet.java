package ru.carSales.servlets;

import lombok.SneakyThrows;
import ru.carSales.storage.ValidateCarService;
import ru.carSales.storage.operations.Actions;
import ru.carSales.models.Offer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 10.08.2020.
 */
public class DeleteOfferServlet extends HttpServlet {
    private static Actions<Offer> VALIDATE = ValidateCarService.getInstance();

    /**
     * Delete offer from DB.
     *
     * @param req - req.
     * @param resp - resp.
     * @throws IOException - IOException.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("idI"));
        VALIDATE.delete(id);
        resp.sendRedirect(String.format("%s/start", req.getContextPath()));
    }

    /**
     * Close ValidateCarService and destroy DeleteOfferServlet.
     */
    @SneakyThrows
    @Override
    public void destroy() {
        VALIDATE.close();
        super.destroy();
    }
}
