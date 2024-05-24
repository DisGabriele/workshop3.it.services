package it.paa.model.dto.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
DTO per la PUT di employee. differenze con oggetto originale:
possibilità di non mettere per forza tutti i dati che normalmente devono essere not null nella POST;
*/
public class EmployeePutDTO {
    private String name;

    private String surname;

    @JsonProperty("role_name")
    private String roleName;

    @JsonProperty("experience_level")
    private Integer experienceLevel;

    @JsonProperty("hiring_date")
    private String hiringDate;

    private Integer salary;

    public EmployeePutDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getExperienceLevel() { return experienceLevel;}

    public void setExperienceLevel(Integer experienceLevel) { this.experienceLevel = experienceLevel;}

    public String getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(String hiringDate) {
        this.hiringDate = hiringDate;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    //usato per la put per verificare se l'oggetto non sia cambiato, per dare response 304
    @JsonIgnore
    public boolean isAllEmpty() {
        return this.name == null &&
                this.surname == null &&
                this.hiringDate == null &&
                this.roleName == null &&
                this.experienceLevel == null &&
                this.salary == null;
    }
}
