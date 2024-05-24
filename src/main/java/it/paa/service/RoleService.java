package it.paa.service;

import it.paa.model.entity.Role;
import it.paa.repository.RoleRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.NoContentException;

import java.util.List;

@ApplicationScoped
public class RoleService implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /*
    get all che o restituisce la lista intera o filtrata se vengono passati i filtri
    */
    @Override
    public List<Role> getAll(String name, Float min_salary) throws NoContentException {
        String query = "SELECT r FROM Role r";

        if (name != null && !name.isEmpty() && !name.isBlank())
            query += " WHERE LOWER(r.name) = LOWER(:name)";

        if (min_salary != null) {
            if (!query.contains("WHERE"))
                query += " WHERE r.min_salary = :min_salary";
            else
                query += " AND r.min_salary = :min_salary";

        }

        TypedQuery<Role> tQuery = entityManager.createQuery(query, Role.class);

        if (name != null)
            tQuery.setParameter("name", name);

        if (min_salary != null)
            tQuery.setParameter("min_salary", min_salary);

        List<Role> roles = tQuery.getResultList();

        if (roles == null || roles.isEmpty())
            throw new NoContentException("no roles found");

        return roles;
    }

    /*
    get by id che torna l'eccezione se non trova l'oggetto
    */
    @Override
    public Role getById(Long id) throws NotFoundException {
        Role role = entityManager.find(Role.class, id);

        if (role == null) {
            throw new NotFoundException("role not found");
        }

        return role;
    }

    /*
    save che se vengono violati dei validatori o viene inserito un nome già esistente, torna l'eccezione
    */
    @Override
    @Transactional
    public Role save(Role role) throws PersistenceException, ConstraintViolationException {
        try {
            /*
            ricerca usata per l'univocità del nome del ruolo con ignore case
            */
            String query = "SELECT r FROM Role r WHERE LOWER(r.name) = LOWER(:name)";

            List<Role> roles = entityManager.createQuery(query, Role.class)
                    .setParameter("name", role.getName())
                    .getResultList();

            if (!roles.isEmpty())
                throw new PersistenceException();

            entityManager.persist(role);
            entityManager.flush();

            return role;
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getConstraintViolations());
        } catch (PersistenceException e) {
            throw new PersistenceException("another role with this name already exists");
        }
    }

    /*
    update che se vengono violati dei validatori o viene inserito un nome già esistente, torna l'eccezione
    */
    @Override
    @Transactional
    public Role update(Role role) throws PersistenceException, ConstraintViolationException {
        try {
            /*
            ricerca usata per l'univocità del nome del ruolo con ignore case
            */
            String query = "SELECT r FROM Role r WHERE LOWER(r.name) = LOWER(:name) AND r.id != :id";

            List<Role> roles = entityManager.createQuery(query, Role.class)
                    .setParameter("name", role.getName())
                    .setParameter("id", role.getId())
                    .getResultList();

            if (!roles.isEmpty())
                throw new PersistenceException();

            entityManager.merge(role);
            entityManager.flush();

            return role;
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getConstraintViolations());
        } catch (PersistenceException e) {
            throw new PersistenceException("another role with this name already exists");
        }

    }

    /*
    delete che torna eccezione se l'oggetto ha associazioni o se non lo trova
    */
    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException, BadRequestException{
        Role role = getById(id);

        if(!role.getEmployeeList().isEmpty())
            throw new BadRequestException("cannot delete role because has associated employees");

        entityManager.remove(role);
    }
}
