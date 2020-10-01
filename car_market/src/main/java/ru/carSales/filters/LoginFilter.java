package ru.carSales.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 09.07.2020.
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    /**
     * Check id.
     *
     * @param servletRequest  - servletRequest.
     * @param servletResponse - servletResponse.
     * @param chain           - chain.
     * @throws IOException      - IOException.
     * @throws ServletException - ServletException.
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        if (session.getAttribute("id") == null) {
            response.sendRedirect(String.format("%s/login", request.getContextPath()));
            return;
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
