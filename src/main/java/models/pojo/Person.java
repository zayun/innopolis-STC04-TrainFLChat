package models.pojo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.sql.Date;

/**
 * Created by smoldyrev on 16.02.17.
 * Класс сущности, хранимой в таблице d_Persones
 * реализованы геттеры и сеттеры всех полей
 */
@XmlType(propOrder={"firstName","lastName","male","birthDay","email","phoneNumber"})
@XmlRootElement
public class Person {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean male;
    private Date birthDay;

    /**Дефолтный конструктор
     * требуется для JAXB
     * */
    public Person() {

    }

    public Person(Integer id, String firstName, String lastName,
                  String email, String phoneNumber, Date birthDay, boolean male) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.male = male;
    }

    @XmlAttribute(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @XmlElement
    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    @XmlElement
    public Date getBirthDay() {
        return birthDay;
    }


    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

}
