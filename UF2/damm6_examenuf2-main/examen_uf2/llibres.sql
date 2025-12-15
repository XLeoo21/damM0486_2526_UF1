DROP TABLE IF EXISTS llibres;
CREATE TABLE llibres (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL
);
INSERT INTO llibres (title, author) VALUES
('One Hundred Years of Solitude', 'Gabriel García Márquez'),
('Don Quixote', 'Miguel de Cervantes'),
('1984', 'George Orwell');

