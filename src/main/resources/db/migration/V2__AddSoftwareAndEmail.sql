CREATE TABLE software
(
    identifier VARCHAR(255) NOT NULL,
    name       VARCHAR(255),
    website    VARCHAR(255),
    soure_code VARCHAR(255),
    CONSTRAINT pk_software PRIMARY KEY (identifier)
);

ALTER TABLE instance
    ADD email VARCHAR(255);