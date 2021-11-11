drop table if exists profile;

create table profile (
	pid int primary key,
	firstname varchar not null,
	lastname varchar not null,
	email varchar not null,
	username varchar not null unique,
	passkey varchar not null
);

insert into profile (pid, firstname, lastname, email, username, passkey) values
           (1, 'test', 'test', 'test@email.com', 'test', '1234'),