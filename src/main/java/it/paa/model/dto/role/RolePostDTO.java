package it.paa.model.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

/*
DTO per la POST di role. differenze con oggetto originale:
id assente;
*/
public class RolePostDTO {
    @NotBlank(message = "role name cannot be empty")
    private String name;

    @PositiveOrZero(message = "minimum salary cannot be negative")
    @JsonProperty("min_salary")
    private Integer minSalary;

    public RolePostDTO(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }
}
