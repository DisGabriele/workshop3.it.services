package it.paa.repository;

import it.paa.model.entity.Customer;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.NoContentException;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getAll(String name, String sector) throws NoContentException,IllegalArgumentException;
    Customer getById(Long id) throws NotFoundException;
    Customer save(Customer customer) throws ConstraintViolationException;
    Customer update(Customer customer) throws ConstraintViolationException;
    void delete(Long id) throws ConstraintViolationException;
}
