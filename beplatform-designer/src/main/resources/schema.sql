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



CREATE TABLE IF NOT EXISTS form_master
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    object_class_id      INT NOT NULL,
    label        VARCHAR(255)  NULL,
    object_class_name   VARCHAR(255) NULL,
    registry   VARCHAR(255) NULL,
    file_name   VARCHAR(255) NOT NULL,
    last_updated  TIMESTAMP   NOT NULL DEFAULT CURRENT_DATE,
    solution_id INT NOT NULL,
   FOREIGN KEY (object_class_id) REFERENCES object_class(id) ON UPDATE NO ACTION ON DELETE CASCADE,
   FOREIGN KEY (solution_id) REFERENCES solutions(id) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS form_attributes
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    master_id      INT NOT NULL,
    attribute_name        VARCHAR(255) NOT NULL,
    attribute_value        VARCHAR(255) NOT NULL,
    last_updated  TIMESTAMP        NOT NULL DEFAULT CURRENT_DATE,
   FOREIGN KEY (master_id) REFERENCES form_master(id) ON UPDATE NO ACTION ON DELETE CASCADE
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

CREATE TABLE IF NOT EXISTS counter
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    solution_id      INT  NULL,
    counter_name     VARCHAR(100) not NULL,
    last_updated     TIMESTAMP        NOT NULL DEFAULT CURRENT_DATE,
    FOREIGN KEY (solution_id) REFERENCES solutions(id) ON UPDATE NO ACTION ON DELETE CASCADE

);
