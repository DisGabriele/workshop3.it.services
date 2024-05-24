--script sql per inserimento di alcune colonne per ogni tabella, senza associazioni
insert into roles (id,name,minimum_salary)values
(1, 'role_name1',0),
(2, 'role_name2',10),
(3, 'role_name3',20);
alter sequence roles_seq restart with 4;

insert into employees (id,name,surname,hiring_date,role,experience_level,salary) values
(1, 'employee_name1', 'employee_surname1','01-01-2001',1,1,0),
(2, 'employee_name2', 'employee_surname2','02-02-2002',2,2,10),
(3, 'employee_name3', 'employee_surname3','03-03-2003',3,3,20);
alter sequence employees_seq restart with 4;

insert into customers (id, name, sector, address) values
(1, 'customer_name1', 'customer_sector1','customer_address1'),
(2, 'customer_name2', 'customer_sector2','customer_address2'),
(3, 'customer_name3', 'customer_sector3','customer_address3');
alter sequence customers_seq restart with 4;

insert into projects (id, name, description, start_date, end_date) values
(1,'project_name1','project_description1','01-01-2001','11-01-2001'),
(2,'project_name2','project_description2','02-02-2002','12-02-2002'),
(3,'project_name3','project_description3','03-03-2003','13-03-2003');
alter sequence projects_seq restart with 4;

insert into technologies (id, name, description, minimum_experience_level) values
(1,'technology_name1','technology_description1',1),
(2,'technology_name2','technology_description2',2),
(3,'technology_name3','technology_description3',3);
alter sequence public.technologies_seq restart with 4;