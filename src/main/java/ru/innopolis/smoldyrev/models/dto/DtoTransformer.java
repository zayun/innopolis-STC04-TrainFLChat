package ru.innopolis.smoldyrev.models.dto;

import ru.innopolis.smoldyrev.models.pojo.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by smoldyrev on 19.03.17.
 */
public class DtoTransformer {

//    public static User transform(UserDTO userDTO) {
//
//        User user = new User(
//                userDTO.getUserID(),
//                userDTO.getUserType(),
//                userDTO.getLogin(),
//                userDTO.getPassword(),
//                transform(userDTO.getPerson()),
//                userDTO.isBlocked());
//
//        return user;
//    }
//
//    public static Person transform(PersonDTO personDTO) {
//
//        Set<Language> languages = new HashSet<>();
//
//        for (LanguageDTO lang:
//                personDTO.getLanguages()) {
//            languages.add(transform(lang));
//        }
//        Person person = new Person(
//                personDTO.getId(),
//                personDTO.getFirstName(),
//                personDTO.getLastName(),
//                personDTO.getEmail(),
//                personDTO.getPhoneNumber(),
//                personDTO.getBirthday(),
//                personDTO.isMale());
//        person.setLanguages(languages);
//
//        return person;
//    }
//
//    public static Message transform(MessageDTO messageDTO) {
//
//        Message message = new Message(
//                messageDTO.getId(),
//                messageDTO.getDate(),
//                transform(messageDTO.getFromUser()),
//                transform(messageDTO.getToUser()),
//                messageDTO.getBodyText(),
//                messageDTO.isViewed(),
//                messageDTO.getChatRoom());
//        return message;
//    }
//
//    public static Language transform(LanguageDTO languageDTO) {
//
//        Language language = new Language(
//                languageDTO.getShortName(),
//                languageDTO.getFullName(),
//                languageDTO.getDialekt());
//
//        return language;
//    }
//
//    public static Conversation transform(ConversationDTO conversationDTO) {
//
//        Conversation conversation =
//                new Conversation(conversationDTO.getId(),
//                        conversationDTO.getChatrooom(),
//                        conversationDTO.getStartTime(),
//                        conversationDTO.getEndTime(),
//                        conversationDTO.getGradeConverse());
//        Set<User> users = new HashSet<>();
//
//        for (UserDTO u:
//                conversationDTO.getUsers()) {
//            users.add(transform(u));
//        }
//
//        conversation.setUsers(users);
//
//        return conversation;
//    }
}
