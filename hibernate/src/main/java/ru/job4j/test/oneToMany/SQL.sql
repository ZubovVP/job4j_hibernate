CREATE TABLE carsforonetomany (
id SERIAL PRIMARY KEY,
name VARCHAR(50)
);

CREATE TABLE modelsforonetomany (
id SERIAL PRIMARY KEY,
name VARCHAR(50)
);

CREATE TABLE modelsforonetomany_carsforonetomany (
cars_id INT,
modelsforonetomany_id INT
);