DROP SCHEMA IF EXISTS test1 CASCADE;
CREATE SCHEMA test1

CREATE TABLE address (
	idaddress SERIAL UNIQUE,  --key = integer; BIGSERIAL = large integer
	street varchar(45),
	number int CHECK (number > 0),
	city varchar(45),
	country varchar(45),
	PRIMARY KEY (idAddress)
)

CREATE TABLE company(
	idcompany SERIAL UNIQUE, -- NOT NULL e automat datorita lui SERIAL
	name varchar(45),
	domain varchar(45),
	PRIMARY KEY (idCompany)
)

CREATE TABLE person(
	idperson SERIAL UNIQUE,
	name varchar(45),
	surname varchar(45),
	age int CHECK (age >= 0),
	idaddress int4,
	idcompany int4,
	PRIMARY KEY (idPerson),
	UNIQUE(idaddress),      -- daca nu as avea asta, ar fi 1-n ? 
	FOREIGN KEY (idaddress) REFERENCES address(idaddress) ON DELETE RESTRICT,  --  cum mai bine? : CONSTRAINT fk_address_id FOREIGN KEY (idperson) REFERENCES address(idaddress), + fara camp de foreign key
	FOREIGN KEY (idcompany) REFERENCES company(idcompany) ON DELETE RESTRICT   -- CASCADE - stergel RESTRICT - prevents deletion of referenced row. NO ACTION = DEFAULT;
);

INSERT INTO test1.company (name, domain) 
	VALUES ('NTT Data', 'IT'),
	('EBS', 'IT');

INSERT INTO test1.address (street, number, city, country) 
	VALUES ('Lucian Blaga', 117, 'Cluj', 'Romania'),
	('Marcelelor', 2234, 'Bistrita', 'Romania');

INSERT INTO test1.person (name, surname, age, idaddress, idcompany) 
	VALUES ('Alex', 'Salajan', 22, 1, 1),
	('Sava', 'Cristin', 22, 2, 2);

