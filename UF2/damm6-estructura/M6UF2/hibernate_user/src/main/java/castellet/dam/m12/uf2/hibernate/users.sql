
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
	id SERIAL PRIMARY KEY,
	name VARCHAR(20)
);

INSERT INTO users
(name)
VALUES
('Lola');

INSERT INTO sports
(name)
VALUES
('Teo');