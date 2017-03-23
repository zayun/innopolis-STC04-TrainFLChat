package ru.innopolis.smoldyrev.models.dto;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ru.innopolis.smoldyrev.common.utilities.LocalDateConverter;
import ru.innopolis.smoldyrev.models.entity.*;
import ru.innopolis.smoldyrev.models.pojo.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Transformer {

    private final static MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

    public static User user(UserEntity entity) {
        MAPPER_FACTORY.classMap(UserEntity.class, User.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, User.class);
    }

    public static List<User> user(Collection<UserEntity> listEntities) {
        MAPPER_FACTORY.classMap(UserEntity.class, User.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<User> listUsers = new ArrayList<>();
        for (UserEntity entity : listEntities) {
            listUsers.add(mapper.map(entity, User.class));
        }
        return listUsers;
    }

    public static UserEntity user(User pojo) {
        MAPPER_FACTORY.classMap(User.class, UserEntity.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(pojo, UserEntity.class);
    }

    public static Person person(PersonEntity entity) {
        MAPPER_FACTORY.classMap(PersonEntity.class, Person.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Person.class);
    }

    public static List<Person> person(Collection<PersonEntity> listEntities) {
        MAPPER_FACTORY.classMap(PersonEntity.class, Person.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Person> listPersons = new ArrayList<>();
        for (PersonEntity entity : listEntities) {
            listPersons.add(mapper.map(entity, Person.class));
        }
        return listPersons;
    }

    public static PersonEntity person(Person pojo) {
        MAPPER_FACTORY.classMap(Person.class, PersonEntity.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(pojo, PersonEntity.class);
    }

    public static Message message(MessageEntity entity) {
        MAPPER_FACTORY.classMap(MessageEntity.class, Message.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Message.class);
    }

    public static List<Message> message(Collection<MessageEntity> listEntities) {
        MAPPER_FACTORY.classMap(MessageEntity.class, Message.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Message> listMessages = new ArrayList<>();
        for (MessageEntity entity : listEntities) {
            listMessages.add(mapper.map(entity, Message.class));
        }
        return listMessages;
    }

    public static MessageEntity message(Message pojo) {
        MAPPER_FACTORY.classMap(Message.class, MessageEntity.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(pojo, MessageEntity.class);
    }

    public static Language language(LanguageEntity entity) {
        MAPPER_FACTORY.classMap(LanguageEntity.class, Language.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Language.class);
    }

    public static List<Language> language(Collection<LanguageEntity> listEntities) {
        MAPPER_FACTORY.classMap(LanguageEntity.class, Language.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Language> listLanguages = new ArrayList<>();
        for (LanguageEntity entity : listEntities) {
            listLanguages.add(mapper.map(entity, Language.class));
        }
        return listLanguages;
    }

    public static Conversation conversation(ConversationEntity entity) {
        MAPPER_FACTORY.classMap(ConversationEntity.class, Conversation.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(entity, Conversation.class);
    }

    public static List<Conversation> conversation(Collection<ConversationEntity> listEntities) {
        MAPPER_FACTORY.classMap(ConversationEntity.class, Conversation.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        List<Conversation> listConversations = new ArrayList<>();
        for (ConversationEntity entity : listEntities) {
            listConversations.add(mapper.map(entity, Conversation.class));
        }
        return listConversations;
    }

    public static ConversationEntity conversation(Conversation pojo) {
        MAPPER_FACTORY.classMap(Conversation.class, ConversationEntity.class);
        MapperFacade mapper = MAPPER_FACTORY.getMapperFacade();
        return mapper.map(pojo, ConversationEntity.class);
    }

}
