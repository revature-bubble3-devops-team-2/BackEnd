create table profile (
	profile_id int primary key,
	username text unique not null,
	passkey text not null,
	first_name text not null,
	last_name text not null,
	email text unique not null
)

create table post (
	post_id int primary key,
	profile_id int references profile,
	body text,
	image_url text,
	date_posted timestamp
)

create table followers(
	profile_id int references profile,
	follower_id int references profile,
	primary key(profile_id, follower_id)
)



ALTER TABLE public.profile ADD username text NOT NULL;

drop table profile;
drop table post;
drop table followers;

select * from followers where profile_id = 514459774;
select * from post where profile_id = 1555506227;