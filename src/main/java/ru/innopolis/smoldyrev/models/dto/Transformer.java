package ru.innopolis.smoldyrev.models.dto;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ru.innopolis.smoldyrev.common.utilities.LocalDateConverter;
import ru.innopolis.smoldyrev.models.pojo.*;

import java.util.ArrayList;
import java.util.List;

public class Transformer {

    private final static MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

    static {
        MAPPER_FACTORY.getConverterFactory().registerConverter(new LocalDateConverter());
    }
    public static User userEntityToPojo(UserDTO entity) {
        MAPPER_FACTORY.classMap(UserDTO.class, User.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, User.class);
    }

    public static List<User> userEntityToPojo(List<UserDTO> listEntities) {
        MAPPER_FACTORY.classMap(UserDTO.class, User.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<User> listUsers = new ArrayList<>();
        for (UserDTO entity : listEntities) {
            listUsers.add(mapper.map(entity, User.class));
        }
        return listUsers;
    }

    public static Person personEntityToPojo(PersonDTO entity) {
        MAPPER_FACTORY.classMap(PersonDTO.class, Person.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Person.class);
    }

    public static List<Person> personEntityToPojo(List<PersonDTO> listEntities) {
        MAPPER_FACTORY.classMap(PersonDTO.class, Person.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Person> listPersons = new ArrayList<>();
        for (PersonDTO entity : listEntities) {
            listPersons.add(mapper.map(entity, Person.class));
        }
        return listPersons;
    }

    public static Message messageEntityToPojo(MessageDTO entity) {
        MAPPER_FACTORY.classMap(MessageDTO.class, Message.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Message.class);
    }

    public static List<Message> messageEntityToPojo(List<MessageDTO> listEntities) {
        MAPPER_FACTORY.classMap(MessageDTO.class, Message.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Message> listMessages = new ArrayList<>();
        for (MessageDTO entity : listEntities) {
            listMessages.add(mapper.map(entity, Message.class));
        }
        return listMessages;
    }

    public static Language languageEntityToPojo(LanguageDTO entity) {
        MAPPER_FACTORY.classMap(LanguageDTO.class, Language.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Language.class);
    }

    public static List<Language> languageEntityToPojo(List<LanguageDTO> listEntities) {
        MAPPER_FACTORY.classMap(LanguageDTO.class, Language.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Language> listLanguages = new ArrayList<>();
        for (LanguageDTO entity : listEntities) {
            listLanguages.add(mapper.map(entity, Language.class));
        }
        return listLanguages;
    }

    public static Conversation conversationEntityToPojo(ConversationDTO entity) {
        MAPPER_FACTORY.classMap(ConversationDTO.class, Conversation.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Conversation.class);
    }

    public static List<Conversation> conversationEntityToPojo(List<ConversationDTO> listEntities) {
        MAPPER_FACTORY.classMap(LanguageDTO.class, Conversation.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Conversation> listConversations = new ArrayList<>();
        for (ConversationDTO entity : listEntities) {
            listConversations.add(mapper.map(entity, Conversation.class));
        }
        return listConversations;
    }


}
