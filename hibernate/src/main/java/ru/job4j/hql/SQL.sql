CREATE TABLE candidates (
id SERIAL PRIMARY KEY,
name VARCHAR(50),
expirience INT,
salary NUMERIC(10, 2),
vacancy_id int references vacancies(id)
);

CREATE TABLE vacancies (
id SERIAL PRIMARY KEY,
price NUMERIC(10, 2),
description VARCHAR(50)
);

CREATE TABLE bases (
id SERIAL PRIMARY KEY,
name VARCHAR(50)
);

CREATE TABLE bases_vacancies(
baseofvacancy_id INT,
listofvacancies_id INT
);