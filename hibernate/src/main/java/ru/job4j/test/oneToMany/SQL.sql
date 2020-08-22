CREATE TABLE carsforonetomany (
id SERIAL PRIMARY KEY,
name VARCHAR(50)
);

CREATE TABLE modelsforonetomany (
id SERIAL PRIMARY KEY,
name VARCHAR(50),
car_id INT REFERENCES carsforonetomany (id)
);