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


    private String shortName;
    private String fullName;
    private String dialekt;
    private Integer version;

    public LanguageDTO() {
    }

    @Id
    @Column(name = "short_name")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "dialekt")
    public String getDialekt() {
        return dialekt;
    }

    public void setDialekt(String dialekt) {
        this.dialekt = dialekt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
