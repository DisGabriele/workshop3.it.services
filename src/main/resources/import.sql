
insert into roles (id,name,minimum_salary)values
(1, 'name1',0),
(2, 'name2',10),
(3, 'name3',20);
alter sequence roles_seq restart with 4;

insert into employees (id,name,surname,hiring_date,role,salary) values
(1, 'name1', 'surname1','01-01-2001',1,0),
(2, 'name2', 'surname2','02-02-2002',2,10),
(3, 'name3', 'surname3','03-03-2003',3,20);
alter sequence employees_seq restart with 4;

insert into customers (id, name, sector, address, contact_person) values
(1, 'name1', 'sector1','address1',1),
(2, 'name2', 'sector2','address2',2),
(3, 'name3', 'sector3','address3',3);
alter sequence customers_seq restart with 4;