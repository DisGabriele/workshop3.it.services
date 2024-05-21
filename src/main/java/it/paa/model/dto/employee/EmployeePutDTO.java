package it.paa.model.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeePutDTO {
    private String name;

    private String surname;

    @JsonProperty("role_name")
    private String roleName;

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

    public boolean allEmpty() {
        return this.name == null &&
                this.surname == null &&
                this.hiringDate == null &&
                this.roleName == null &&
                this.salary == null;
    }
}
