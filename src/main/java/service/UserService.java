package service;

import models.dao.PersonDAO;
import models.dao.UserDAO;
import models.pojo.User;

import java.util.List;

/**
 * Created by smoldyrev on 23.02.17.
 * Сервис работы с пользователями
 */
public class UserService {

    private static UserDAO userDAO = new UserDAO();

    public static User authorize(String login, String password) {
        return (userDAO.getUserByLoginAndPassword(login, password));
    }

    public static boolean registration(User user) {

        PersonDAO pdao = new PersonDAO();

        if (pdao.create(user.getPerson())) {
            return (userDAO.create(user));
        } else return false;
    }

    public static User getUserById(int id) {
        return userDAO.getEntityById(id);
    }

    public static List<User> getAll() {
        return userDAO.getAll();
    }

    public static User update(User user) {
        return userDAO.update(user);
    }
}
