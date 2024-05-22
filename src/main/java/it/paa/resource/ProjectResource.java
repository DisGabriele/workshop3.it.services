package it.paa.resource;

import it.paa.model.dto.project.ProjectPostDTO;
import it.paa.model.dto.project.ProjectPutDTO;
import it.paa.model.entity.Employee;
import it.paa.model.entity.Project;
import it.paa.service.ProjectService;
import it.paa.util.DateStringParser;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NoContentException;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Path("/projects")
public class ProjectResource {

    @Inject
    ProjectService projectService;

    @GET
    public Response getAll(@QueryParam("name") String name, @QueryParam("start date") String startDateString, @QueryParam("end date") String endDateString) {
        LocalDate startDate = null;
        LocalDate endDate = null;

        if (startDateString != null) {
            try {
                startDate = DateStringParser.parse(startDateString);
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.TEXT_PLAIN)
                        .entity("start date: " + e.getMessage())
                        .build();
            }
        }

        if (endDateString != null) {
            try {
                endDate = DateStringParser.parse(endDateString);
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.TEXT_PLAIN)
                        .entity("end date: " + e.getMessage())
                        .build();
            }
        }

        try {
            List<Project> projectList = projectService.getAll(name, startDate, endDate);
            return Response.ok(projectList).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        } catch (NoContentException e) {
            return Response.noContent()
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }

    @GET
    @Path("/project_id/{project_id}")
    public Response getById(@PathParam("project_id") Long projectId) {
        try {
            Project project = projectService.getById(projectId);
            return Response.ok(project).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/project_id/{project_id}/employees")
    public Response getEmployees(@PathParam("project_id") Long projectId) {
        Project project;
        try{
            project = projectService.getById(projectId);
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
        Set<Employee> employeeList = project.getEmployeesList();

        if(employeeList.isEmpty()){
            return Response.noContent()
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        return Response.ok(employeeList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid ProjectPostDTO projectDTO) {
        if (projectDTO == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        LocalDate startDate = null;
        LocalDate endDate = null;

        if (projectDTO.getStartDate() != null) {
            try {
                startDate = DateStringParser.parse(projectDTO.getStartDate());
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.TEXT_PLAIN)
                        .entity("start date: " + e.getMessage())
                        .build();
            }
        }

        if (projectDTO.getEndDate() != null) {
            try {
                endDate = DateStringParser.parse(projectDTO.getEndDate());
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.TEXT_PLAIN)
                        .entity("end date: " + e.getMessage())
                        .build();
            }
        }

        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(startDate);
        project.setEndDate(endDate);

        try {
            return Response.status(Response.Status.CREATED)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(projectService.save(project))
                    .build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/project_id/{project_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("project_id") Long projectId, ProjectPutDTO projectDTO) {
        if (projectDTO == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (projectDTO.isAllEmpty())
            return Response.status(Response.Status.NOT_MODIFIED).build();

        Project old;
        try {
            old = projectService.getById(projectId);
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }

        if (projectDTO.getName() != null)
            old.setName(projectDTO.getName());

        if (projectDTO.getDescription() != null)
            old.setDescription(projectDTO.getDescription());

        if (projectDTO.getStartDate() != null) {
            LocalDate startDate = null;
            try {
                startDate = DateStringParser.parse(projectDTO.getStartDate());
                old.setStartDate(startDate);
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.TEXT_PLAIN)
                        .entity("start date: " + e.getMessage())
                        .build();
            }
        }

        if (projectDTO.getEndDate() != null) {
            LocalDate endDate = null;
            try {
                endDate = DateStringParser.parse(projectDTO.getStartDate());
                old.setEndDate(endDate);
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.TEXT_PLAIN)
                        .entity("end date: " + e.getMessage())
                        .build();
            }
        }

        try {
            return Response.ok(projectService.update(old)).build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/project_id/{project_id}/add_eployee/{employee_id}")
    public Response addEmployee(@PathParam("project_id") Long projectId, @PathParam("employee_id") Long employeeId) {
        try {
            projectService.addEmployee(projectId, employeeId);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/project_id/{project_id}/remove_eployee/{employee_id}")
    public Response removeEmployee(@PathParam("project_id") Long projectId, @PathParam("employee_id") Long employeeId) {
        try {
            projectService.removeEmployee(projectId, employeeId);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/project_id/{project_id}")
    public Response delete(@PathParam("project_id") Long projectId) {
        try {
            projectService.delete(projectId);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
