package it.paa.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import it.paa.validation.ProjectDates;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "projects")
@ProjectDates
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "name cannot be empty")
    private String name;

    @Column(name = "description", nullable = false)
    @NotBlank(message = "description cannot be empty")
    private String description;

    @Column(name="start_date")
    @JsonProperty("start_date")
    private LocalDate startDate;

    @Column(name="end_date")
    @JsonProperty("end_date")
    private LocalDate endDate;

    //ManyToMany Employee

    public Project() {}

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
