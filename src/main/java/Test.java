import models.dao.PersonDAO;
import models.pojo.Person;

import java.sql.Date;

/**
 * Created by smoldyrev on 23.02.17.
 */
public class Test {
    public static void main(String[] args) {
        PersonDAO personDAO =new PersonDAO();

        Person person = new Person(0,"semenov","semen",
                "semen@semen.ru","911-911",new Date(1,1,1),true);
        personDAO.create(person);
    }
}
