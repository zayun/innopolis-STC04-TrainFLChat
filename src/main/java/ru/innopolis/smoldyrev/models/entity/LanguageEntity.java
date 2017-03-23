package ru.innopolis.smoldyrev.models.entity;


import javax.persistence.*;

/**
 * Created by smoldyrev on 18.03.17.
 */
@Entity
@Table(name = "d_languages", schema = "main", catalog = "LFLChat")
public class LanguageEntity {


    private String shortName;
    private String fullName;
    private String dialekt;
    private int version;

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

    @Version
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
