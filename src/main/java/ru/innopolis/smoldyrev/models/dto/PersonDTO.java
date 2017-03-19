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

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean male;
    private Date birthday;
    private Set<LanguageDTO> languages = new HashSet<>();
    private Integer version;

    public PersonDTO() {
    }

    @Id
    @GeneratedValue
    @Column(name = "person_id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "male")
    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    @Column(name = "birthday")
    @Type(type="date")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "r_langowner", schema = "main", joinColumns = {
            @JoinColumn(name = "person_id", nullable = false, updatable = false)
    }, inverseJoinColumns = {@JoinColumn(name = "lang_id", nullable = false, updatable = false)})
    public Set<LanguageDTO> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<LanguageDTO> languages) {
        this.languages = languages;
    }

    public void addLanguage(LanguageDTO language) {
        this.languages.add(language);
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
