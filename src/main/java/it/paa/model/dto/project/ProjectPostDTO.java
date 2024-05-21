package it.paa.model.dto.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class ProjectPostDTO {

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "description cannot be empty")
    private String description;

    @Column(name="start_date")
    @JsonProperty("start_date")
    private String startDate;

    @Column(name="end_date")
    @JsonProperty("end_date")
    private String endDate;

    public ProjectPostDTO() {}

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
