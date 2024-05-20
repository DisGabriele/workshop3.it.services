package it.paa.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "role name cannot be blank")
    private String name;

    @Column(name = "minimum_salary")
    @PositiveOrZero(message = "minimum salary cannot be negative")
    @JsonProperty("min_salary")
    private Integer minSalary;

    public Role() {}

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

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public boolean oldEquals(Role role){
        return this.name.equals(role.getName()) &&
                this.minSalary.equals(role.getMinSalary());
    }
}
