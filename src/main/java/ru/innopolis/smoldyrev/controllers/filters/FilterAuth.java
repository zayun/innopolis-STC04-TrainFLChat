package ru.innopolis.smoldyrev.controllers.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by smoldyrev on 25.02.17.
 * Фильтр доступа по сессии
 * проверяет id пользователя
 * записанный при авторизации
 */
//@WebFilter(filterName = "FilterAuth",
//        urlPatterns = {"/generalchat",
//                "/privateoffice",
//                "/privatechatroom",
//                "/sendmessage",
//                "/deletemessage"})
public class FilterAuth implements Filter {

    private static Logger logger = Logger.getLogger(FilterAuth.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getSession().getAttribute("sessionUserId") != null) {
            logger.trace("Authentificator is true");
            chain.doFilter(request, response);
        } else {
            logger.trace("Authentificator is false, go to auth");
            request.setAttribute("msg", "Сначала авторизируйтесь");
            request.getRequestDispatcher("/login").forward(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
