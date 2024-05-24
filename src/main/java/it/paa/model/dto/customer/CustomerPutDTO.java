package it.paa.model.dto.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
DTO per la PUT di customer. differenze con oggetto originale:
possibilità di non mettere per forza tutti i dati che normalmente devono essere not null nella POST;
*/
public class CustomerPutDTO {
    private String name;
    private String sector;
    private String address;

    @JsonProperty("contact_person_id")
    private Long employeeId;

    public CustomerPutDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    //usato per la put per verificare se l'oggetto non sia cambiato, per dare response 304
    @JsonIgnore
    public boolean isAllEmpty() {
        return name == null &&
                sector == null &&
                address == null &&
                employeeId == null;
    }

}
