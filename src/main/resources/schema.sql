CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(100)        NOT NULL,
    email         VARCHAR(150) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL
);

CREATE TABLE plants
(
    id             SERIAL PRIMARY KEY,
    user_id        INTEGER REFERENCES users (id) ON DELETE CASCADE,
    name           VARCHAR(100) NOT NULL,
    description    TEXT,
    species        VARCHAR(100),
    recommendation TEXT,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE reminders
(
    id                  SERIAL PRIMARY KEY,
    plant_id            INTEGER REFERENCES plants (id) ON DELETE CASCADE,
    water_interval_days INTEGER NOT NULL,
    next_water_date     DATE,
    last_wareed         DATE
);
