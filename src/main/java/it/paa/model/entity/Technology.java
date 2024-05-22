package it.paa.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Set;

@Entity
@Table(name = "technologies")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "name cannot be empty")
    private String name;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "description cannot be empty")
    private String description;

    @Column(name = "minimum_experience_level")
    @PositiveOrZero(message = "minimum experience level cannot be negative")
    @JsonProperty("minimum_experience_level")
    private Integer minExperienceLevel;

    @ManyToMany
    @JoinTable(
            name = "technology_employee",
            joinColumns = @JoinColumn(name = "technology_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @JsonIgnore
    private Set<Employee> employeesList;

    public Technology() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinExperienceLevel() {
        return minExperienceLevel;
    }

    public void setMinExperienceLevel(Integer minExperienceLevel) {
        this.minExperienceLevel = minExperienceLevel;
    }

    public Set<Employee> getEmployeesList() {
        return employeesList;
    }

    public void setEmployeesList(Set<Employee> employeesList) {
        this.employeesList = employeesList;
    }

}
