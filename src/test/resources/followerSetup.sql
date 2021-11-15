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

insert into post (post_id, profile_id, body, image_url, date_posted) values (1,241747610,'text1','https://source.unsplash.com/random/300x300',current_date)
insert into post (post_id, profile_id, body, image_url, date_posted) values (2,1555506227,'text1','https://source.unsplash.com/random/300x300',current_date)
insert into post (post_id, profile_id, body, image_url, date_posted) values (3,1833197514,'text1','https://source.unsplash.com/random/300x300',current_date)
insert into post (post_id, profile_id, body, image_url, date_posted) values (4,2008162393,'text1','https://source.unsplash.com/random/300x300',current_date)
insert into post (post_id, profile_id, body, image_url, date_posted) values (5,1732049713,'text1','https://source.unsplash.com/random/300x300',current_date)
insert into post (post_id, profile_id, body, image_url, date_posted) values (6,1931464143,'text1','https://source.unsplash.com/random/300x300',current_date)
insert into post (post_id, profile_id, body, image_url, date_posted) values (7,2007304758,'text1','https://source.unsplash.com/random/300x300',current_date)


ALTER TABLE public.profile ADD username text NOT NULL;

drop table profile;
drop table post;
drop table followers;

select * from followers where profile_id = 514459774;
select * from post where profile_id = 1555506227;