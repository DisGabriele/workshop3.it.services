package it.paa.repository;

import it.paa.model.entity.Project;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.NoContentException;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository {
    List<Project> getAll(String name, LocalDate startDate, LocalDate endDate) throws NoContentException,IllegalArgumentException;
    Project getById(Long id) throws NotFoundException;
    Project save(Project project) throws ConstraintViolationException;
    Project update(Project project) throws ConstraintViolationException;
    void delete(Long id) throws NotFoundException;
}
