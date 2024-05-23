package it.paa.resource;

import it.paa.model.dto.technology.TechnologyPostDTO;
import it.paa.model.dto.technology.TechnologyProjectsDTO;
import it.paa.model.dto.technology.TechnologyPutDTO;
import it.paa.model.entity.Employee;
import it.paa.model.entity.Technology;
import it.paa.service.TechnologyService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NoContentException;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Set;

@Path("/technologies")
public class TechnologyResource {
    @Inject
    TechnologyService technologyService;

    @GET
    public Response getAll(@QueryParam("name") String name, @QueryParam("minimum experience level") Integer minimumExperienceLevel) {
        try{
            List<Technology> technologiesList = technologyService.getAll(name,minimumExperienceLevel);
            return Response.ok(technologiesList).build();
        } catch (NoContentException e) {
            return Response.noContent()
                    .build();
        }
    }

    @GET
    @Path("/techology_id/{technology_id}")
    public Response getTechnologyById(@PathParam("technology_id") Long technologyId) {
        try{
            Technology technology = technologyService.getById(technologyId);
            return Response.ok(technology).build();
        } catch(NotFoundException e){
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/technology_id/{technology_id}/employees")
    public Response getEmployees(@PathParam("technology_id") Long technologyId) {
        Technology technology;
        try{
            technology = technologyService.getById(technologyId);
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
        Set<Employee> employeeList = technology.getEmployeesList();

        if(employeeList.isEmpty()){
            return Response.noContent()
                    .build();
        }

        return Response.ok(employeeList).build();
    }

    @GET
    @Path("/5_most_requested")
    public Response getMostRequestedTechnologiy() {
        List<TechnologyProjectsDTO> technologiesList = technologyService.getMostRequestedTechnology();

        return Response.ok(technologiesList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTechnology(@Valid TechnologyPostDTO technologyDTO) {
        if(technologyDTO == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        Technology technology = new Technology();
        technology.setName(technologyDTO.getName());
        technology.setDescription(technologyDTO.getDescription());
        technology.setMinExperienceLevel(technologyDTO.getMinExperienceLevel());

        try{
            return Response.status(Response.Status.CREATED)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(technologyService.save(technology))
                    .build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/technology_id/{technology_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("technology_id") Long technologyId, TechnologyPutDTO technologyDTO) {
        if(technologyDTO == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (technologyDTO.isAllEmpty())
            return Response.status(Response.Status.NOT_MODIFIED).build();

        Technology old;
        try{
            old = technologyService.getById(technologyId);
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }

        if(technologyDTO.getName() != null)
            old.setName(technologyDTO.getName());

        if(technologyDTO.getDescription() != null)
            old.setDescription(technologyDTO.getDescription());

        if(technologyDTO.getMinExperienceLevel() != null)
            old.setMinExperienceLevel(technologyDTO.getMinExperienceLevel());

        try{
            return Response.ok(technologyService.update(old)).build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/technology_id/{technology_id}")
    public Response delete(@PathParam("technology_id") Long technologyId) {
        try{
            technologyService.delete(technologyId);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

}
