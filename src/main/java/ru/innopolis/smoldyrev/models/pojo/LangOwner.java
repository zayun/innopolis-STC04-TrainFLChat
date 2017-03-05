package ru.innopolis.smoldyrev.models.pojo;

/**
 * Created by smoldyrev on 16.02.17.
 * Класс сущности, хранимой в таблице r_LangOwners
 * реализованы геттеры и сеттеры всех полей
 */
public class LangOwner {

    private Person person;
    private Language language;
    private int level;

    /**Дефолтный конструктор
     * требуется для JAXB
     * */
    public LangOwner() {
    }

    public LangOwner(Person person, Language language, int level) {
        this.person = person;
        this.language = language;
        this.level = level;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
