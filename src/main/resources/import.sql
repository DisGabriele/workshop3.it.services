-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the databas
insert into roles (id,name,minimum_salary) values(1, 'name1',0);
insert into roles (id,name,minimum_salary) values(2, 'name2',10);
insert into roles (id,name,minimum_salary) values(3, 'name3',20);

-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;