DROP SCHEMA IF EXISTS proiect1 CASCADE;
CREATE SCHEMA proiect1
--SET search_path TO test1;

CREATE TABLE address (
	idaddress SERIAL UNIQUE,  --key = integer; BIGSERIAL = large integer
	street varchar(45) NOT NULL,
	number int CHECK (number > 0) NOT NULL,
	city varchar(45) NOT NULL,
	country varchar(45) NOT NULL,
	PRIMARY KEY (idAddress),
	UNIQUE(street, number, city)
)

CREATE TABLE company(
	idcompany SERIAL UNIQUE, -- NOT NULL e automat datorita lui SERIAL
	name varchar(45) NOT NULL,
	domain varchar(45),
	PRIMARY KEY (idCompany),
	UNIQUE(name)
)

CREATE TABLE person(
	idperson SERIAL UNIQUE,
	name varchar(45) NOT NULL,
	surname varchar(45) NOT NULL,
	age int CHECK (age >= 0),
	idaddress int4,
	idcompany int4,
	PRIMARY KEY (idPerson),
	FOREIGN KEY (idaddress) REFERENCES address(idaddress) ON DELETE SET NULL,  --  cum mai bine? : CONSTRAINT fk_address_id FOREIGN KEY (idperson) REFERENCES address(idaddress), + fara camp de foreign key
	FOREIGN KEY (idcompany) REFERENCES company(idcompany) ON DELETE SET NULL,   -- CASCADE - stergel RESTRICT - prevents deletion of referenced row. NO ACTION = DEFAULT;
	UNIQUE (name, surname)
);

INSERT INTO proiect1.company (name, domain) 
	VALUES ('NTT Data', 'IT'),
	('EBS', 'IT');

INSERT INTO proiect1.address (street, number, city, country) 
	VALUES ('Lucian Blaga', 117, 'Cluj', 'Romania'),
	('Marcelelor', 2234, 'Bistrita', 'Romania');

INSERT INTO proiect1.person (name, surname, age, idaddress, idcompany) 
	VALUES ('Alex', 'Salajan', 22, 2, 1),
	('Sava', 'Cristin', 22, 1, 2);

