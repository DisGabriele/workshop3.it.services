package it.paa.model.dto.role;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RolePutDTO {
    private String name;
    private Integer minSalary;

    public RolePutDTO() {}

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

    @JsonIgnore
    public boolean isAllEmpty() {
        return this.name == null &&
                this.minSalary == null;
    }
}
