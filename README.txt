================================================================================================================================
Requirements 
================================================================================================================================
DVD sharing API
One person can publish a list of DVDs that she is willing share.
Another person can search for a DVD and request a DVD in the list to borrow. 
This sends a notification to the lender for the person to bring the DVD. The lender can release a DVD when returned.

    Write a data model
    Write an API spec
    Code the implementation of the API (choose your language and libraries such as JDBC for calling the database)
    The code should compilable
    Describe your solution at a high-level and mention any potential design issues and possible improvements:
        What if there are multiple copies of the same title?
        What if all copies are taken?
        How to make sure first one is first served when a copy fries up?
        What if multiple instances of the web server serving the application?
        What is the biggest issue of the design? Where are we mostly likely to find bugs? What kind of bugs?

    Bonus points:
        Publish the project to a Github repo
        Add security and/or authentication to the API
        Implement email notifications. How to prevent accidentally sending multiple emails to the same person?
        Be creative, anything else you'd like to add
        
Implementation of the Required Feature 
What if there are multiple copies of the same title? - It is ok to having multiple copies with same title, copies will be borrowed 
on first added to the system unless they are fried or taken. Check logic in DVDManagerImpl#borrow method.

What if all copies are taken? - If all the copies are taken, than 404 Not Found will be send to client.  Check logic in DVDManagerImpl#borrow method.

How to make sure first one is first served when a copy fries up? - My understanding is not to lend fried copies even though they were added first.
Not fetching the DVD is taken care in the query. Check logic in DVDDaoImpl#findAvailable method.

What if multiple instances of the web server serving the application? - Proper isolation level are added to take care of multiple thread updating the 
row.

What is the biggest issue of the design? Where are we mostly likely to find bugs? What kind of bugs?
- slow because of row locking - need to fix it
- bug can be at the search dvds - need to optimize the where criteria.
- More Unit and Integration test cases  
- No versioning at this point of time 

 
Publish the project to a Github repo - published at https://github.com/rachana1101/dvd

Add security and/or authentication to the API - Added explain in the below sections

Implement email notifications. How to prevent accidentally sending multiple emails to the same person? - Please check EmailManager. 
Also sending mail to one user, multiple times can be prevents by adding in the cache. Once the mail is send, put it in cache for 
atlease 1 min. Every time you send the mail for with in that minute check in cache. If it is in Cache dont send it.
 
===============================================================================================================================
Assumption
===============================================================================================================================
DVD can have only one Movie 


      
===============================================================================================================================        
Project Structure         
===============================================================================================================================
The project is divided in to main 3 components 
Api: Where the resource class resides to interact with the web
Business : Contains the service layer, which takes the information from the Api layer, applies business logic and give it to the DAOs
		Service : Constains Managers
		DAO : Classes meant to interact with Database
		Entity : Classes meant to interact with the object.
External : Objects meant only to expose outside

Note : No caching is been added since it was out of scope of this project but hits to the DB can be reduced by adding caching at the 
appropriate layers.

===============================================================================================================================
Database - Also save in /main/resources/sql/create_db.sql
===============================================================================================================================
Please note that Table are having only minimum attributes required. There can be other attributes like actors, directors etc. 

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


 
=============================================================================================================================== 
Rest API 
===============================================================================================================================
*To share the dvds 
    Url : localhost:8080/dvd-api/rest/dvd/share  
    Request : [
        "3f533253-b5ac-48e5-b816-a19852bc9b07"    
    ]
    Header : Content-Type: application/json
             X_TRIPADIVSOR_USER_ID: db064348-b727-4f16-942f-13a7220c1252
   otherwise respective error with message will be returned
   [
        {
            "code":0,
            "message":" lender id is required", 
            "path":null
        }
    ]
 
*To fetch the information about particular DVD
Url : localhost:8080/dvd-api/rest/dvd/3cefe7a9-db6e-46fa-ae34-6367dd50f488
HTTP METHOD : GET
{
    "id": "3f533253-b5ac-48e5-b816-a19852bc9b07",
    "movieId": "1908bca1-0369-486c-9aab-080fdf677c1d",
    "price": 9.99,
    "userId": "db064348-b727-4f16-942f-13a7220c1252",
    "descripton": "Star Wars ",
    "addedOn": 1407567600000,
    "status": "FRIED"
}

*To search the dvd 
Url: localhost:8080/dvd-api/rest/dvd
Note : instead of making search at GET I am doing post to add more attributes in future.
HTTP METHOD : POST 
Request : {
  "price":9.99, 
  "name":"Start wars"
}
Response : List of DVDS 
[
    {
        "id": "3f533253-b5ac-48e5-b816-a19852bc9b07",
        "movieId": "1908bca1-0369-486c-9aab-080fdf677c1d",
        "price": 9.99,
        "userId": "db064348-b727-4f16-942f-13a7220c1252",
        "descripton": "Star Wars ",
        "addedOn": 1407654000000,
        "status": "FRIED"
    }
]

*To borrow the dvd 
Url : localhost:8080/dvd-api/rest/dvd/borrow
HTTP METHOD : PUT
Request : {
  "dvdId":"3f533253-b5ac-48e5-b816-a19852bc9b07",
  "dueDate":"2014-09-12"
}
Header : Content-Type: application/json
         X_TRIPADIVSOR_USER_ID: db064348-b727-4f16-942f-13a7220c1252
         
Security Check In API 
=========================================================================================================
 Using UUID instead of plain int or long ids to prevent the hackers from guessing the ids and access the information.
 Not accepting the user id from the json - this prevents the hackers from manipulating the user id. Accept it form the header.
 
Authorization 
=========================================================================================================
 Authorization hook can be added at the interceptor layer to fetch the userId from the header and take it to the 
 UserAPI to check 
 1) If such user exists or not - (out of scope)  
 2) What privileges it has - (out of scope)
 3) Lender cannot borrow his own DVD (implemented, see borrow method of DVDManagerImpl)
 
 
 
