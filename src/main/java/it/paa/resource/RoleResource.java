package it.paa.resource;

import it.paa.model.entity.Role;
import it.paa.service.RoleService;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
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

    @POST
    public Response create(@Valid Role role) {
        try {
            if(role.getMinSalary() == null)
                role.setMinSalary(0);
            return Response.status(Response.Status.CREATED)
                    .entity(roleService.save(role))
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
    public Response update(@PathParam("role_id") Long id, @Valid Role role) {
        //metodo per vedere univocità del nome in ignore case per la PUT
        if (roleService.checkNameUnique(role.getName(), id))
            return Response.status(Response.Status.CONFLICT)
                    .type(MediaType.TEXT_PLAIN)
                    .entity("role with this name already exists")
                    .build();

        try {
            Role old = roleService.getById(id);
            Role newRole = new Role();
            newRole.setId(id);
            newRole.setName(role.getName());
            newRole.setMinSalary(role.getMinSalary() != null ?
                    role.getMinSalary() :
                    old.getMinSalary());

            try {
                if (newRole.oldEquals(old)) {
                    return Response.status(Response.Status.NOT_MODIFIED)
                            .build();
                } else {
                    return Response.status(Response.Status.CREATED)
                            .type(MediaType.APPLICATION_JSON)
                            .entity(roleService.update(newRole))
                            .build();
                }
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
        }
    }

}
