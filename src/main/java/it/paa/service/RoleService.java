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
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.NoContentException;

import java.util.List;

@ApplicationScoped
public class RoleService implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> getAll(String name, Float min_salary) throws NoContentException {
        String query = "SELECT r FROM Role r";

        if(name != null && !name.isEmpty() && !name.isBlank())
            query += " WHERE LOWER(r.name) = LOWER(:name)";

        if(min_salary != null){
            if(!query.contains("WHERE"))
                query += " WHERE r.min_salary = :min_salary";
            else
                query += " AND r.min_salary = :min_salary";

        }

        TypedQuery<Role> tQuery = entityManager.createQuery(query, Role.class);

        if(name != null)
            tQuery.setParameter("name", name);

        if(min_salary != null)
            tQuery.setParameter("min_salary", min_salary);

        List<Role> roles = tQuery.getResultList();

        if(roles.isEmpty())
            throw new NoContentException("no roles found");

        return roles;
    }

    //metodo per vedere univocità del nome in ignore case per la PUT
    public boolean checkNameUnique(String name,Long id) {
        String query = "SELECT r FROM Role r WHERE LOWER(r.name) = LOWER(:name) AND r.id != :id";

        List<Role> roles = entityManager.createQuery(query, Role.class)
                .setParameter("name",name)
                .setParameter("id",id)
                .getResultList();

        return !roles.isEmpty();
    }

    @Override
    public Role getById(Long id) throws NotFoundException {
        Role role = entityManager.find(Role.class, id);

        if(role == null){
            throw new NotFoundException("role not found");
        }

        return role;
    }

    @Override
    @Transactional
    public Role save(Role role) throws PersistenceException, ConstraintViolationException {
        try {
            entityManager.persist(role);
            entityManager.flush();

            return role;
        } catch (PersistenceException e) {
            throw new PersistenceException("role with this name already exists");
        }
    }

    @Override
    @Transactional
    public Role update(Role role) throws PersistenceException, ConstraintViolationException {
            entityManager.merge(role);
            entityManager.flush();

            return role;

    }

    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        Role role = getById(id);
        entityManager.remove(role);
    }
}
