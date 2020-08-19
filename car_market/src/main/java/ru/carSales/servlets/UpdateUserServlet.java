package ru.carSales.servlets;

import lombok.SneakyThrows;
import ru.carSales.models.Offer;
import ru.carSales.models.UserForSales;
import ru.carSales.storage.operations.Actions;
import ru.carSales.storage.ValidateUserService;
import ru.carSales.storage.encrypt.Encryption;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 10.08.2020.
 */
public class UpdateUserServlet extends HttpServlet {
    private static Actions<UserForSales> VALIDATE = ValidateUserService.getInstance();

    /**
     * Show UpdateUser.jsp.
     *
     * @param req - req.
     * @param resp - resp.
     * @throws ServletException - ServletException.
     * @throws IOException - ServletException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        int id = (int) session.getAttribute("id");
        UserForSales user = VALIDATE.find(id);
        req.setAttribute("name", user.getName());
        req.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/WEB-INF/views/UpdateUser.jsp").forward(req, resp);
    }

    /**
     * Update user.
     *
     * @param req - req.
     * @param resp - resp.
     * @throws ServletException - ServletException.
     * @throws IOException - IOException.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*"); // Cros
        UserForSales user = new UserForSales();
        HttpSession session = req.getSession();
        int id = (int) session.getAttribute("id");
        if (id != 0) {
            user.setId(id);
            user.setCars(VALIDATE.find(user.getId()).getCars());
            user.setTelephone(req.getParameter("telephone"));
            user.setPassword(req.getParameter("password"));
            user.setEmail(req.getParameter("email"));
            user.setName(req.getParameter("name"));
            user.setSurname(req.getParameter("surname"));
            if (VALIDATE.update(user) == true) {
                resp.sendRedirect(String.format("%s/start", req.getContextPath()));
            }
        }
        doGet(req, resp);
    }

    /**
     * Close ValidateUserService and destroy UpdateOfferServlet.
     */
    @SneakyThrows
    @Override
    public void destroy() {
        VALIDATE.close();
        super.destroy();
    }
}
