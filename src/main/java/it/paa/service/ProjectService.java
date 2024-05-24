package it.paa.service;

import it.paa.model.entity.Employee;
import it.paa.model.entity.Project;
import it.paa.repository.ProjectRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.NoContentException;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class ProjectService implements ProjectRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /*
    get all che o restituisce la lista intera o filtrata se vengono passati i filtri
    */
    @Override
    public List<Project> getAll(String name, LocalDate startDate, LocalDate endDate) throws NoContentException, IllegalArgumentException {
        String query = "SELECT p FROM Project p";

        if (name != null && !name.isEmpty() && !name.isBlank()) {
            query += " WHERE LOWER(p.name) = LOWER(:name) ";
        }

        if (startDate != null) {
            if (!query.contains("WHERE"))
                query += " WHERE p.startDate = :startDate";
            else
                query += " AND p.startDate = :startDate";
        }

        if (endDate != null) {
            if (!query.contains("WHERE"))
                query += " WHERE p.endDate = :endDate";
            else
                query += " AND p.endDate = :endDate";
        }

        TypedQuery<Project> tQuery = entityManager.createQuery(query, Project.class);

        if (name != null && !name.isEmpty() && !name.isBlank())
            tQuery.setParameter("name", name);

        if (startDate != null)
            tQuery.setParameter("startDate", startDate);

        if (endDate != null)
            tQuery.setParameter("endDate", endDate);

        if (startDate != null && endDate != null && startDate.isAfter(endDate))
            throw new IllegalArgumentException("start date cannot be after end date");

        List<Project> projectList = tQuery.getResultList();

        if (projectList == null || projectList.isEmpty())
            throw new NoContentException("no projects found");

        return projectList;
    }

    /*
    get by id che torna l'eccezione se non trova l'oggetto
    */
    @Override
    public Project getById(Long id) throws NotFoundException {
        Project project = entityManager.find(Project.class, id);

        if (project == null)
            throw new NotFoundException("project not found");

        return project;
    }

    /*
    save che se vengono violati dei validatori, torna l'eccezione
    */
    @Override
    @Transactional
    public Project save(Project project) throws ConstraintViolationException {
        try {
            entityManager.persist(project);
            entityManager.flush();

            return project;
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getConstraintViolations());
        }
    }

    /*
    update che se vengono violati dei validatori, torna l'eccezione
    */
    @Override
    @Transactional
    public Project update(Project project) throws ConstraintViolationException {
        try {
            entityManager.merge(project);
            entityManager.flush();

            return project;
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getConstraintViolations());
        }
    }

    /*
    rimozione che torna l'eccezione se non trova l'oggetto
    */
    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        Project project = getById(id);
        entityManager.remove(project);
    }

    /*
    aggiunta dipendente al progetto
    */
    @Override
    @Transactional
    public void addEmployee(Long projectId, Long employeeId) throws NotFoundException,IllegalArgumentException {
        Project project = getById(projectId);
        Employee employee = getEmployeeById(employeeId);

        if(project.getEmployeesList().contains(employee))
            throw new IllegalArgumentException("project already has this employee");

        project.addEmployee(employee);
        entityManager.merge(project);
    }

    /*
    rimozione dipendente dal progetto
    */
    @Override
    @Transactional
    public void removeEmployee(Long projectId, Long employeeId) throws NotFoundException, IllegalArgumentException {
        Project project = getById(projectId);
        Employee employee = getEmployeeById(employeeId);

        if(!project.getEmployeesList().contains(employee))
            throw new IllegalArgumentException("project does not have this employee");

        project.removeEmployee(employee);
        entityManager.merge(project);
    }

    /*
    get employee usato per l'associazione con employee
    */
    public Employee getEmployeeById(Long id) throws NotFoundException {
        Employee employee = entityManager.find(Employee.class, id);

        if (employee == null)
            throw new NotFoundException("employee not found");

        return employee;
    }
}
