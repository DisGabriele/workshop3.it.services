package it.paa.resource;

import it.paa.model.dto.role.RoleDTO;
import it.paa.model.dto.role.RoleUpdateDTO;
import it.paa.model.entity.Employee;
import it.paa.model.entity.Role;
import it.paa.service.RoleService;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NoContentException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/roles")
public class RoleResource {

    @Inject
    RoleService roleService;

    @GET
    public Response getAll(@QueryParam("name") String name, @QueryParam("minimum_salary") Float minSalary) {
        try {
            List<Role> roles = roleService.getAll(name, minSalary);
            return Response.ok(roles)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (NoContentException e) {
            return Response.noContent()
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/role_id/{id}")
    public Response getById(@PathParam("id") Long id) {
        try {
            Role role = roleService.getById(id);
            return Response.ok(role)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/role_id/{id}/employees")
    public Response getEmployees(@PathParam("id") Long id) {
        try {
            Role role = roleService.getById(id);

            List<Employee> employeeList = role.getEmployeeList();
            if (employeeList.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT)
                        .build();
            }

            return Response.ok(employeeList).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @POST
    public Response create(@Valid RoleDTO roleDTO) {
        try {
            Role role = new Role();
            role.setName(roleDTO.getName());
            if (roleDTO.getMinSalary() == null)
                role.setMinSalary(0);
            else
                role.setMinSalary(roleDTO.getMinSalary());
            return Response.status(Response.Status.CREATED)
                    .entity(roleService.save(role))
                    .build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.CONFLICT)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/role_id/{role_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("role_id") Long id, RoleUpdateDTO roleDto) {

        if (roleDto == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (roleDto.allEmpty())
            return Response.status(Response.Status.NOT_MODIFIED).build();


        try {
            Role old = roleService.getById(id);
            //metodo per vedere univocità del nome in ignore case per la PUT
            if (roleDto.getName() != null && !roleDto.getName().isEmpty())
                old.setName(roleDto.getName());

            if (roleDto.getMinSalary() != null)
                old.setMinSalary(roleDto.getMinSalary());

            try {
                return Response.ok(roleService.update(old)).build();
            } catch (ConstraintViolationException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.TEXT_PLAIN)
                        .entity(e.getMessage())
                        .build();
            } catch (PersistenceException e) {
                return Response.status(Response.Status.CONFLICT)
                        .type(MediaType.TEXT_PLAIN)
                        .entity(e.getMessage())
                        .build();
            }

        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/role_id/{role_id}")
    public Response delete(@PathParam("role_id") Long id) {
        try {
            roleService.delete(id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

}
