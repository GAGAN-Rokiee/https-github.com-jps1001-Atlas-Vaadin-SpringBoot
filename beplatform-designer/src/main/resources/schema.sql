CREATE TABLE IF NOT EXISTS solutions
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    template_name VARCHAR(64) NOT NULL,
    prefix        VARCHAR(10) NOT NULL,
    description   VARCHAR(500) NULL,
    last_updated  TIMESTAMP        NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS object_class
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

CREATE TABLE IF NOT EXISTS workflow_master
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    solution_id   INT NOT NULL,
    name          VARCHAR(100) NOT NULL,
    prefix        VARCHAR(50) NOT NULL,
    description   VARCHAR(500) NULL,
    last_updated  TIMESTAMP        NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (solution_id) REFERENCES solutions(id) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS workflow_property
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    workflow_id   INT NOT NULL,
    property_name VARCHAR(100) NOT NULL,
    property_type VARCHAR(50) NOT NULL,
    FOREIGN KEY (workflow_id) REFERENCES workflow_master(id)
);

CREATE TABLE IF NOT EXISTS workflow_role
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    workflow_id          INT NOT NULL,
    role_name            VARCHAR(100) NOT NULL,
    workflow_property_id INT NOT NULL,
    is_dynamic           INT NOT NULL DEFAULT 0,
    is_created           INT NOT NULL DEFAULT 0,
    FOREIGN KEY (workflow_property_id) REFERENCES workflow_property(id),
    FOREIGN KEY (workflow_id) REFERENCES workflow_master(id)
);

CREATE TABLE IF NOT EXISTS workflow_advanced
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    workflow_id          INT NOT NULL,
    jar_type             VARCHAR(10) NOT NULL,
    jar_class            VARCHAR(100) NOT NULL,
    jar_path             VARCHAR(100) NOT NULL,
    FOREIGN KEY (workflow_id) REFERENCES workflow_master(id)
);

CREATE TABLE IF NOT EXISTS PROPERTY
(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    SOLUTION_ID INT NOT NULL,
    OBJECT_CLASS_ID INT NOT NULL,
    PROPERTY_NAME VARCHAR(100) NOT NULL,
    PROPERTY_TYPE VARCHAR(20) NOT NULL,
    LABEL VARCHAR(100) NOT NULL,
    CONSTRAINT_KEY VARCHAR(50) NULL,
    CONSTRAINT_VALUE VARCHAR(50) NULL,
    IS_NULL INT NOT NULL DEFAULT 1,
    DEFAULT_VALUE VARCHAR(100) NULL,
    LAST_UPDATED TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (solution_id) REFERENCES solutions(id) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (object_class_id) REFERENCES object_class(id) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS workflow_advanced_settings
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    workflow_id          INT NOT NULL,
    setting_name         VARCHAR(100) NOT NULL,
    setting_value        VARCHAR(100) NOT NULL,
    FOREIGN KEY (workflow_id) REFERENCES workflow_master(id)
);
CREATE TABLE IF NOT EXISTS counter
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    solution_id      INT  NULL,
    counter_name     VARCHAR(100) not NULL,
    last_updated     TIMESTAMP        NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (solution_id) REFERENCES solutions(id) ON UPDATE NO ACTION ON DELETE CASCADE
);