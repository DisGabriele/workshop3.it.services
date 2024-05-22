package it.paa.service;

import it.paa.model.entity.Technology;
import it.paa.repository.TechnologyRepository;
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
public class TechnologyService implements TechnologyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Technology> getAll(String name, Integer minExperienceLevel) throws NoContentException {
        String query = "SELECT t FROM Technology t";

        if (name != null && !name.isEmpty() && !name.isBlank()) {
            query += " WHERE LOWER(t.name) = LOWER(:name) ";
        }

        if (minExperienceLevel != null) {
            if (!query.contains("WHERE"))
                query += " WHERE t.minExperienceLevel = :minExperienceLevel";
            else
                query += " AND t.minExperienceLevel = :minExperienceLevel";
        }

        TypedQuery<Technology> tQuery = entityManager.createQuery(query, Technology.class);


        if(name != null && !name.isEmpty() && !name.isBlank()) {
            tQuery.setParameter("name", name);
        }

        if(minExperienceLevel != null) {
            tQuery.setParameter("minExperienceLevel", minExperienceLevel);
        }

        List<Technology> technologiesList = tQuery.getResultList();

        if(technologiesList == null || technologiesList.isEmpty()) {
            throw new NoContentException("no technologies found");

        }

        return technologiesList;
    }

    @Override
    public Technology getById(Long id) throws NotFoundException {
        Technology technology = entityManager.find(Technology.class, id);

        if(technology == null) {
            throw new NotFoundException("technology not found");
        }

        return technology;
    }

    @Override
    @Transactional
    public Technology save(Technology technology) throws ConstraintViolationException {
        try {
            entityManager.persist(technology);
            entityManager.flush();

            return technology;
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getConstraintViolations());
        }
    }

    @Override
    @Transactional
    public Technology update(Technology technology) throws ConstraintViolationException {
        try {
            entityManager.merge(technology);
            entityManager.flush();

            return technology;
        } catch (ConstraintViolationException e) {
            throw new ConstraintViolationException(e.getConstraintViolations());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        Technology technology = getById(id);
        entityManager.remove(technology);
    }
}
