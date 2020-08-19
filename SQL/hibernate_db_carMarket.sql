CREATE TABLE users1(
id SERIAL PRIMARY KEY,
name VARCHAR(50),
surname VARCHAR(50),
password VARCHAR(32) NOT NULL,
telephone VARCHAR(15) NOT NULL,
email VARCHAR(50) NOT NULL
);

CREATE TABLE cars1 (
id SERIAL PRIMARY KEY,
mark VARCHAR(150) NOT NULL,
category VARCHAR(100),
type_body VARCHAR(100),
transmission VARCHAR(50),
year_of_issue INT NOT NULL,
photos VARCHAR(500),
status BOOLEAN DEFAULT false,
price INT NOT NULL,
user_id INT NOT NULL REFERENCES users1(id)
);