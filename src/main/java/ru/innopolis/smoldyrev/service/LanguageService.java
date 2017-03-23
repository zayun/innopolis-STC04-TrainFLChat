package ru.innopolis.smoldyrev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.innopolis.smoldyrev.common.exceptions.LanguageServiceException;
import org.apache.log4j.Logger;
import ru.innopolis.smoldyrev.models.dto.Transformer;
import ru.innopolis.smoldyrev.models.entity.PersonEntity;
import ru.innopolis.smoldyrev.models.pojo.Language;
import ru.innopolis.smoldyrev.models.pojo.Person;
import ru.innopolis.smoldyrev.models.repository.LanguageRepository;
import ru.innopolis.smoldyrev.models.repository.PersonRepository;
import ru.innopolis.smoldyrev.service.interfaces.ILanguageService;


import java.util.Set;

/**
 * Created by smoldyrev on 27.02.17.
 * Сервис работы с сущностями таблиц d_languages и r_langowner
 */
@Service
public class LanguageService implements ILanguageService {

    private static Logger logger = Logger.getLogger(LanguageService.class);

    private PersonRepository personRepository;
    private LanguageRepository languageRepository;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Autowired
    public void setLanguageRepository(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Set<Language> getLanguagesOnPerson(Integer personId) throws LanguageServiceException {
        Person person = Transformer.person(personRepository.findOne(personId));
        return person.getLanguages();
    }

    public boolean addLangToPerson(int personId, String language) throws LanguageServiceException {
        try {

            PersonEntity person = personRepository.findOne(personId);
            person.addLanguage(languageRepository.findOne(language));
            personRepository.saveAndFlush(person);

            return true;
        } catch (Exception e) {
            logger.error(e);
            throw new LanguageServiceException();
        }
    }
}
