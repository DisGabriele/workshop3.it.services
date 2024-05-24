package it.paa.model.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
DTO per la PUT di project. differenze con oggetto originale:
possibilità di non mettere per forza tutti i dati che normalmente devono essere not null nella POST;
startDate e endDate come string per comodità, in modo da passarla nel formato mm-dd-yyyy o yyyy-mm-dd;
*/
public class ProjectPutDTO {
    private String name;

    private String description;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    public ProjectPutDTO() {}

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

    //usato per la put per verificare se l'oggetto non sia cambiato, per dare response 304
    @JsonIgnore
    public boolean isAllEmpty() {
        return name == null &&
                description == null &&
                startDate == null &&
                endDate == null;
    }
}
