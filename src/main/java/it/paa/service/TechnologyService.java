package it.paa.service;

import it.paa.model.dto.technology.TechnologyProjectsDTO;
import it.paa.model.entity.Customer;
import it.paa.model.entity.Project;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class TechnologyService implements TechnologyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /*
    get all che o restituisce la lista intera o filtrata se vengono passati i filtri
    */
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


        if (name != null && !name.isEmpty() && !name.isBlank()) {
            tQuery.setParameter("name", name);
        }

        if (minExperienceLevel != null) {
            tQuery.setParameter("minExperienceLevel", minExperienceLevel);
        }

        List<Technology> technologiesList = tQuery.getResultList();

        if (technologiesList == null || technologiesList.isEmpty()) {
            throw new NoContentException("no technologies found");

        }

        return technologiesList;
    }

    /*
    get by id che torna l'eccezione se non trova l'oggetto
    */
    @Override
    public Technology getById(Long id) throws NotFoundException {
        Technology technology = entityManager.find(Technology.class, id);

        if (technology == null) {
            throw new NotFoundException("technology not found");
        }

        return technology;
    }

    /*
    save che se vengono violati dei validatori, torna l'eccezione
    */
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

    /*
    update che se vengono violati dei validatori, torna l'eccezione
    */
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

    /*
    delete che torna eccezione se l'oggetto ha associazioni o se non lo trova
    */
    @Override
    @Transactional
    public void delete(Long id) throws NotFoundException {
        Technology technology = getById(id);
        entityManager.remove(technology);
    }

    /*
    metodo usato per la 2° esercitazione (tecnologia piu' richiesta)
    */

    public List<TechnologyProjectsDTO> get5MostRequestedTechnology() {
        /*
        ricerca tecnologie che hanno almeno un dipendente e di quei dipendenti
        prendo solo quelli che hanno almeno un cliente e un progetto
        */
        String query = "select distinct t.* from technologies t" +
                " inner join technology_employee te on te.technology_id = t.id" +
                " inner join customers c on c.contact_person = te.employee_id" +
                " inner join project_employee pe on pe.employee_id = te.employee_id";

        List<Technology> technologiesList = entityManager.createNativeQuery(query, Technology.class).getResultList();

        /*
        ordinamento di queste tecnologie in base alla somma del numero dei clienti dei dipendenti associati
        */
        List<Technology> sortedTechnologies = technologiesList.stream()
                .sorted((t1, t2) -> {
                    List<Customer> customerList1 = t1.getEmployeesList().stream()
                            .flatMap(employee -> employee.getCustomerList().stream())
                            .toList();

                    List<Customer> customerList2 = t2.getEmployeesList().stream()
                            .flatMap(employee -> employee.getCustomerList().stream())
                            .toList();

                    return customerList2.size() - customerList1.size();
                })
                .limit(5)
                .toList();
        /*
        ritorno della lista del dto con tecnologia, numero clienti e lista progetti
        */
        List<TechnologyProjectsDTO> technologyProjectsDTOList = new ArrayList<>();

        sortedTechnologies.forEach(technology -> {
            TechnologyProjectsDTO technologyProjectsDTO = new TechnologyProjectsDTO();
            technologyProjectsDTO.setTechnology(technology);

            /*
            set progetti per togliere duplicati in caso di piu' dipendenti sugli stessi progetti
            */
            Set<Project> projectSet = technology.getEmployeesList().stream()
                    .flatMap(employee -> employee.getProjectList().stream())
                    .collect(Collectors.toSet());
            technologyProjectsDTO.setProjectList(projectSet);

            //calcolarmi la somma dei clienti associati ai dipentente
            int count = technology.getEmployeesList().stream()
                    .map(employee -> employee.getCustomerList().size())
                    .reduce(Integer::sum)
                    .orElse(0);
            technologyProjectsDTO.setClientCount(count);

            technologyProjectsDTOList.add(technologyProjectsDTO);
        });

        return technologyProjectsDTOList;
    }
}
