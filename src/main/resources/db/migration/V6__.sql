ALTER TABLE instance
    ADD software_id VARCHAR(255);

UPDATE instance SET software_id = software;

INSERT INTO software (identifier)
SELECT DISTINCT software_id
FROM instance
WHERE software_id IS NOT NULL
ON CONFLICT (identifier) DO NOTHING;

ALTER TABLE instance
    ADD CONSTRAINT FK_INSTANCE_ON_SOFTWARE FOREIGN KEY (software_id) REFERENCES software (identifier);

ALTER TABLE instance
    DROP COLUMN software;