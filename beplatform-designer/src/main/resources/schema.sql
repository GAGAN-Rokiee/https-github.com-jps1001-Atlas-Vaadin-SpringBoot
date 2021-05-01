CREATE TABLE solutions
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    template_name VARCHAR(64) NOT NULL,
    prefix        VARCHAR(10) NOT NULL,
    description   VARCHAR(500) NULL,
    last_updated  DATE        NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE object_class
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    unique_id        VARCHAR(50)  NOT NULL,
    solution_id      INT NOT NULL,
    class_name       VARCHAR(64)  NOT NULL,
    description      VARCHAR(500) NULL,
    base_type        int          not null,
    class_type       int          not null,
    base_path        VARCHAR(500) NULL,
    entity_name      VARCHAR(100) not NULL,
    security_enabled int          not null default 1,
    crypto_content   int          not null default 0,
    counter_name     VARCHAR(100) not NULL,
    storage_type     int null,
    system_class     int          not null default 0,
    default_workflow VARCHAR(100) not NULL default 'OFF',
    last_updated     TIMESTAMP        NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (solution_id) REFERENCES solutions(id) ON UPDATE NO ACTION ON DELETE CASCADE
);