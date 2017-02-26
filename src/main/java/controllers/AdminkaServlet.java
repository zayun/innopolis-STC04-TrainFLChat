package controllers;

import models.dao.UserDAO;
import models.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by smoldyrev on 26.02.17.
 * данные админки
 */
public class AdminkaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAll();

        req.setAttribute("users", users);

        req.getRequestDispatcher("/admin/adminoffice.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        List<User> users = userDAO.getAll();

        req.setAttribute("users", users);

        req.getRequestDispatcher("/admin/adminoffice.jsp").forward(req, resp);
    }
}
