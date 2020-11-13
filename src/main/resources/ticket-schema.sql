drop table if exists ticket CASCADE 
create table ticket (id bigint not null, create timestamp, name varchar(255), problem_description varchar(255), solution varchar(255), primary key (id))