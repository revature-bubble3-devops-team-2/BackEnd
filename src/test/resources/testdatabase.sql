drop table if exists profile cascade;

create table profile (
	pid int primary key,
	username varchar(50) unique not null,
	passkey text not null,
	firstName varchar(50) not null,
	lastName varchar(50) not null,
	email varchar(50) unique not null
);

insert into Profile (pid,username,passkey,firstName,lastName,email) values (1,'pepe','123','f','l','em');

