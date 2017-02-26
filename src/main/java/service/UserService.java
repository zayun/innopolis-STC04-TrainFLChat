package service;

import models.dao.PersonDAO;
import models.dao.UserDAO;
import models.pojo.User;

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

}
