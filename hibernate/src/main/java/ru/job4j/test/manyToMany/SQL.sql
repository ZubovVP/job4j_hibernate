CREATE TABLE addresses (
id SERIAL PRIMARY KEY,
street VARCHAR(50),
number VARCHAR(50)
);

CREATE TABLE persons (
id SERIAL PRIMARY KEY,
name VARCHAR(50)
);

create table persons_addresses (
  person_id     integer not null,
  address_id  integer not null,
  CONSTRAINT FK_CAR_ID FOREIGN KEY (person_id)
      REFERENCES persons (id),
  CONSTRAINT FK_DRIVER_ID FOREIGN KEY (address_id)
      REFERENCES addresses (id)
);
SELECT * FROM addresses
SELECT * FROM persons
SELECT * FROM persons_addresses
DELETE FROM persons_addresses;
DELETE FROM addresses;
DELETE FROM persons;