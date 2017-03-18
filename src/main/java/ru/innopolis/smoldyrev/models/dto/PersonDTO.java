package ru.innopolis.smoldyrev.models.dto;

import org.hibernate.annotations.Type;
import ru.innopolis.smoldyrev.models.pojo.Language;
import ru.innopolis.smoldyrev.models.pojo.Person;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by smoldyrev on 16.02.17.
 * Класс сущности, хранимой в таблице d_Persones
 * реализованы геттеры и сеттеры всех полей
 */

@Entity
@Table(name = "d_persons", schema = "main", catalog = "LFLChat")
public class PersonDTO {

    @Id
    @GeneratedValue
    @Column(name = "person_id")
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "male")
    private boolean male;
    @Column(name = "birthday")
    @Type(type="date")
    private Date birthday;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
    @JoinTable(name = "r_langowner", schema = "main", joinColumns = {
            @JoinColumn(name = "person_id", nullable = false, updatable = false)
    }, inverseJoinColumns = {@JoinColumn(name = "lang_id", nullable = false, updatable = false)})
    private Set<LanguageDTO> languages = new HashSet<>();

    public PersonDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set<LanguageDTO> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<LanguageDTO> languages) {
        this.languages = languages;
    }

    public void addLanguage(LanguageDTO language) {
        this.languages.add(language);
    }

    public Person transformToPerson(){

        Set<Language> languages = new HashSet<>();

        for (LanguageDTO lang:
             this.languages) {
            languages.add(lang.transformToLanguage());
        }
        Person person = new Person(
                id,
                firstName,
                lastName,
                email,
                phoneNumber,
                birthday,
                male);
        person.setLanguages(languages);

        return person;
    }
}
