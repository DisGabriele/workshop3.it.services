package it.paa.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "name cannot be empty")
    private String name;

    @Column(name = "surname", nullable = false)
    @NotBlank(message = "surname cannot be empty")
    private String surname;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private Role role;

    @Column(name = "experience_level")
    @PositiveOrZero(message = "experience level cannot be negative")
    @JsonProperty("experience_level")
    private Integer experienceLevel;

    @Column(name = "hiring_date", nullable = false)
    @JsonProperty("hiring_date")
    @NotNull(message = "hiring_date cannot be empty")
    private LocalDate hiringDate;

    @Column(name = "salary")
    private Integer salary;

    @OneToMany(mappedBy = "employee")
    @JsonBackReference
    @JsonIgnore
    private List<Customer> customerList;

    @ManyToMany(mappedBy = "employeesList")
    @JsonIgnore
    private Set<Project> projectList;


    @ManyToMany
    @JoinTable(
            name = "technology_employee",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    @JsonIgnore
    private Set<Technology> technologiesList;

    public Employee() {
    }

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(Integer experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Set<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(Set<Project> projectList) {
        this.projectList = projectList;
    }

    public Set<Technology> getTechnologiesList() {
        return technologiesList;
    }

    public void setTechnologiesList(Set<Technology> technologiesList) {
        this.technologiesList = technologiesList;
    }

    public void addTechnology(Technology technology) {
        if (technologiesList == null) {
            technologiesList = new HashSet<>();
        }
        technologiesList.add(technology);
    }

    public void removeTechnology(Technology technology) {
        if (technologiesList == null) {
            technologiesList = new HashSet<>();
        }
        technologiesList.remove(technology);
    }
}
