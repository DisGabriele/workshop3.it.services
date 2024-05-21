package it.paa.model.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeUpdateDTO {
    private String name;

    private String surname;

    @JsonProperty("role_name")
    private String roleName;

    @JsonProperty("hiring_date")
    private String hiringDate;

    private Integer salary;

    public EmployeeUpdateDTO() {
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
        return (this.name == null || this.name.isEmpty() || this.name.isBlank()) &&
                (this.surname == null || this.surname.isEmpty() || this.surname.isBlank()) &&
                (this.hiringDate == null || this.hiringDate.isEmpty() || this.hiringDate.isBlank()) &&
                (this.roleName == null || this.roleName.isEmpty() || this.roleName.isBlank()) &&
                (this.salary == null);
    }
}
