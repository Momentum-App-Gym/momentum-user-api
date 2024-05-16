CREATE TABLE users (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    login TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL,
    birth_date DATE NOT NULL,
    password TEXT NOT NULL,
    role TEXT NOT NULL
);