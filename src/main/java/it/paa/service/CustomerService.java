package it.paa.service;

import it.paa.model.entity.Customer;
import it.paa.model.entity.Employee;
import it.paa.repository.CustomerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.NoContentException;

import java.util.List;

@ApplicationScoped
public class CustomerService implements CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Customer> getAll(String name, String sector) throws NoContentException {
        String query = "SELECT c FROM Customer c";

        if(name!=null && !name.isEmpty() && !name.isBlank())
            query += " WHERE LOWER(c.name) = LOWER(:name)";

        if(sector!=null && !sector.isEmpty() && !sector.isBlank()){
            if(!query.contains("WHERE"))
                query += " WHERE LOWER(c.sector) = LOWER(:sector)";
            else
                query += " AND LOWER(c.sector) = LOWER(:sector)";
        }

        TypedQuery<Customer> tQuery = entityManager.createQuery(query, Customer.class);

        if(name!=null && !name.isEmpty() && !name.isBlank())
            tQuery.setParameter("name", name);

        if(sector!=null && !sector.isEmpty() && !sector.isBlank())
            tQuery.setParameter("sector", sector);

        List<Customer> customerList = tQuery.getResultList();

        if(customerList == null || customerList.isEmpty())
            throw new NoContentException("no customers found");

        return customerList;
    }

    @Override
    public Customer getById(Long id) throws NotFoundException {
        Customer customer = entityManager.find(Customer.class, id);

        if(customer == null)
            throw new NotFoundException("customer not found");

        return customer;
    }

    @Override
    @Transactional
    public Customer save(Customer customer) throws ConstraintViolationException {
        try {
            entityManager.persist(customer);
            entityManager.flush();

            return customer;
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getConstraintViolations());
        }
    }

    @Override
    @Transactional
    public Customer update(Customer customer) throws ConstraintViolationException {
        try {
            entityManager.merge(customer);
            entityManager.flush();

            return customer;
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getConstraintViolations());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws ConstraintViolationException {
        Customer customer = getById(id);
        entityManager.remove(customer);
    }

    public Employee getEmployeeById(Long id) throws NotFoundException {
        Employee employee = entityManager.find(Employee.class, id);

        if (employee == null)
            throw new NotFoundException("employee not found");

        return employee;
    }

}
