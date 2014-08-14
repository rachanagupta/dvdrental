-- create database test 
create database test;

-- drop table 
drop table dvd;
drop table movie;
drop table borrow;

-- create table 
CREATE TABLE `dvd` (
  `id` char(36) NOT NULL,
  `price` decimal(3,2) NOT NULL,
  `user_id` char(36) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `title` varchar(255) NOT NULL,
  `description` text,
  `added_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `price_idx` (`price`),
  KEY `status_idx` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `dvd_movie_mapping` (
	`dvd_id` char(36) NOT NULL,
	`movie_id` char(36) NOT NULL,
	PRIMARY KEY (`dvd_id`,`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `movie` (
  `id` char(36) NOT NULL,
  `name` varchar(255) NOT NULL,
  `genre` varchar(255) DEFAULT NULL,
  `studio` varchar(255) DEFAULT NULL,
  `released_on` timestamp NOT NULL,
  `year` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`name`, `year`),
  KEY `name_idx` (`name`),
  KEY `genre_idx` (`genre`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `borrowed` (
	`dvd_id` char(36) NOT NULL,
	`user_id` char(36) NOT NULL,
	`due_date` timestamp NULL DEFAULT NULL,
	`borrowed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`dvd_id`, `user_id`),
	 KEY `borrowed_on_idx` (`borrowed_on`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- multiple copies of dvd with same title 
insert into dvd (`id`, `price`, `user_id`, `title`, `status`, `description`)
values ('3f533253-b5ac-48e5-b816-a19852bc9b07', 9.99, 'db064348-b727-4f16-942f-13a7220c1252',"StarWars" , 2, "Star Wars Description ");
insert into dvd (`id`, `price`, `user_id`, `title`, `status`, `description`)
values ('25359b8c-36ec-405d-9cef-eff7bff22587', 9.99, 'b96441f1-1ce3-47f4-a93c-73568e02e98a',"StarWars" , 2, "Star Wars Description ");
insert into dvd (`id`, `price`, `user_id`, `title`, `status`, `description`)
values ('af7167de-f1de-42fb-b583-b8c1a4af7b9a', 9.99, 'f344e3a5-47fe-4bd5-96f9-a2b343b9035d',"StarWars" , 2, "Star Wars Description ");

insert into movie(`id`, `name`, `genre`, `studio`, `year`)
values('1908bca1-0369-486c-9aab-080fdf677c1d', "Start wars ", "Science Fiction", "Lucas Movies", "1980");

insert into dvd_movie_mapping(`dvd_id`, `movie_id`) values ('3f533253-b5ac-48e5-b816-a19852bc9b07','1908bca1-0369-486c-9aab-080fdf677c1d');

insert into borrowed (`dvd_id`, `user_id`, `due_date`) values ('3f533253-b5ac-48e5-b816-a19852bc9b07', '4f3ad4a8-b010-46de-8162-8b4ab7ab123e
', '2014-04-12');


