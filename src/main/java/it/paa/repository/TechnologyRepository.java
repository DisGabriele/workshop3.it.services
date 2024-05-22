package it.paa.repository;

import it.paa.model.entity.Technology;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.NoContentException;

import java.util.List;

public interface TechnologyRepository {
    List<Technology> getAll(String name, Integer minExperienceLevel) throws NoContentException;
    Technology getById(Long id) throws NotFoundException;
    Technology save(Technology technology) throws ConstraintViolationException;
    Technology update(Technology technology) throws ConstraintViolationException;
    void delete(Long id) throws NotFoundException;
}
