package it.paa.model.dto.employee;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class EmployeePostDTO {
    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "surname cannot be empty")
    private String surname;

    @NotBlank(message = "role cannot be empty")
    @JsonProperty("role_name")
    private String roleName;

    @PositiveOrZero(message = "experience level cannot be negative")
    @JsonProperty("experience_level")
    private Integer experienceLevel;

    @NotBlank(message = "hiring_date cannot be empty")
    @JsonProperty("hiring_date")
    private String hiringDate;

    private Integer salary;

    public EmployeePostDTO() {}

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

    public Integer getExperienceLevel() {return experienceLevel;}

    public void setExperienceLevel(Integer experienceLevel) { this.experienceLevel = experienceLevel; }

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
}
