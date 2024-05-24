package it.paa.model.dto.technology;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/*
DTO per la POST di technology. differenze con oggetto originale:
id assente;
*/
public class TechnologyPostDTO {

    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "description cannot be empty")
    private String description;

    @PositiveOrZero(message = "minimum experience level cannot be negative")
    @JsonProperty("minimum_experience_level")
    private Integer minExperienceLevel;

    public TechnologyPostDTO() {}

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
}
