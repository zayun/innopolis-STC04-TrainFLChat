package service;

import models.dao.PersonDAO;
import models.dao.UserDAO;
import models.pojo.User;

/**
 * Created by smoldyrev on 23.02.17.
 */
public class UserService {

    public static boolean authorize(String login, String password) {
        return (UserDAO.getUserByLoginAndPassword(login, password) != null);
    }

    public static boolean registration(User user) {
        PersonDAO pdao = new PersonDAO();
        UserDAO udao = new UserDAO();
        if (pdao.create(user.getPerson())) {
            return (udao.create(user));
        } else return false;
    }

}
