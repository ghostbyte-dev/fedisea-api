CREATE TABLE instance_protocol
(
    instance_id VARCHAR(255) NOT NULL,
    protocol_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_instance_protocol PRIMARY KEY (instance_id, protocol_id)
);

CREATE TABLE protocol
(
    identifier  VARCHAR(255) NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(1000),
    homepage    VARCHAR(255),
    CONSTRAINT pk_protocol PRIMARY KEY (identifier)
);

ALTER TABLE protocol
    ADD CONSTRAINT uc_protocol_name UNIQUE (name);

ALTER TABLE instance_protocol
    ADD CONSTRAINT fk_inspro_on_instance FOREIGN KEY (instance_id) REFERENCES instance (domain);

ALTER TABLE instance_protocol
    ADD CONSTRAINT fk_inspro_on_protocol FOREIGN KEY (protocol_id) REFERENCES protocol (identifier);