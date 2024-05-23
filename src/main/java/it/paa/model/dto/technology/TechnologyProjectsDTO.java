package it.paa.model.dto.technology;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import it.paa.model.entity.Project;
import it.paa.model.entity.Technology;

import java.util.Set;

@JsonPropertyOrder({"technology", "clients_count", "projectList"})
public class TechnologyProjectsDTO {
    Technology technology;
    @JsonProperty("clients_count")
    int clientCount;
    Set<Project> projectList;

    public TechnologyProjectsDTO() {}

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public int getClientCount() {
        return clientCount;
    }

    public void setClientCount(int clientCount) {
        this.clientCount = clientCount;
    }

    public Set<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(Set<Project> projectList) {
        this.projectList = projectList;
    }
}
