CREATE TABLE technologies (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(255),
    project_id BIGINT REFERENCES projects(id) ON DELETE CASCADE
);