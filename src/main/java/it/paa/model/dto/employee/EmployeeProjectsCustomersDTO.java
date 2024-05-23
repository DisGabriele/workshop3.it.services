package it.paa.model.dto.employee;

import it.paa.model.entity.Customer;
import it.paa.model.entity.Employee;
import it.paa.model.entity.Project;

import java.util.Set;

public class EmployeeProjectsCustomersDTO {
    private Employee employee;
    private Set<Project> projects;
    private Set<Customer> customers;

    public EmployeeProjectsCustomersDTO() {}

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
}
