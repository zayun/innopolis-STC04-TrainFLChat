package ru.innopolis.smoldyrev.models.dto;

import ru.innopolis.smoldyrev.models.pojo.Language;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by smoldyrev on 18.03.17.
 */
@Entity
@Table(name = "d_languages", schema = "main", catalog = "LFLChat")
public class LanguageDTO {

    @Id
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "dialekt")
    private String dialekt;

    public LanguageDTO() {
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDialekt() {
        return dialekt;
    }

    public void setDialekt(String dialekt) {
        this.dialekt = dialekt;
    }

    public Language transformToLanguage(){
        Language language = new Language(shortName,fullName,dialekt);
        return language;
    }

}
