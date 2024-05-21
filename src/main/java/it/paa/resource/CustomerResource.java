package it.paa.resource;

import it.paa.model.dto.customer.CustomerPostDTO;
import it.paa.model.dto.customer.CustomerPutDTO;
import it.paa.model.entity.Customer;
import it.paa.model.entity.Employee;
import it.paa.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NoContentException;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/customers")
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @GET
    public Response getAll(@QueryParam("name") String name, @QueryParam("sector") String sector) {
        try {
            List<Customer> customerList = customerService.getAll(name, sector);
            return Response.ok(customerList).build();
        } catch (NoContentException e) {
            return Response.noContent()
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/customer_id/{customer_id}")
    public Response getById(@PathParam("customer_id") Long customer_id) {
        try {
            Customer customer = customerService.getById(customer_id);
            return Response.ok(customer)
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid CustomerPostDTO customerDTO) {
        if (customerDTO == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        Employee employee = null;
        if (customerDTO.getEmployeeId() != null) {
            try {
                employee = customerService.getEmployeeById(customerDTO.getEmployeeId());
            } catch (NotFoundException e) {
                return Response.status(Response.Status.NOT_FOUND)
                        .type(MediaType.TEXT_PLAIN)
                        .entity(e.getMessage())
                        .build();
            }
        }

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setSector(customerDTO.getSector());
        customer.setAddress(customerDTO.getAddress());

        if (employee != null)
            customer.setEmployee(employee);

        try {
            return Response.status(Response.Status.CREATED)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(customerService.save(customer))
                    .build();
        } catch (ConstraintViolationException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/customer_id/{customer_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("customer_id") Long customer_id, CustomerPutDTO customerDTO) {
        if (customerDTO == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        if (customerDTO.isAllEmpty())
            return Response.status(Response.Status.NOT_MODIFIED).build();

        try {
            Customer customer = customerService.getById(customer_id);

            if (customerDTO.getName() != null)
                customer.setName(customerDTO.getName());

            if (customerDTO.getSector() != null)
                customer.setSector(customerDTO.getSector());

            if (customerDTO.getAddress() != null)
                customer.setAddress(customerDTO.getAddress());

            if (customerDTO.getEmployeeId() != null) {
                try {
                    Employee employee = customerService.getEmployeeById(customerDTO.getEmployeeId());
                    customer.setEmployee(employee);
                } catch (NotFoundException e) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .type(MediaType.TEXT_PLAIN)
                            .entity(e.getMessage())
                            .build();
                }
            }

            try {
                return Response.ok(customerService.update(customer)).build();
            } catch (ConstraintViolationException e) {
                return Response.status(Response.Status.BAD_REQUEST)
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

    @PUT
    @Path("/customer_id/{customer_id}/remove_contact_person")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeContactPerson(@PathParam("customer_id") Long customer_id) {
        try {
            Customer customer = customerService.getById(customer_id);
            customer.setEmployee(null);

            try {
                return Response.ok(customerService.update(customer)).build();
            } catch (ConstraintViolationException e) {
                return Response.status(Response.Status.BAD_REQUEST)
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
    @Path("//customer_id/{customer_id}")
    public Response delete(@PathParam("customer_id") Long customer_id) {
        try{
            customerService.delete(customer_id);
            return Response.ok().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }
    }
}
