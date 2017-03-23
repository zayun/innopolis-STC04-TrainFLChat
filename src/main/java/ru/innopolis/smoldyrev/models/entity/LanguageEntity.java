package ru.innopolis.smoldyrev.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by smoldyrev on 18.03.17.
 */
@Entity
@Table(name = "d_languages", schema = "main", catalog = "LFLChat")
public class LanguageEntity {


    private String shortName;
    private String fullName;
    private String dialekt;
    private Integer version;

    public LanguageEntity() {
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
