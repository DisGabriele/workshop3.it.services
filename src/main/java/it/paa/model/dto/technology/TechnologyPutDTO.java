package it.paa.model.dto.technology;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
DTO per la PUT di technology. differenze con oggetto originale:
possibilità di non mettere per forza tutti i dati che normalmente devono essere not null nella POST;
*/
public class TechnologyPutDTO {
    private String name;
    private String description;

    @JsonProperty("minimum_experience_level")
    private Integer minExperienceLevel;

    public TechnologyPutDTO() {}

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

    //usato per la put per verificare se l'oggetto non sia cambiato, per dare response 304
    @JsonIgnore
    public boolean isAllEmpty() {
        return name == null &&
                description == null &&
                minExperienceLevel == null;
    }
}
