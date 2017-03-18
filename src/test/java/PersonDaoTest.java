import org.junit.jupiter.api.Test;
import ru.innopolis.smoldyrev.models.dao.LanguageDAO;
import ru.innopolis.smoldyrev.models.dao.PersonDAO;
import ru.innopolis.smoldyrev.models.dto.LanguageDTO;
import ru.innopolis.smoldyrev.models.dto.PersonDTO;
import ru.innopolis.smoldyrev.models.pojo.Language;
import ru.innopolis.smoldyrev.models.pojo.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by smoldyrev on 18.03.17.
 */
public class PersonDaoTest {

    private static final EntityManagerFactory FACTORY =
            Persistence.createEntityManagerFactory("LFLChat");

    private static final PersonDAO personDAO = new PersonDAO();

//    @Test
//    public void getEntityById() {
//        Integer id = 68;
//        EntityManager entityManager = FACTORY.createEntityManager();
//
//        TypedQuery<PersonDTO> query = entityManager.createQuery(
//                "SELECT person FROM PersonDTO person where person.id = :person_id", PersonDTO.class);
//
//        PersonDTO personDTO = query.setParameter("person_id", id).getSingleResult();
//        Person person = personDAO.getEntityById(id).transformToPerson();
//
//        assertNotNull(person);
//        System.out.println(person.getLanguages().size());
//        for (Language l:
//             person.getLanguages()) {
//            System.out.println(l.getFullName());
//        }
//    }


    @Test
    public void addLanguageToPerson() {
        Integer id = 68;

        EntityManager entityManager = FACTORY.createEntityManager();
        entityManager.getTransaction().begin();

        LanguageDAO ldao = new LanguageDAO();
        LanguageDTO l = ldao.getEntityById("UKR");

        PersonDTO personDTO = personDAO.getEntityById(id);
        System.out.println(personDTO.getLanguages().size());
        personDTO.addLanguage(l);
        System.out.println(personDTO.getLanguages().size());
        personDTO = entityManager.merge(personDTO);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

}
