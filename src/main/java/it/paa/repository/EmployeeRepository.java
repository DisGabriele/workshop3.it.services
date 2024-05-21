package it.paa.repository;

import it.paa.model.entity.Employee;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.NoContentException;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAll(String surname, LocalDate startDate, LocalDate endDate) throws NoContentException,IllegalArgumentException;
    Employee getById(Long id) throws NotFoundException;
    Employee save(Employee employee) throws ConstraintViolationException;
    Employee update(Employee employee) throws ConstraintViolationException;
    void delete(Long id) throws NotFoundException;
}
