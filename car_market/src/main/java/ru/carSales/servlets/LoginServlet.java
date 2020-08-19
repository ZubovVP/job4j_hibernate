package ru.carSales.servlets;

import lombok.SneakyThrows;
import ru.carSales.models.UserForSales;
import ru.carSales.storage.ValidateUserService;

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
 * Date: 28.06.2020.
 */
public class LoginServlet extends HttpServlet {
    private final static ValidateUserService VALIDATE = ValidateUserService.getInstance();

    /**
     * Check password and e-mail, if false than redirect CreateUser.jsp, if exist than redirect CreateUser.jsp.
     *
     * @param req - req.
     * @param resp - resp.
     * @throws ServletException - ServletException.
     * @throws IOException - IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserForSales result = this.VALIDATE.findUser(email, password);
        if (result != null) {
            HttpSession session = req.getSession();
            session.setAttribute("id", result.getId());
            resp.sendRedirect(String.format("%s/createOffer", req.getContextPath()));
        } else {
            if (email != null && password != null) {
                req.setAttribute("error", "Credentional invalid");
            }
            getServletContext().getRequestDispatcher("/WEB-INF/views/CreateUser.jsp").forward(req, resp);
        }
    }

    /**
     * Create new user.
     *
     * @param req - req.
     * @param resp - resp.
     * @throws ServletException - ServletException.
     * @throws IOException - IOException.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*"); // Cros
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String telephone = req.getParameter("telephone");
        UserForSales user = new UserForSales();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setTelephone(telephone);
        user = this.VALIDATE.add(user);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("id", user.getId());
            resp.sendRedirect(String.format("%s/createOffer", req.getContextPath()));
            return;
        }
        doGet(req, resp);
    }

    /**
     * Close ValidateUserService and destroy LoginServlet.
     */
    @SneakyThrows
    @Override
    public void destroy() {
        VALIDATE.close();
        super.destroy();
    }
}
