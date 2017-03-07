package ru.innopolis.smoldyrev.controllers.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 26.02.17.
 * Фильтр административных прав
 * проверяет права полученный при авторизации из
 * @see ru.innopolis.smoldyrev.controllers.listeners.SessionListener
 */
@WebFilter(filterName = "FilterAdmin",
        urlPatterns = {"/adm/*","/delmessage"})
public class FilterAdmin implements Filter {

    private static Logger logger = Logger.getLogger(FilterAdmin.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if ((httpRequest.getSession().getAttribute("sessionUserType") != null)
                && ((httpRequest.getSession().getAttribute("sessionUserType").equals("admin"))
                || (httpRequest.getSession().getAttribute("sessionUserType").equals("administrator")))) {
            chain.doFilter(request, response);
        } else {
            String msg = "Нехватает прав для посещения этого ресурса!";
            httpRequest.setAttribute("msg", msg);
            httpRequest.getRequestDispatcher("/error.jsp").forward(httpRequest, httpResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
