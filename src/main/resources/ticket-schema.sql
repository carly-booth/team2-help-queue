drop table if exists ticket CASCADE;
create table ticket (id bigint PRIMARY KEY AUTO_INCREMENT, created timestamp, name varchar(255), problem_description varchar(255), solution varchar(255))