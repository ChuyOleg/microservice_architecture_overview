CREATE SEQUENCE IF NOT EXISTS resource_seq START WITH 1 INCREMENT BY 5;

CREATE TABLE IF NOT EXISTS resource (
    id BIGINT PRIMARY KEY DEFAULT nextval('resource_seq'),
    audio_data BYTEA NOT NULL
);

ALTER SEQUENCE resource_seq OWNED BY resource.id;
