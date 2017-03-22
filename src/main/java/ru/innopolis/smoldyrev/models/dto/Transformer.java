package ru.innopolis.smoldyrev.models.dto;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ru.innopolis.smoldyrev.common.utilities.LocalDateConverter;
import ru.innopolis.smoldyrev.models.pojo.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Transformer {

    private final static MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

    public static User user(UserDTO entity) {
        MAPPER_FACTORY.classMap(UserDTO.class, User.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, User.class);
    }

    public static List<User> user(Collection<UserDTO> listEntities) {
        MAPPER_FACTORY.classMap(UserDTO.class, User.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<User> listUsers = new ArrayList<>();
        for (UserDTO entity : listEntities) {
            listUsers.add(mapper.map(entity, User.class));
        }
        return listUsers;
    }

    public static UserDTO user(User pojo) {
        MAPPER_FACTORY.classMap(User.class, UserDTO.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(pojo, UserDTO.class);
    }

    public static Person person(PersonDTO entity) {
        MAPPER_FACTORY.classMap(PersonDTO.class, Person.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Person.class);
    }

    public static List<Person> person(Collection<PersonDTO> listEntities) {
        MAPPER_FACTORY.classMap(PersonDTO.class, Person.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Person> listPersons = new ArrayList<>();
        for (PersonDTO entity : listEntities) {
            listPersons.add(mapper.map(entity, Person.class));
        }
        return listPersons;
    }

    public static PersonDTO person(Person pojo) {
        MAPPER_FACTORY.classMap(Person.class, PersonDTO.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(pojo, PersonDTO.class);
    }

    public static Message message(MessageDTO entity) {
        MAPPER_FACTORY.classMap(MessageDTO.class, Message.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Message.class);
    }

    public static List<Message> message(Collection<MessageDTO> listEntities) {
        MAPPER_FACTORY.classMap(MessageDTO.class, Message.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Message> listMessages = new ArrayList<>();
        for (MessageDTO entity : listEntities) {
            listMessages.add(mapper.map(entity, Message.class));
        }
        return listMessages;
    }

    public static MessageDTO message(Message pojo) {
        MAPPER_FACTORY.classMap(Message.class, MessageDTO.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(pojo, MessageDTO.class);
    }

    public static Language language(LanguageDTO entity) {
        MAPPER_FACTORY.classMap(LanguageDTO.class, Language.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Language.class);
    }

    public static List<Language> language(Collection<LanguageDTO> listEntities) {
        MAPPER_FACTORY.classMap(LanguageDTO.class, Language.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Language> listLanguages = new ArrayList<>();
        for (LanguageDTO entity : listEntities) {
            listLanguages.add(mapper.map(entity, Language.class));
        }
        return listLanguages;
    }

    public static Conversation conversation(ConversationDTO entity) {
        MAPPER_FACTORY.classMap(ConversationDTO.class, Conversation.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Conversation.class);
    }

    public static List<Conversation> conversation(Collection<ConversationDTO> listEntities) {
        MAPPER_FACTORY.classMap(LanguageDTO.class, Conversation.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Conversation> listConversations = new ArrayList<>();
        for (ConversationDTO entity : listEntities) {
            listConversations.add(mapper.map(entity, Conversation.class));
        }
        return listConversations;
    }

    public static ConversationDTO conversation(Conversation pojo) {
        MAPPER_FACTORY.classMap(Conversation.class, ConversationDTO.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(pojo, ConversationDTO.class);
    }

}
