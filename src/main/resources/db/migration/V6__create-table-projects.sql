CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    url VARCHAR(255),
    url_github VARCHAR(255)
);