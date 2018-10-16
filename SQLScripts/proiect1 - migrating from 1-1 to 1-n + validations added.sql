SET search_path to proiect1;

-- modificare tabel + migrare date fara sa se piarda.

create or replace function datamigration()
returns void as $$
declare
eachaddress record;
BEGIN
	FOR eachaddress IN
		SELECT * FROM proiect1.address ORDER BY idperson
	LOOP 
		UPDATE person SET idaddress = eachaddress.idaddress 
		WHERE person.idperson = eachaddress.idperson;
	END LOOP;
END;
$$language plpgsql;


ALTER TABLE person
ADD COLUMN idaddress int4,
ADD CONSTRAINT fk_personAddress
	FOREIGN KEY (idaddress)
	REFERENCES address(idaddress) ON DELETE SET NULL,
ALTER COLUMN name SET NOT NULL,
ALTER COLUMN surname SET NOT NULL,
ADD CONSTRAINT person_validation_constraint UNIQUE (name, surname);


DO $$ BEGIN
    PERFORM "datamigration"();
END $$;


ALTER TABLE company
ALTER COLUMN name SET NOT NULL,
ADD CONSTRAINT company_validation_constraint UNIQUE (name);

ALTER TABLE address
ALTER COLUMN street SET NOT NULL,
ALTER COLUMN number  SET NOT NULL,
ALTER COLUMN city  SET NOT NULL,
ALTER COLUMN country  SET NOT NULL,
DROP COLUMN idperson,
ADD CONSTRAINT address_validation_constraint UNIQUE (street, number, city);









