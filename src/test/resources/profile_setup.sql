--drop table if exists profile cascade;
--
--create table profile (
--	pid int primary key,
--	firstName varchar not null,
--	lastName varchar not null,
--	email varchar not null,
--	username varchar not null unique,
--	passkey varchar not null
--);

insert into profile (pid, firstName, lastName, email, username, passkey) values
           (1, 'test', 'test', 'test@email.com', 'test', '1234');