package it.paa.model.dto.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public class RoleDTO {
    @NotBlank(message = "role name cannot be blank")
    private String name;

    @PositiveOrZero(message = "minimum salary cannot be negative")
    @JsonProperty("min_salary")
    private Integer minSalary;

    public RoleDTO(){}

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
