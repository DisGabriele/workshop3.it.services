package it.paa.model.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/*
DTO per la POST di cstomer. differenze con oggetto originale:
id assente;
id di employee invece dell'oggetto intero
*/
public class CustomerPostDTO {
    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotBlank(message = "sector cannot be empty")
    private String sector;

    private String address;

    @JsonProperty("contact_person_id")
    private Long employeeId;

    public CustomerPostDTO() {}

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
}
