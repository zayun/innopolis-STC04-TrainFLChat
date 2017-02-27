package service;

import models.dao.PersonDAO;
import models.pojo.Person;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис по работе с сущностями таблицы d_Persones
 */
public class PersonService {

    private static PersonDAO personDAO = new PersonDAO();

    public static Person getPersonOnId(int personId) {
        return personDAO.getEntityById(personId);
    }

    public static Person update(Person person) {
        return personDAO.update(person);
    }
}
