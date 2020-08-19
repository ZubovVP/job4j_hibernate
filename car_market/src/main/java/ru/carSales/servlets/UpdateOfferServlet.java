package ru.carSales.servlets;

import lombok.SneakyThrows;
import ru.carSales.models.Offer;
import ru.carSales.models.UserForSales;
import ru.carSales.storage.ValidateCarService;
import ru.carSales.storage.operations.Actions;

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
 * Date: 10.08.2020.
 */
public class UpdateOfferServlet extends HttpServlet {
    private static Actions<Offer> VALIDATE = ValidateCarService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/UpdateOffer.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*"); // Cros
        Offer car = new Offer();
        UserForSales user = new UserForSales();
        user.setId(Integer.parseInt(req.getParameter("idUser")));
        car.setUser(user);
        car.setPrice(Integer.parseInt(req.getParameter("price")));
        car.setStatus(true);
        car.setDir_photos(VALIDATE.find(Integer.parseInt(req.getParameter("id"))).getDir_photos());
        car.setCategory(req.getParameter("category"));
        car.setTypeBody(req.getParameter("type_body"));
        car.setTransmission(req.getParameter("transmission"));
        car.setYearOfIssue(Integer.parseInt(req.getParameter("year_of_issue")));
        car.setMark(req.getParameter("mark"));
        car.setId(Integer.parseInt(req.getParameter("id")));
        if (VALIDATE.update(car)) {
            resp.sendRedirect(String.format("%s/start", req.getContextPath()));
            return;
        }
        doGet(req, resp);
    }

    /**
     * Close ValidateCarService and destroy UpdateOfferServlet.
     */
    @SneakyThrows
    @Override
    public void destroy() {
        VALIDATE.close();
        super.destroy();
    }
}
