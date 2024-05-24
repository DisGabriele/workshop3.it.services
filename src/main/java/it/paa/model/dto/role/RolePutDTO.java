package it.paa.model.dto.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
DTO per la PUT di role. differenze con oggetto originale:
possibilità di non mettere per forza tutti i dati che normalmente devono essere not null nella POST;
*/
public class RolePutDTO {
    private String name;
    @JsonProperty("min_salary")
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

    //usato per la put per verificare se l'oggetto non sia cambiato, per dare response 304
    @JsonIgnore
    public boolean isAllEmpty() {
        return this.name == null &&
                this.minSalary == null;
    }
}
