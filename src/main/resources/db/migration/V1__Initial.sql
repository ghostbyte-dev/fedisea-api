CREATE TABLE instance
(
    domain                VARCHAR(255) NOT NULL,
    title                 VARCHAR(255),
    description           VARCHAR(255),
    source_url            VARCHAR(255),
    thumbnail             VARCHAR(255),
    software              VARCHAR(255),
    software_version      VARCHAR(255),
    open_registration     BOOLEAN,
    total_users           BIGINT,
    active_users_month    BIGINT,
    active_users_halfyear BIGINT,
    local_posts           BIGINT,
    local_comments        BIGINT,
    status                VARCHAR(255),
    points_to             VARCHAR(255),
    last_seen             TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    CONSTRAINT pk_instance PRIMARY KEY (domain)
);