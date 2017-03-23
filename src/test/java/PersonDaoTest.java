import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ru.innopolis.smoldyrev.models.entity.PersonEntity;
import ru.innopolis.smoldyrev.models.repository.LanguageRepository;
import ru.innopolis.smoldyrev.models.repository.PersonRepository;

@ContextConfiguration(classes = { TestConfiguration.class }, loader = AnnotationConfigContextLoader.class)
public class PersonDaoTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private LanguageRepository languageRepository;


    @Test
    public void addLanguageToPerson() {
        int personId = 156;
        String language = "RUS";
        PersonEntity person = personRepository.findOne(personId);
        person.addLanguage(languageRepository.findOne(language));
//        personRepository.saveAndFlush(person);
    }

}
