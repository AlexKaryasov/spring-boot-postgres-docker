CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS products (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS orders (
                                      id SERIAL PRIMARY KEY,
                                      description VARCHAR(255) NOT NULL
    );


INSERT INTO users (name)
VALUES ('Alice'), ('Bob');
INSERT INTO products (name) VALUES ('Laptop'), ('Phone');
INSERT INTO orders (description) VALUES ('Order #1'), ('Order #2');