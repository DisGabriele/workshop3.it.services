package it.paa.service;

import it.paa.model.entity.Employee;
import it.paa.model.entity.Role;
import it.paa.repository.EmployeeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.NoContentException;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class EmployeeService implements EmployeeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Employee> getAll(String surname, LocalDate startDate, LocalDate endDate) throws NoContentException, IllegalArgumentException {
        String query = "SELECT e FROM Employee e";

        if (surname != null && !surname.isEmpty() && !surname.isBlank())
            query += " WHERE LOWER(e.surname) = LOWER(:surname)";

        if (startDate != null || endDate != null) {
            //CONTROLLI DATE
            if (startDate == null || endDate == null) {
                throw new IllegalArgumentException("start_date and end_date must be both empty or filled");
            }
            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("start_date cannot be after end_date ");
            }

            if (!query.contains("WHERE"))
                query += " WHERE e.hiringDate BETWEEN :startDate AND :endDate";
            else
                query += " AND e.hiringDate BETWEEN :startDate AND :endDate";
        }

        TypedQuery<Employee> tQuery = entityManager.createQuery(query, Employee.class);

        if (surname != null && !surname.isEmpty() && !surname.isBlank())
            tQuery.setParameter("surname", surname);

        //solo una perchè arrivati qua sono o entrambe null o con valore
        if (startDate != null) {
            tQuery.setParameter("startDate", startDate);
            tQuery.setParameter("endDate", endDate);
        }

        List<Employee> employeeList = tQuery.getResultList();

        if (employeeList == null || employeeList.isEmpty())
            throw new NoContentException("no employees found");

        return employeeList;
    }

    @Override
    public Employee getById(Long id) throws NotFoundException {
        Employee employee = entityManager.find(Employee.class, id);

        if (employee == null)
            throw new NotFoundException("employee not found");

        return employee;
    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        entityManager.persist(employee);
        return employee;
    }

    @Override
    @Transactional
    public Employee update(Employee employee) {
        entityManager.merge(employee);
        return employee;
    }

    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException, BadRequestException {
        Employee employee = getById(id);

        if(!employee.getCustomerList().isEmpty())
            throw new BadRequestException("cannot delete role because has associated customers");

        entityManager.remove(employee);
    }

    public Role getRoleByName(String roleName) throws NoResultException {
        try {

            return entityManager.createQuery("SELECT r FROM Role r WHERE LOWER(r.name) = LOWER(:name)", Role.class)
                    .setParameter("name", roleName)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NoResultException("role not found");
        }
    }
}
