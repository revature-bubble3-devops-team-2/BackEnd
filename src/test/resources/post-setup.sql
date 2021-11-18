drop table if exists profile, post, comment_section, followers, likes;

create table profile (
	profile_id int primary key,
	first_name varchar not null,
	last_name varchar not null,
	email varchar not null,
	username varchar not null unique,
	passkey varchar not null
);
create table post (
	post_id int primary key,
	profile_id int references profile(profile_id),
	body varchar,
	date_posted timestamp not null,
	image_url varchar
);
create table comment_section (
	comment_id int primary key,
	body varchar not null,
	date_commented timestamp not null,
	previous_comment int references comment_section(comment_id),
	post_id int references post(post_id),
	profile_id int references profile(profile_id)
);
create table followers (
	follower_id int primary key,
	profile_id int references profile(profile_id)
);
create table likes (
	post_id int references post(post_id),
	profile_id int references profile(profile_id),
	primary key (post_id, profile_id)
);

insert into profile (profile_id, first_name, last_name, email, username, passkey) values
           (1, 'One', 'LastOne', 'Email1', 'profile1', '11'),
           (2, 'Two', 'LastTwo', 'Email2', 'profile2', '22');

insert into post (post_id, profile_id, body, date_posted, image_url) values
          (1, 1, 'Hello', current_date, 'image_url1'),
          (2, 1, 'World', current_date, 'image_url2');

